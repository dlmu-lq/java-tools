package top.itlq.tools.reactor.webflux;

import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.concurrent.CountDownLatch;

public class TestWebClient {
    @Test
    void test() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(2);
        Mono<String> baiduMono = WebClient.create()
                .get()
                .uri(URI.create("https://www.baidu.com"))
                .retrieve().bodyToMono(String.class);
        baiduMono.subscribe(System.out::println,System.err::println,countDownLatch::countDown);
        Mono<String> baiduMono2 = WebClient.create()
                .get()
                .uri(URI.create("https://www.baidu.com"))
                .retrieve().bodyToMono(String.class);
        baiduMono2.subscribe(System.out::println,System.err::println,countDownLatch::countDown);
        System.out.println(1);
        countDownLatch.await();
        System.out.println(2);
    }

    @Test
    void testCookie(){
        Mono<String> baiduMono = WebClient.create()
                .post()
                .uri(URI.create("http://xxxx/xx/xx/xx.do"))
                // 可加会话 cookie 模拟已经登录环境
                .header("Cookie","xx")
                .retrieve().bodyToMono(String.class);
        System.out.println(baiduMono.block());
    }
}
