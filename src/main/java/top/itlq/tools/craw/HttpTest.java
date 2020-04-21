package top.itlq.tools.craw;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HttpTest {

    Pattern pattern = Pattern.compile("^http.*/(\\d+)\\.ts$");

    @Test
    void test1() throws IOException, InterruptedException {
        String url = "http://live1.plus.hebtv.com/bjws_sd/1568113462/1568114993158.ts";
        Matcher matcher = pattern.matcher(url);
        if(!matcher.find()){
            System.err.println("url有误");
        }
        String name = matcher.group(1);
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

    @Test
    void test2() throws IOException, InterruptedException {
        HttpClient httpClient = HttpClient.newBuilder()
                .build();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://www.baidu.com"))
                .build();
        System.out.println("1: " + System.currentTimeMillis());
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println("2: " + System.currentTimeMillis());
        CompletableFuture<HttpResponse<String>> completableFuture = httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString());
        System.out.println("3: " + System.currentTimeMillis());
        CountDownLatch countDownLatch = new CountDownLatch(1);
        Mono.fromFuture(httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString()))
                .map(HttpResponse::body)
                .subscribe(System.out::println,System.err::println,countDownLatch::countDown);
        System.out.println("4: " + System.currentTimeMillis());
//        countDownLatch.await();
        System.out.println("5: " + System.currentTimeMillis());
//        completableFuture.

    }
}
