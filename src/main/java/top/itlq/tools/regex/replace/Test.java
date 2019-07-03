package top.itlq.tools.regex.replace;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test {
    public static void main(String... args) throws Exception {

        BufferedReader br = new BufferedReader(new FileReader("src/main/java/top/itlq/tools/replace/test"));
        BufferedWriter bw = new BufferedWriter(new FileWriter("src/main/java/top/itlq/tools/replace/re"));
        String a;
        StringBuffer sb1 = new StringBuffer();
        while ((a = br.readLine()) != null) {

            a = a.replaceAll(" ", "");
            if (a.startsWith("fname:")) {
                BufferedReader template = new BufferedReader(new FileReader("src/main/java/top/itlq/tools/replace/template"));
                String templateLine;

                while ((templateLine = template.readLine()) != null) {
                    templateLine = templateLine.replaceAll(" ", "");
                    Matcher matcher = Pattern.compile("(.*?):\\{\\{(.*?)\\}\\}").matcher(templateLine);
                    Matcher matcherLine = Pattern.compile("fname:\"(.*?)\"").matcher(a);
                    StringBuffer sbNew = new StringBuffer();
                    while (matcherLine.find()) {
                        while (matcher.find()) {
                            if (matcherLine.group(1).equals(matcher.group(1))) {

                                matcherLine.appendReplacement(sbNew, "fname:\"" + matcher.group(2) + "\",");

                            }
                        }
                    }
                    matcherLine.appendTail(sbNew);
                    a = sbNew.toString();
                }
            }
            bw.write(a+ "\r\n");
        }
        bw.flush();
        bw.close();
//        String string = sb1.toString();
////        Matcher matcher = Pattern.compile(">\\s+<").matcher(string);
////
////        while (matcher.find()) {
////            matcher.appendReplacement(sb, "><");
////        }
//        matcher.appendTail(sb);



    }
}
