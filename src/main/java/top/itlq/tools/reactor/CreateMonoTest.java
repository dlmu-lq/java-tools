package top.itlq.tools.reactor;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;

import java.util.Optional;

public class CreateMonoTest {
    /**
     * justOrEmpty静态方法，其他 just等也有
     */
    @Test
    void test1(){
        Mono.justOrEmpty("Hello").subscribe(System.out::println);
        Mono.justOrEmpty(Optional.empty()).subscribe(System.out::println);
        Mono.justOrEmpty(Optional.of("Hello")).subscribe(System.out::println);
        Integer a = null;
        Mono.justOrEmpty(a).subscribe(System.out::println);
    }

    /**
     * fromSupplier
     */
    @Test
    void test2(){
        Mono.fromSupplier(()->"Hello").subscribe(System.out::println);
    }

    /**
     * create
     */
    @Test
    void test3(){
        Mono.create(monoSink -> monoSink.success("World")).subscribe(System.out::println);
    }
}
