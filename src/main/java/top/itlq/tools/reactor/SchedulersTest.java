package top.itlq.tools.reactor;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

/**
 * 调度器测试
 */
public class SchedulersTest {
    /**
     * subscribeOn 直接影响到request到的序列产生源头至第一个publishOn，publishOn影响到后续逻辑流程至下一个publishOn；
     */
    @Test
    void test(){
        Flux.generate(sink->{
            sink.next(Thread.currentThread().getName());
            sink.complete();
        }).publishOn(Schedulers.single())
                .map(x->String.format("[%s] %s",Thread.currentThread().getName(), x))
                .publishOn(Schedulers.elastic())
                .map(x->String.format("[%s] %s",Thread.currentThread().getName(),x))
                .subscribeOn(Schedulers.parallel())
                .toStream().forEach(System.out::println);
    }
}
