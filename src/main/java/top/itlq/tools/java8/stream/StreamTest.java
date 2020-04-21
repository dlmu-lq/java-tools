package top.itlq.tools.java8.stream;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamTest {

    /**
     * 数组转Stream
     * flatMap
     */
    @Test
    void test1(){
        List<Integer[]> list=new ArrayList<>();
        Integer[] int1 = {1, 2, 3, 4, 5};
        Integer[] int2 = {3, 4, 3, 6, 8};
        list.add(int1);
        list.add(int2);

        System.out.println(
                list.stream()
                        .flatMap(Stream::of)
                        .reduce(0, Integer::sum)
        );
    }

    /**
     * 基本类型数组转Stream使用 IntStream, DoubleStrem等
     * flatMapToInt
     */
    @Test
    void test2(){
        List<int[]> list=new ArrayList<>();
        int[] int1 = {1, 2, 3, 4, 5};
        int[] int2 = {3, 4, 3, 6, 8};
        list.add(int1);
        list.add(int2);

        System.out.println(
                list.stream()
                        .map(Arrays::stream)
                        .map(intStream -> intStream.reduce(0,Integer::sum))
                        .reduce(0, Integer::sum)
        );

        System.out.println(
                list.stream()
                        .flatMapToInt(Arrays::stream)
                        .reduce(0, Integer::sum)
        );
    }

    @Test
    void testHowItWorks(){
        Stream.of(1,2,3)
                .filter(n->{
                    System.out.println("filter1:" + n);
                    return true;
                })
                .filter(n->{
                    System.out.println("filter2:" + n);
                    return true;
                }).collect(Collectors.toList());
    }
}
