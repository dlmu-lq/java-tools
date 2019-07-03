package top.itlq.tools.regex.withDraw;

import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 利用正则表达式从string中提取想要的东西
 */
public class WithdrawStrings {

    /**
     * 一个从echarts包里拿到的json （geojson形式） ，从中获取中国的省份名称；
     */
    @Test
    void getProvincesFromChinaJson(){
        String path = new WithdrawStrings().getClass().getResource("/tools").getPath();
        System.out.println(path);
        try {
            BufferedReader br = new BufferedReader(new FileReader(path + "/regex/withDraw/china.json"));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null){
                sb.append(line);
            }
            Pattern pattern = Pattern.compile("\"name\".*?\"(.*?)\"");
            Matcher matcher = pattern.matcher(sb.toString());
            List<String> provinces = new ArrayList<>();
            int i = 0;
            while (matcher.find()){
                provinces.add("{\"name\":\"" + matcher.group(1) + "\",\"value\":" + i++ + "}");
            }
            System.out.println(provinces);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
