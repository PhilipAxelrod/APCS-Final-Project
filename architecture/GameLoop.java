package architecture;

import java.util.Timer;
import java.util.TimerTask;

public class GameLoop {

    public static void main(String[] args) {
        Timer timer = new Timer();
        long startTime = System.currentTimeMillis();

        TimerTask runnable = new Combatant(0, 0, 0, null) {
            @Override
            public void run() {
                System.out.println("I'm bob!");
                timer.schedule(this, 1000);
            }
        };

        System.out.println("just scheduled!");

        timer.schedule(runnable, 10000);
    }
}
