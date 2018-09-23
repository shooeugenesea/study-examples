package ohcache;

import org.caffinitas.ohc.CacheSerializer;
import org.caffinitas.ohc.OHCache;
import org.caffinitas.ohc.OHCacheBuilder;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.UUID;
import java.util.stream.IntStream;

public class SimpleMain {

    public static void main(String[] params) {
        Scanner scanner = new Scanner(System.in);
        while(scanner.hasNextLine()) {
            if (scanner.nextLine().trim().equals("GO")) {
                System.out.println(Runtime.getRuntime().freeMemory()/1024/1024 + "/" + Runtime.getRuntime().totalMemory()/1024/1024);
                OHCache<String,String> c = OHCacheBuilder.<String,String>newBuilder()
                        .keySerializer(new StringSerializer())
                        .valueSerializer(new StringSerializer())
                        .build();
                c.put("A", "A");
                System.out.println(c.get("A"));
                IntStream.range(0, 1000_000).forEach(idx -> {
                    c.put(UUID.randomUUID().toString(), UUID.randomUUID().toString());
                });
                System.out.println(Runtime.getRuntime().freeMemory()/1024/1024 + "/" + Runtime.getRuntime().totalMemory()/1024/1024);
            } else {
                System.out.println("input 'GO' then click enter");
            }
        }
    }

    private static class StringSerializer implements CacheSerializer<String> {

        @Override
        public void serialize(String s, ByteBuffer byteBuffer) {
            byteBuffer.put(s.getBytes());
        }

        @Override
        public String deserialize(ByteBuffer byteBuffer) {
            return StandardCharsets.UTF_8.decode(byteBuffer).toString();
        }

        @Override
        public int serializedSize(String s) {
            return s.getBytes().length;
        }
    }

}
