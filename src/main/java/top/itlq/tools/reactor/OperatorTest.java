package top.itlq.tools.reactor;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;

/**
 * 反应流的操作符
 */
public class OperatorTest {
    /**
     * buffer bufferTimeout bufferUntil bufferWhile
     */
    @Test
    void testBuffer(){
        // 5个20个元素的集合的新序列；
        Flux.range(0,100).buffer(20).subscribe(System.out::println);
        // 两个含有十个元素的集合的序列，toStream而不是直接打印是防止主线程结束导致异步操作不能完成；
        // toStream为同步，会阻塞主线程；
        Flux.interval(Duration.of(100, ChronoUnit.MILLIS))
                .buffer(Duration.of(1050,ChronoUnit.MILLIS))
                .take(2).toStream().forEach(System.out::println);
        // 只能收集8个元素，虽然时间充足
        Flux.interval(Duration.of(100, ChronoUnit.MILLIS))
                .bufferTimeout(8,Duration.of(1050,ChronoUnit.MILLIS))
                .take(1).toStream().forEach(System.out::println);
        // 只能收集10个元素，虽然maxSize为12
        Flux.interval(Duration.of(100, ChronoUnit.MILLIS))
                .bufferTimeout(12,Duration.of(1050,ChronoUnit.MILLIS))
                .take(1).toStream().forEach(System.out::println);
        // 5个两个元素的集合的序列，遇到偶数收集下一个集合
        Flux.range(1,10).bufferUntil(i->i%2==0).subscribe(System.out::println);
        // 5个含有一个元素（偶数）集合的序列，遇到偶数才收集，遇到false收集下一个
        Flux.range(1,10).bufferWhile(i->i%2==0).subscribe(System.out::println);
        // 4个集合的序列
        Flux.range(1,10).bufferWhile(i->i%3!=0).subscribe(System.out::println);
    }

    /**
     * filter
     */
    @Test
    void testFilter(){
        Flux.range(1,10).filter(i->i%2==0).subscribe(System.out::println);
    }

    /**
     * window
     */
    @Test
    void testWindow(){
        Flux.range(1,100).window(20).subscribe(System.out::println);
        Flux.interval(Duration.of(100,ChronoUnit.MILLIS))
                .window(Duration.of(1050,ChronoUnit.MILLIS)).take(2)
                .toStream().forEach(System.out::println);
    }

    /**
     * zipWith
     */
    @Test
    void testZipWith(){
        // zipWith，不处理结合，Turple2流
        Flux.just("a","b").zipWith(Flux.just("c","d"))
                .subscribe(System.out::println);
        // zipWith, 处理为String流
        Flux.just("a","b").zipWith(Flux.just("c","d"),(s1,s2)->String.format("%s-%s",s1,s2))
                .subscribe(System.out::println);
    }

    /**
     * take
     */
    @Test
    void testTake(){
        Flux.range(1,1000).take(10).subscribe(System.out::println);
        Flux.range(1,1000).takeLast(10).subscribe(System.out::println);
        // 1-11
        Flux.range(1,1000).takeUntil(i->i>10).subscribe(System.out::println);
        // 遇到false即停止收集
        Flux.range(1,1000).takeWhile(i->i%100==0).subscribe(System.out::println);
        Flux.range(1,1000).takeWhile(i->i<10).subscribe(System.out::println);
    }

    /**
     * reduce reduceWith
     */
    @Test
    void testReduce(){
        Flux.range(1,100).reduce((a,b)->a+b).subscribe(System.out::println);
        Flux.range(1,100).reduceWith(()->10,(a,b)->a+b).subscribe(System.out::println);
    }

    /**
     * merge mergeSequential
     */
    @Test
    void testMerge(){
        // 按照产生顺序
        Flux.merge(
                Flux.interval(Duration.of(100,ChronoUnit.MILLIS)).take(5),
                Flux.interval(Duration.of(50,ChronoUnit.MILLIS),Duration.of(100,ChronoUnit.MILLIS)).take(5))
                .toStream().forEach(System.out::println);
        // 按照订阅顺序
        Flux.mergeSequential(
                Flux.interval(Duration.of(100,ChronoUnit.MILLIS)).take(5),
                Flux.interval(Duration.of(50,ChronoUnit.MILLIS),Duration.of(100,ChronoUnit.MILLIS)).take(5))
                .toStream().forEach(System.out::println);
    }

    /**
     * flatMap flatMapSequential
     */
    @Test
    void testFlatMap(){
        Flux.just(5,10).flatMap(x->
                Flux.interval(
                        Duration.of(x*10,ChronoUnit.MILLIS),
                        Duration.of(100,ChronoUnit.MILLIS)
                ).take(x)
        ).toStream().forEach(System.out::println);
        Flux.just(5,10).flatMapSequential(x->
                Flux.interval(
                        Duration.of(x*10,ChronoUnit.MILLIS),
                        Duration.of(100,ChronoUnit.MILLIS)
                ).take(x)
        ).toStream().forEach(System.out::println);
    }

    /**
     * concatMap，合并流，动态订阅；
     */
    @Test
    void testConcatMap(){
        // 结果与flatMapSequential相同，但是动态订阅；后者是合并流之前已订阅所有的流；
        Flux.just(5,10).concatMap(x->
                Flux.interval(
                        Duration.of(x*10,ChronoUnit.MILLIS),
                        Duration.of(100,ChronoUnit.MILLIS)
                ).take(x)
        ).toStream().forEach(System.out::println);
    }

    /**
     * combineLatest
     */
    @Test
    void testCombineLatest(){
        Flux.combineLatest(Arrays::toString,
                Flux.interval(Duration.of(100,ChronoUnit.MILLIS)).take(5),
                Flux.interval(
                        Duration.of(50,ChronoUnit.MILLIS),
                        Duration.of(100,ChronoUnit.MILLIS)
                ).take(5)
        ).toStream().forEach(System.out::println);
    }
}
