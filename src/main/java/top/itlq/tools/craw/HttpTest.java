package top.itlq.tools.craw;

import org.junit.jupiter.api.Test;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HttpTest {

    Pattern pattern = Pattern.compile("http.*/(\\d+)\\.ts$");

    @Test
    void test1() throws IOException, InterruptedException {
        String url = "http://live1.plus.hebtv.com/bjws_sd/1568113462/1568114993158.ts";
        Matcher matcher = pattern.matcher(url);
        matcher.find();
        String name = pattern.matcher(url).group(1);
        HttpClient httpClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofMillis(5000))
                .build();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .timeout(Duration.ofMillis(5009))
                .build();
        HttpResponse<InputStream> response = httpClient.send(request, HttpResponse.BodyHandlers.ofInputStream());
        try (
                InputStream inputStream = response.body();
                FileOutputStream outputStream = new FileOutputStream(name + ".ts")
        ){
            inputStream.transferTo(outputStream);
        }
    }
}
