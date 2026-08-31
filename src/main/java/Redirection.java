import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

public class Redirection {
    public String outFile;
    public String errFile;
    public boolean appendOut;
    public boolean appendErr;
    public String[] args;

    public static Redirection parse(String[] tokens) {
        Redirection r = new Redirection();
        List<String> list = new ArrayList<>();

        for (int i = 0; i < tokens.length; i++) {
            String t = tokens[i];
            if ((t.equals(">") || t.equals("1>")) && i + 1 < tokens.length) {
                r.outFile = tokens[++i];
                r.appendOut = false;
            } else if ((t.equals(">>") || t.equals("1>>")) && i + 1 < tokens.length) {
                r.outFile = tokens[++i];
                r.appendOut = true;
            } else if (t.equals("2>") && i + 1 < tokens.length) {
                r.errFile = tokens[++i];
                r.appendErr = false;
            } else if (t.equals("2>>") && i + 1 < tokens.length) {
                r.errFile = tokens[++i];
                r.appendErr = true;
            } else {
                list.add(t);
            }
        }

        r.args = list.toArray(new String[0]);
        return r;
    }

    public void apply(ProcessBuilder pb) {
        pb.inheritIO();
        if (outFile != null) {
            File f = new File(outFile);
            pb.redirectOutput(appendOut ? ProcessBuilder.Redirect.appendTo(f) : ProcessBuilder.Redirect.to(f));
        }
        if (errFile != null) {
            File f = new File(errFile);
            pb.redirectError(appendErr ? ProcessBuilder.Redirect.appendTo(f) : ProcessBuilder.Redirect.to(f));
        }
    }

    public PrintStream getOut() throws Exception {
        return outFile == null ? System.out : new PrintStream(new FileOutputStream(outFile, appendOut));
    }

    public PrintStream getErr() throws Exception {
        return errFile == null ? System.err : new PrintStream(new FileOutputStream(errFile, appendErr));
    }
}
