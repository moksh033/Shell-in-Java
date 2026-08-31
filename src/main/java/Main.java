import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class Main {

    //helper function which splits path into a string array
    public static String[] getPathFolders() {
        String pathEnv = System.getenv("PATH");
        return pathEnv.split(File.pathSeparator);
    }
    //diff between these two functions is - above one is only giving array of folders and below one is returning file path
    //function which gives back the path for that specific command 
    public static String findCommandPath(String cmd) {
        String[] paths = getPathFolders();
        //dir is going over each path and checking if its executable and if it exists
        for (String dir : paths) {
            File file = new File(dir, cmd);
            if (file.exists() && file.canExecute()) {
                return file.getAbsolutePath();
            }
        }
        return null; 
    }

    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        //hash set better than a normal set runs over O(1)
        Set<String> builtins = new HashSet<>(Arrays.asList("echo", "exit", "type"));

        while (true) {
            System.out.print("$ ");
            String input = scanner.nextLine();
            //splitting the input user command into tokens , so we know token[0] is a the name followed by all others which are arguments
            String[] tokens = input.split("\\s+");
            String cmd = tokens[0];
            String[] cmdArgs = Arrays.copyOfRange(tokens, 1, tokens.length);

            if (input.equals("exit")) {
                break;
            } else if (input.startsWith("echo ")) {
                System.out.println(input.substring(5));
            } else if (input.startsWith("type ")) {
                    cmd = input.substring(5);

                if (builtins.contains(cmd)) {
                    System.out.println(cmd + " is a shell builtin");
                } else {
                    String path = findCommandPath(cmd);
                    if (path != null) {
                        System.out.println(cmd + " is " + path);
                    } else {
                        System.out.println(cmd + ": not found");
                    }
                }
            } else {
            String path = findCommandPath(cmd);
            if (path != null) {
            List<String> commandList = new ArrayList<>();
            commandList.add(cmd); 
            commandList.addAll(Arrays.asList(cmdArgs));

            try {
            ProcessBuilder pb = new ProcessBuilder(commandList);
        
            pb.directory(new File(path).getParentFile());
            pb.inheritIO(); 
            Process process = pb.start();
            process.waitFor(); 
            }       
            catch (Exception e) {
            System.out.println("Error running command: " + e.getMessage());
            }
}           else {
             System.out.println(cmd + ": command not found");
}

            }

        
        }

        scanner.close();
    }
}
