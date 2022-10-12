import java.io.FileInputStream;
import java.io.PrintStream;

public class AC {
    public static void main(String[] args) throws Exception {
        System.setIn(new FileInputStream("./input/a.test"));
        System.setOut(new PrintStream("./output/ac.out"));

    }
}
