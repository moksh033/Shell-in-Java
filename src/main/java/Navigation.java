import java.io.File;

public class Navigation {
    // we track the current directory ourselves since Java can't actually change its real working dir
    private static String currentDir = System.getProperty("user.dir");

    public static String getCurrentDir() { return currentDir; }

    // prints where we are right now
    public static void pwd() { System.out.println(currentDir); }

    public static void cd(String target) {
        // no argument, empty, or ~ means "take me home"
        if (target == null || target.isEmpty() || target.equals("~")) {
            updateDir(System.getProperty("user.home"));
            return;
        }

        // if it starts with / it's absolute, otherwise build it relative to where we are
        File dest = target.startsWith("/") ? new File(target) : new File(currentDir, target);
        // normalize cleans up stuff like . and .. so we get a clean path
        File resolved = dest.toPath().normalize().toFile();

        if (resolved.isDirectory()) {
            updateDir(resolved.getPath());
        } else {
            System.out.println("cd: " + target + ": No such file or directory");
        }
    }

    // helper to keep currentDir and the system property in sync
    private static void updateDir(String path) {
        if (path != null) {
            currentDir = path;
            System.setProperty("user.dir", path);
        }
    }
}
