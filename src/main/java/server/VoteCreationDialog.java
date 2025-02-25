package server;

import model.DialogState;
import model.Vote;

public class VoteCreationDialog {
    private DialogState currentState = DialogState.IDLE;
    private String currentTopic;
    private Vote currentVote;

    public String handleInput(String input) {
        input = input.trim();

        switch (currentState) {
            case AWAITING_VOTE_NAME:
                currentVote = new Vote();
                currentVote.setName(input);
                currentState = DialogState.AWAITING_VOTE_DESCRIPTION;
                return "Enter vote description: \n";

            case AWAITING_VOTE_DESCRIPTION:
                currentVote.setDescription(input);
                currentState = DialogState.AWAITING_NUMBER_OF_OPTIONS;
                return "Enter the number of options: \n";

            case AWAITING_NUMBER_OF_OPTIONS:
                Long numberOfOptions = Long.parseLong(input);
                currentVote.setNumberOfOptions(numberOfOptions);
                currentState = DialogState.AWAITING_OPTIONS;
                return "Enter option 1: \n";

            case AWAITING_OPTIONS:
                currentVote.addOption(input);
                if (currentVote.getOptions().size() < currentVote.getNumberOfOptions()) {
                    return "Enter option " + (currentVote.getOptions().size() + 1) + ": \n";
                } else {
                    currentState = DialogState.IDLE;
                    return "Vote created successfully: " + currentVote.getName() + "\n";
                }

            default:
                return "Invalid state. Please try again.\n";
        }
    }

    public void start(String topicName) {
        currentTopic = topicName;
        currentState = DialogState.AWAITING_VOTE_NAME;
    }

    public boolean isInProgress() {
        return currentState != DialogState.IDLE;
    }

    public Vote getCurrentVote() {
        return currentVote;
    }

    public String getCurrentTopic() {
        return currentTopic;
    }
}
