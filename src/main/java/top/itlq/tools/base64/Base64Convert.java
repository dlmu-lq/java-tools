package top.itlq.tools.base64;


import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.Base64;

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