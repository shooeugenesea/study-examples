package examples.concurrent;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Phaser;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class PhaserRacingGame {

    public static void main(String[] params) {
        Random r = new Random();
        Phaser perGamePhase = new Phaser() {
            @Override
            protected boolean onAdvance(int phase, int registeredParties) {
                System.out.println("phase:" + phase + ", registeredParties:" + registeredParties + ", arrived:" + getArrivedParties());
                switch (phase) {
                    case 0:
                        System.out.println("start!");
                        break;
                    case 1:
                        System.out.println("game finish");
                        break;
                    default:
                        System.out.println("unexpected:" + phase);
                }
                return super.onAdvance(phase, registeredParties);
            }
        };
        IntStream.range(0,5).forEach(idx -> {
            perGamePhase.register();
            new Thread(() -> {
                System.out.println(idx + " arrive starting line and wait for start");
                perGamePhase.arriveAndAwaitAdvance();
                System.out.println(idx + " run!! startTime:" + System.currentTimeMillis());
                long spendTime = r.nextInt(1000);
                sleep(spendTime);
                perGamePhase.arrive();
            }).start();
        });
        sleep(5000);
    }

    private static void sleep(long ms) {
        try {
            TimeUnit.MILLISECONDS.sleep(ms);
        } catch (InterruptedException e) {
        }
    }

}
