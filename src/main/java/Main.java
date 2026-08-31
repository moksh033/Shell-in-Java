import java.io.File;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class Main {
    static Set<String> builtins = new HashSet<>(Arrays.asList("echo", "exit", "type", "pwd", "cd"));

    static String findCommand(String cmd) {
        String pathEnv = System.getenv("PATH");
        if (pathEnv == null) return null;
        for (String dir : pathEnv.split(File.pathSeparator)) {
            File file = new File(dir, cmd);
            if (file.exists() && file.canExecute()) {
                return file.getAbsolutePath();
            }
        }
        return null;
    }

    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("$ ");
            if (!scanner.hasNextLine()) break;
            String line = scanner.nextLine().trim();
            if (line.isEmpty()) continue;

            Redirection redir = Redirection.parse(line.split("\\s+"));
            String[] tokens = redir.args;
            if (tokens.length == 0) continue;

            String cmd = tokens[0];
            String[] cmdArgs = Arrays.copyOfRange(tokens, 1, tokens.length);

            if (cmd.equals("exit")) {
                break;
            } else if (cmd.equals("pwd")) {
                PrintStream out = redir.getOut();
                out.println(Navigation.getCurrentDir());
                if (out != System.out) out.close();
            } else if (cmd.equals("cd")) {
                Navigation.cd(cmdArgs.length > 0 ? cmdArgs[0] : null);
            } else if (cmd.equals("echo")) {
                PrintStream out = redir.getOut();
                out.println(String.join(" ", cmdArgs));
                if (out != System.out) out.close();
            } else if (cmd.equals("type")) {
                String target = cmdArgs.length > 0 ? cmdArgs[0] : "";
                PrintStream out = redir.getOut();
                if (builtins.contains(target)) {
                    out.println(target + " is a shell builtin");
                } else {
                    String path = findCommand(target);
                    out.println(path != null ? target + " is " + path : target + ": not found");
                }
                if (out != System.out) out.close();
            } else {
                String path = findCommand(cmd);
                if (path != null) {
                    List<String> command = new ArrayList<>();
                    command.add(cmd);
                    command.addAll(Arrays.asList(cmdArgs));
                    try {
                        ProcessBuilder pb = new ProcessBuilder(command);
                        pb.directory(Navigation.currentDir);
                        redir.apply(pb);
                        pb.start().waitFor();
                    } catch (Exception e) {
                        System.out.println("Error running command: " + e.getMessage());
                    }
                } else {
                    PrintStream err = redir.getErr();
                    err.println(cmd + ": command not found");
                    if (err != System.err) err.close();
                }
            }
        }
        scanner.close();
    }
}
