package top.itlq.tools.reactor;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Hooks;
import reactor.core.publisher.Mono;

public class DebuggerTest {
    @Test
    void testHooks(){
        Hooks.onOperatorDebug();
        Flux.just(1).concatWith(Mono.error(new Exception("a")))
                .subscribe(System.out::println);
    }
}
