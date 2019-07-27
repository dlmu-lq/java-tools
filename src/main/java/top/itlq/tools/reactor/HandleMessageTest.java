package top.itlq.tools.reactor;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Flux流消息处理
 */
public class HandleMessageTest {
    /**
     * subscribe处理正常，错误，完成消息
     * 有错误消息时不再处理下面的流
     */
    @Test
    void testSubscribe(){
        Flux.just(1,2)
                // 可以注释试试
                .concatWith(Mono.error(new IllegalStateException()))
                .concatWith(Mono.just(3))
                .subscribe(System.out::println,System.err::println,
                        ()->System.out.println("completed"));
    }

    /**
     * 出错时返回一个值，但是仍然不会向下进行；
     */
    @Test
    void testOnErrorReturn(){
        Flux.just(1,2)
                .concatWith(Mono.error(new IllegalStateException()))
                .concatWith(Mono.just(3))
                .onErrorReturn(-1)
                .subscribe(System.out::println,System.err::println,
                        System.out::println);
    }

    /**
     * switchOnError没有这个方法
     */
    @Test
    void testSwitchOnError(){
        Flux.just(1,2)
                .concatWith(Mono.error(new IllegalStateException()))
//                .switchOnError
                .subscribe(System.out::println);
    }

    /**
     * onErrorResume
     */
    @Test
    void testOnErrorResumeWith(){
        Flux.just(1,2)
                .concatWith(Mono.error(new IllegalStateException()))
                .concatWith(Mono.just(3))
                .onErrorResume(e->{
                    if(e instanceof IllegalStateException){
                        return Mono.just(-1);
                    }
                    if(e instanceof IllegalArgumentException){
                        return Mono.just(-2);
                    }
                    return Mono.just(-3);
                }).subscribe(System.out::println);
    }

    /**
     * retry,
     */
    @Test
    void testRetry(){
        Flux.just(1,2)
                .concatWith(Mono.error(new IllegalStateException()))
                .concatWith(Mono.just(3))
                .retry(2)
                .subscribe(System.out::println);
    }
}
