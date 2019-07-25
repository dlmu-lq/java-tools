package top.itlq.tools.reactor;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;

import java.awt.*;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class CreateFluxTest {

    /**
     * 静态方法1
     */
    @Test
    void test1(){
        Flux.just("hello","world").subscribe(System.out::println);
        Flux.fromArray(new Integer[]{1,2,3}).subscribe(System.out::println);
        Flux.empty().subscribe(System.out::println);
        Flux.range(1,10).subscribe(System.out::println);
    }

    /**
     * 静态方法2，间隔，延时，多线程
     * @throws InterruptedException
     */
    @Test
    void test2() throws InterruptedException {
        Flux.interval(Duration.of(1, ChronoUnit.SECONDS)).subscribe(System.out::println);
        Flux.interval(Duration.of(500,ChronoUnit.MILLIS),Duration.of(1, ChronoUnit.SECONDS))
                .subscribe(System.out::println);
        TimeUnit.SECONDS.sleep(5);
    }

    /**
     * generate()创建Flux
     */
    @Test
    void test3(){
        Flux.generate(sink->{
            sink.next("Hello");
            sink.complete();
        }).subscribe(System.out::println);

        Random random = new Random();
        Flux.generate(ArrayList::new, (list, sink)->{
            int value = random.nextInt(100);
            list.add(value);
            sink.next(value);
            if(list.size() == 10){
                sink.complete();
            }
            return list;
        }).subscribe(System.out::println);
    }

    /**
     * create() 创建Flux
     */
    @Test
    void test4(){
        Flux.create(fluxSink -> {
            for(int i=0;i<10;i++){
                fluxSink.next(i);
            }
            fluxSink.complete();
        }).subscribe(System.out::println);
    }
}
