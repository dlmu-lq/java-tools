package top.itlq.tools.json;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class TestJson {
    @Test
    void test(){
        System.out.println(JSONObject.parseObject("{\"name\":\"lee\",\"age\":1,\"birth\":1}",User.class));
    }
    @Test
    void test2() throws IOException {
        System.out.println(new ObjectMapper().readValue("{\"name\":\"lee\",\"age\":1,\"birth\":1}",User.class));
    }
}
