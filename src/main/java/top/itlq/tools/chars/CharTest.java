package top.itlq.tools.chars;

import org.junit.jupiter.api.Test;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.util.Arrays;

public class CharTest {
    /**
     * char占用字节数与编码有关，utf-8下 1-4个字节，也有说只占2个字节的？
     */
    @Test
    void test(){
        System.out.println("中国".length());
        System.out.println(Arrays.toString("中国".toCharArray()));
        System.out.println(Arrays.toString("中国".getBytes()));
        char c = '中';
        System.out.println(getUtf8CharLength(c,Charset.forName("utf-8")));
        System.out.println(getUtf8CharLength('a',Charset.forName("utf-8")));
        System.out.println(getUtf8CharLength(c, Charset.forName("utf-16")));
        System.out.println(getUtf8CharLength('a', Charset.forName("utf-16")));
    }

    static int getUtf8CharLength(char c, Charset charset){
        CharBuffer charBuffer = CharBuffer.allocate(1);
        charBuffer.put(c);
        charBuffer.flip();
        ByteBuffer byteBuffer = charset.encode(charBuffer);
        return byteBuffer.array().length;
    }
}
