package server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import model.Topic;
import model.Vote;
import service.TopicService;
import service.UserService;
import service.VoteService;
import util.Logger;
import util.RequestParser;

import java.util.Scanner;

public class ServerHandler extends SimpleChannelInboundHandler<String> {

    private final RequestParser requestParser;

    private final UserService userService;

    private final VoteService voteService;

    private final TopicService topicService;

    private final VoteCreationDialog voteCreationDialog;

    public ServerHandler(UserService userService, VoteService voteService, TopicService topicService, RequestParser requestParser) {
        super();
        this.userService = userService;
        this.voteService = voteService;
        this.topicService = topicService;
        this.requestParser = requestParser;
        this.voteCreationDialog = new VoteCreationDialog();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        Logger.log("Received: " + msg);

        if (voteCreationDialog.isInProgress()) {
            String response = voteCreationDialog.handleInput(msg);
            ctx.writeAndFlush(response);
            if (!voteCreationDialog.isInProgress()) {
                System.out.println(voteCreationDialog.getCurrentTopic());
                voteService.createVote(voteCreationDialog.getCurrentTopic(), voteCreationDialog.getCurrentVote());
            }
        } else {
            String command = requestParser.getCommand(msg);
            String subCommand = requestParser.getSubCommand(msg);
            String[] args = requestParser.getArgs(msg);

            String response = switch (command) {
                case "login" -> userService.login(args);
                case "create" -> handleCreate(subCommand, args, ctx);
//                case "view" -> topicService.viewTopics(args);
//                case "vote" -> voteService.vote(args);
//                case "delete" -> voteService.deleteVote(args);
                case "exit" -> "Goodbye!";
                default -> "Unknown command: " + command;
            };

            Logger.log("Sending response: " + response);
            ctx.writeAndFlush(response);
        }
    }

    private String handleCreate(String subCommand, String[] args, ChannelHandlerContext ctx) {
        if (subCommand.equals("vote")) {
            if (args.length < 1 || !args[1].startsWith("-t=")) {
                return "Usage: create vote -t=<topic>";
            }
            voteCreationDialog.start(args[0].substring(3));
            return "Enter vote name (unique): \n";
        } else if (subCommand.equals("topic")) {
            return topicService.createTopic(args);
        } else {
            return "Unknown sub-command for create: " + subCommand;
        }
    }

//    private String handleView(String[] args) {
//        if (args.length == 0) {
//            StringBuilder sb = new StringBuilder();
//            for (Topic topic : topics.values()) {
//                sb.append(topic.getName()).append(" (votes in topic=").append(topic.getVotes().size()).append(")\n");
//            }
//            return sb.toString();
//        } else if (args.length == 2 && args[0].equals("-t=")) {
//            String topicName = args[0].substring(3);
//            Topic topic = topics.get(topicName);
//            if (topic == null) {
//                return "Topic not found: " + topicName;
//            }
//            StringBuilder sb = new StringBuilder();
//            for (Vote vote : topic.getVotes()) {
//                sb.append(vote.getName()).append(": ").append(vote.getDescription()).append("\n");
//            }
//            return sb.toString();
//        } else {
//            return "Usage: view [-t=<topic>]";
//        }
//    }
//
//    private String handleVote(String[] args) {
//        if (currentUser == null) {
//            return "You must login first.";
//        }
//        if (args.length < 2 || !args[0].startsWith("-t=") || !args[1].startsWith("-v=")) {
//            return "Usage: vote -t=<topic> -v=<vote>";
//        }
//        String topicName = args[0].substring(3);
//        String voteName = args[1].substring(3);
//        Topic topic = topics.get(topicName);
//        if (topic == null) {
//            return "Topic not found: " + topicName;
//        }
//        Vote vote = topic.getVotes().stream()
//                .filter(v -> v.getName().equals(voteName))
//                .findFirst()
//                .orElse(null);
//        if (vote == null) {
//            return "Vote not found: " + voteName;
//        }
//        Scanner scanner = new Scanner(System.in);
//        System.out.println("Enter your choice: ");
//        String choice = scanner.nextLine();
//        vote.voteForOption(choice);
//        return "Voted for " + choice + " in " + voteName;
//    }
//
//    private String handleDelete(String[] args) {
//        if (currentUser == null) {
//            return "You must login first.";
//        }
//        if (args.length < 2 || !args[0].startsWith("-t=") || !args[1].startsWith("-v=")) {
//            return "Usage: delete -t=<topic> -v=<vote>";
//        }
//        String topicName = args[0].substring(3);
//        String voteName = args[1].substring(3);
//        Topic topic = topics.get(topicName);
//        if (topic == null) {
//            return "Topic not found: " + topicName;
//        }
//        Vote vote = topic.getVotes().stream()
//                .filter(v -> v.getName().equals(voteName))
//                .findFirst()
//                .orElse(null);
//        if (vote == null) {
//            return "Vote not found: " + voteName;
//        }
//        topic.removeVote(vote);
//        return "Vote deleted: " + voteName;
//    }

    private String handleExit() {
        return "Goodbye!";
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}