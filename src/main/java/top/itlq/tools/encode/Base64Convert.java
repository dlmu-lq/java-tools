package top.itlq.tools.encode;


import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.Base64;

/**
 * base64的意义，字节存储数据，一个字节256个字符不能完全显示，将三个字节 24位 编码为 每 6 位显示一个字符 变成 四个字节即可将数据以可见字符形式显示
 */
public class Base64Convert {
    @Test
    public void test(){
        Base64.Decoder decoder = Base64.getDecoder();
        try {
            BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream("src/main/resources/tools/test.png"));
            BufferedReader br = new BufferedReader(new FileReader("src/main/resources/tools/base64/base64string"));

            String a;
            StringBuffer sb = new StringBuffer();
            while ((a = br.readLine()) != null){
                sb.append(a);
            }
            try{
                out.write(decoder.decode(sb.toString()));
            }finally {
                out.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Test
    public void test2(){
        System.out.println(new String(Base64.getEncoder().encode("".getBytes())));
    }
    @Test
    public void test3(){
        System.out.println(new String(Base64.getDecoder().decode("".getBytes())));
    }
}
