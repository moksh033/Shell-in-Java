import java.io.File;

public class Navigation {
    public static File currentDir = new File(System.getProperty("user.dir"));

    public static String getCurrentDir() {
        return currentDir.getAbsolutePath();
    }

    public static void pwd() {
        System.out.println(currentDir.getAbsolutePath());
    }

    public static void cd(String path) {
        if (path == null || path.isEmpty() || path.equals("~")) {
            String home = System.getenv("HOME");
            currentDir = new File(home != null ? home : System.getProperty("user.home"));
            return;
        }

        File target = path.startsWith("/") ? new File(path) : new File(currentDir, path);
        try {
            File resolved = target.getCanonicalFile();
            if (resolved.isDirectory()) {
                currentDir = resolved;
            } else {
                System.out.println("cd: " + path + ": No such file or directory");
            }
        } catch (Exception e) {
            System.out.println("cd: " + path + ": No such file or directory");
        }
    }
}
