package top.itlq.tools.reactor;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

public class MonoTest {

    @Test
    void testMonoBlock(){
        Mono<Boolean> mono = Flux.fromIterable(Arrays.asList(1,2,3))
                .flatMap(i -> {
                    System.out.println("mono0" + i);
                    try {
                        TimeUnit.MILLISECONDS.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("mono" + i);
                    return Mono.just(true);
                })
                .all(Boolean::booleanValue);
        System.out.println("out 1");
        mono.block();
        System.out.println("out 2");;
    }
}
