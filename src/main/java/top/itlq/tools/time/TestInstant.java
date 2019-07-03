package top.itlq.tools.time;

import org.junit.jupiter.api.Test;

import java.time.*;
import java.time.format.DateTimeFormatter;

class TestInstant {

    /**
     * Instant 与 LocalDateTime
     *
     *
     */
    @Test
    void test1(){
        System.out.println(Instant.now());
        System.out.println(LocalDateTime.ofInstant(Instant.now(),ZoneId.systemDefault()));
        System.out.println(LocalDateTime.ofInstant(Instant.now(),ZoneOffset.UTC));
        System.out.println(LocalDateTime.now(ZoneOffset.UTC));
        System.out.println(LocalDateTime.now(ZoneOffset.UTC).toInstant(ZoneOffset.UTC));
        System.out.println(LocalDateTime.now().toInstant(ZoneOffset.UTC)); // 时间显示不变，时区加UTC
    }

    /**
     * Instant 与 ZonedDateTime
     */
    @Test
    void test4(){
        ZoneId zoneId = ZoneId.of("Asia/Shanghai");
        System.out.println(ZonedDateTime.ofInstant(Instant.now(),zoneId).getHour());
        System.out.println(ZonedDateTime.ofInstant(Instant.now(),zoneId).withHour(8).toInstant());
        System.out.println(Instant.now());
    }

    /**
     * LocalDateTime 不带时区，可转为带时区的ZoneDateTime
     */
    @Test
    void test2(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHH");
        Integer batch = 2019070300;
        LocalDateTime localDateTime = LocalDateTime.parse(batch.toString(), formatter);
        System.out.println(localDateTime);
        System.out.println(localDateTime.atZone(ZoneId.systemDefault()));
        System.out.println(localDateTime.atZone(ZoneOffset.UTC));
    }

    /**
     * ZoneDateTime 带时区，显示相同转时区，时间相同转时区
     */
    @Test
    void test3(){
        LocalDateTime localDateTime = LocalDateTime.of(2019,7,3,0,0);
        System.out.println(localDateTime.atZone(ZoneOffset.UTC));
        System.out.println(localDateTime.atZone(ZoneOffset.UTC).withZoneSameLocal(ZoneId.systemDefault()));
        System.out.println(localDateTime.atZone(ZoneOffset.UTC).withZoneSameInstant(ZoneId.systemDefault()));
    }


}
