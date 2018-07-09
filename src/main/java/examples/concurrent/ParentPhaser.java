package examples.concurrent;

import java.util.concurrent.Phaser;
import java.util.stream.IntStream;

public class ParentPhaser {

    public static void main(String[] params) {
        Phaser parent = new Phaser() {
            @Override
            protected boolean onAdvance(int phase, int registeredParties) {
                System.out.println("parent phase:" + phase + ", registeredParties:" + registeredParties);
                return super.onAdvance(phase, registeredParties);
            }
        };
        Phaser child1 = new Phaser(parent, 4) {
            @Override
            protected boolean onAdvance(int phase, int registeredParties) {
                System.out.println("child phase:" + phase + ", registeredParties:" + registeredParties);
                return super.onAdvance(phase, registeredParties);
            }
        };
        Phaser child2 = new Phaser(parent, 3) {
            @Override
            protected boolean onAdvance(int phase, int registeredParties) {
                System.out.println("child phase:" + phase + ", registeredParties:" + registeredParties);
                return super.onAdvance(phase, registeredParties);
            }
        };
        IntStream.range(0,10).forEach(idx -> {
            child1.arrive();
            child1.arrive();
            child1.arrive();
            child1.arrive();
//            child1.arrive(); // cause error, child1 need wait for child2 finish
            child2.arrive();
            child2.arrive();
            child2.arrive();
        });
    }


}
