package util;

public class RequestParser {
    public String getCommand(String request) {
        return request.split(" ")[0];
    }

    public static String getSubCommand(String request) {
        String[] parts = request.split(" ");
        return parts.length > 1 ? parts[1] : null;
    }

    public String[] getArgs(String request) {
        String[] parts = request.split(" ");
        String[] args = new String[parts.length - 1];
        System.arraycopy(parts, 1, args, 0, args.length);
        return args;
    }
}
