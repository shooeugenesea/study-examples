package examples.file;

import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class WatchFileEvent {

    public static void main(String[] params) throws Throwable {
        ExecutorService e = Executors.newSingleThreadExecutor();
        Path path = Paths.get("d:/Isaac");
        try (final WatchService w = FileSystems.getDefault().newWatchService()) {
            path.register(w, StandardWatchEventKinds.ENTRY_CREATE, 
                    StandardWatchEventKinds.ENTRY_DELETE, 
                    StandardWatchEventKinds.ENTRY_MODIFY);
            e.execute(new Runnable(){
                @Override
                public void run() {
                    while (true) {
                        try {
                            WatchKey k = w.take();
                            for ( WatchEvent<?> e: k.pollEvents() ) {
                                @SuppressWarnings("unchecked")
                                WatchEvent<Path> pathEvent = (WatchEvent<Path>)e;
                                Path path = pathEvent.context();
                                System.out.println(pathEvent.kind() + ":" + path);
                                if (!k.reset()) {
                                    break;
                                }
                            }
                        } catch (Throwable ex) {
                            ex.printStackTrace();
                        }                    
                    }
                }});
            TimeUnit.DAYS.sleep(1);
        }   
    }

}
