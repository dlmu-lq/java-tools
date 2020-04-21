package top.itlq.tools.reactor;

import org.junit.jupiter.api.Test;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.SignalType;

import java.util.concurrent.atomic.LongAdder;

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
     * 不会再走onError的类似异常捕获的方法；
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
     * 出错依然不会继续运行序列
     */
    @Test
    void testOnErrorResumeWith(){
        Flux.just(1,2,3,4,5)
                .flatMap(HandleMessageTest::mockDangerousOperation)
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
     * onErrorResume 继续序列的执行
     * 为了继续序列的执行，将可能出错操作放在flatMap里使用Mono，包装一层onErrorResume
     */
    @Test
    void testOnErrorResume2(){
        Flux.just(1,2,3,4,5).flatMap(k->{
            return mockDangerousOperation(k).onErrorResume(e->{
                // 只捕获该异常并继续序列；
                if(e instanceof IllegalStateException){
                    return Mono.just(-1);
                }
                // 此步相当于重新抛出
                return Mono.error(e);
            });
        }).subscribe(System.out::println, System.err::println);
    }

    /**
     * doOnError 执行后继续 抛出异常 ，可用于日志记录
     */
    @Test
    void testDoOnError(){
        Flux.just(1,2,3,4,5).flatMap(k->{
            return mockDangerousOperation(k).doOnError(e->{
                // 只捕获该异常并继续序列；
                if(e instanceof IllegalStateException){
                    System.err.println("记录异常2 IllegalStateException（我不影响异常的继续抛出）");
                }
            });
        }).subscribe(System.out::println, System.err::println);
    }

    /**
     * doFinally 结束时执行，相当于 try finally，结束原因类型；
     */
    @Test
    void testDoFinally(){
        LongAdder statsCancel = new LongAdder();
        Flux.just(1,2,3,4,5)
                .flatMap(HandleMessageTest::mockDangerousOperation)
                .doOnSubscribe(s->System.out.println("onSubscribe," + System.currentTimeMillis()))
                .doFinally(signalType -> {
                    if(signalType == SignalType.CANCEL){
                        statsCancel.increment();
                        System.out.println("cancel" + System.currentTimeMillis());
                    }
                })
                .take(1)
                .subscribe(System.out::println, System.err::println);
    }

    /**
     * using操作符，对Disposable使用，相当于try-with-resources对AutoClosable 使用；
     */
    @Test
    void testUsing(){
        MyDisposable disposableInstance = new MyDisposable() {

            private boolean isDisposed = false;

            @Override
            public boolean isDisposed(){
                return isDisposed;
            }

            @Override
            public void dispose() {
                System.out.println("disposing myDisposable");
                isDisposed = true;
            }

            @Override
            public String getData(){
                return "DISPOSABLE DATA";
            }
        };
        Flux.using(
                // 实例
                () -> disposableInstance,
                // 产生的序列
                s -> Flux.just(s.getData()),
                // dispose方法
                MyDisposable::dispose
        ).subscribe(System.out::println);
    }

    /**
     * retry,预到错误，放弃原序列，重新执行新序列；
     */
    @Test
    void testRetry(){
        Flux.just(1,2)
                .concatWith(Mono.error(new IllegalStateException()))
                .concatWith(Mono.just(3))
                .retry(2)
                .subscribe(System.out::println);
    }

    /**
     * retryWhen, 预到错误，放弃原序列，重新执行新序列；
     */
    @Test
    void testRetry2(){
        Flux<String> flux = Flux
            .<String>error(new IllegalArgumentException())
            .doOnError(System.out::println)
            .retryWhen(companion -> companion.take(3));
        flux.subscribe();
    }

    /**
     * 模拟的业务可能出错操作
     * @param i
     * @return
     */
    static Mono<Integer> mockDangerousOperation(Integer i){
        Mono<Integer> re;
        if(i == 2){
            re = Mono.error(new IllegalStateException());
        }else if(i == 4){
            re = Mono.error(new IllegalArgumentException());
        }else{
            re = Mono.just(i);
        }
        return re;
    }

    interface MyDisposable extends Disposable{
        /**
         * 模拟可关闭资源的获取数据方法；
         * @return
         */
        String getData();
    }
}
