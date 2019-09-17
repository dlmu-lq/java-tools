package top.itlq.tools.reactor;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

/**
 * 测试Flux的一些方法
 */
public class FluxTest {

    @Test
    void testAsync(){
        String dir = "src/main/java/top/itlq/tools/reactor/";
        // 非异步操作
        Flux.fromIterable(Arrays.asList(1,2,3))
                .subscribe(i -> {
                    System.out.println("writing " + i);
                    try {
                        TimeUnit.SECONDS.sleep(2);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    try (FileOutputStream outputStream = new FileOutputStream(dir + i)){
                        int a = 0;
                        while (a < 100){
                            outputStream.write((a + " ").getBytes());
                            a++;
                        }
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    System.out.println(i + "written");
                });
    }

    @Test
    void testAsync2(){
        String dir = "src/main/java/top/itlq/tools/reactor/";
        // 非异步操作
        Flux.fromIterable(Arrays.asList(1,2,3))
                .flatMap(i ->{
                    Flux.just(1,2,3).flatMap(b -> {
                        System.out.println("writing " + i + " " + b);
                        try {
                            TimeUnit.SECONDS.sleep(2);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        try (FileOutputStream outputStream = new FileOutputStream(dir + i)){
                            int a = 0;
                            while (a < 100){
                                outputStream.write((a + " ").getBytes());
                                a++;
                            }
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        System.out.println(i + "written");
                        return Mono.just(true);
                    }).subscribe();
                    System.out.println("/" + i);
                    return Mono.just(i);
                }).subscribe(System.out::println);
    }

    @Test
    void testReduce(){
        Flux.fromIterable(Arrays.asList(1,2,3))
                .reduce("", (a, b) -> a + b)
                .subscribe(System.out::println);

        Flux.fromIterable(Arrays.asList(1,2,3))
                .reduce((a, b) -> 1)
                .subscribe(System.out::println);
    }

    /**
     * 测试filter，执行顺序
     */
    @Test
    void testFilter(){
        Flux.just(1,2,3)
                .flatMap(i -> {
                    System.out.println("1:" + i);
                    return Mono.just(i);
                })
                .flatMap(i -> {
                    System.out.println("2:" + i);
                    return Mono.just(i);
                })
                .filter(p -> p < 0)
                .subscribe(System.err::println);
    }
}
