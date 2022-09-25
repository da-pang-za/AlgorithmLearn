import org.fusesource.jansi.Ansi;

import static org.fusesource.jansi.Ansi.Color.GREEN;
import static org.fusesource.jansi.Ansi.Color.RED;

class Test {
    int mod = 1000_000_007;

    //===========  method ==================
    public static void main(String[] args) {
        System.out.println("DPZ EXE");
        System.out.println(Ansi.ansi().fg(RED).a("Hello").fg(GREEN).a(" World").reset().a(" DPZ"));
    }
}

