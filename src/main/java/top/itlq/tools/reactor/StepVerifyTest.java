package top.itlq.tools.reactor;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;
import reactor.test.publisher.TestPublisher;

import java.awt.*;
import java.time.Duration;
import java.time.temporal.ChronoUnit;

public class StepVerifyTest {
    @Test
    void testExpectNext(){
        StepVerifier.create(Flux.just("a","b"))
                .expectNext("a")
                .expectNext("b")
//                .expectNext("c")
                .verifyComplete();
    }

    @Test
    void testVirtualTime(){
        StepVerifier.withVirtualTime(()->Flux.interval(
                Duration.of(4, ChronoUnit.HOURS),
                Duration.of(1, ChronoUnit.DAYS)
        ).take(2))
                // 必须
                .expectSubscription()
                .expectNoEvent(Duration.ofHours(4))
                .expectNext(0L)
                .thenAwait(Duration.ofDays(1))
                .expectNext(1L)
                .verifyComplete();
    }

    @Test
    void testTestPublisher(){
        TestPublisher<String> testPublisher = TestPublisher.create();
        testPublisher.next("a");
        testPublisher.next("b");
        testPublisher.complete();
        StepVerifier.create(testPublisher).expectNext("a").expectNext("b")
                // 不好用
//                .verifyComplete()
                .expectComplete();
    }
}
