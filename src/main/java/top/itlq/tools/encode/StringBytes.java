package top.itlq.tools.encode;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

/**
 * 可见字符串字节存储
 */
public class StringBytes {

    /**
     * 在ascii编码下，一个英文占一个字节，一个中文两个字节
     * unicode 一个英文字符一个中文字符均为两个个字节 （不能表示所有，有四个字节版本）
     * utf-8 一个英文字符一个字节，一个中文三个字节；是unicode的一种实现方式；用头几位表示字符占字节数，以防止英文字符占用两个字节造成浪费；
     */
    @Test
    void test1(){
        // 字符的字节表示
        System.out.println(Arrays.toString("梁".getBytes()));
        // 字节数组显示对应字符
        byte [] bytes = new byte[]{-26, -94, -127};
        System.out.println(new String(bytes));

        byte [] bytes2 = new byte[]{-26, -95, -127};
        System.out.println(new String(bytes2));
    }
}
