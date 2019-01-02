package examples;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class TestMain {

    public static void main(String[] params) throws IOException {
        StringBuilder sb = new StringBuilder();
        Files.list(Paths.get("C:\\Users\\isaac.liao\\Downloads\\preprovision_ap")).forEach(file -> {
            try {
                String content = FileUtils.readFileToString(file.toFile(), "UTF-8");
                String body = StringUtils.substringBetween(content, "{", "}");
                JsonObject jsonObject = new Gson().fromJson("{" + body + "}", JsonObject.class);
                sb.append(jsonObject.toString()).append("\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        FileUtils.writeStringToFile(new File("C:\\Projects\\study-practice\\src\\main\\resources\\preprovision_ap.txt"), sb.toString(), "UTF-8");
    }

}
