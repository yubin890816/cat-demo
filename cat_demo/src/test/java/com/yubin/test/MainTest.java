package com.yubin.test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 测试类
 *
 * @author YUBIN
 */
public class MainTest {

    public static void main(String[] args) throws ParseException {

        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        System.out.println(list.indexOf(1));

        System.out.println(User.class.getSimpleName());
        System.out.println(IpUtil.getLocalIP());
        //BigDecimal a = new BigDecimal("50");
        //BigDecimal b = new BigDecimal("100");
        //System.out.println(a.divide(b,4,BigDecimal.ROUND_DOWN));
        LocalDateTime now = LocalDateTime.now();
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        Date parse = sf.parse("2017-08-08");
        LocalDateTime localDateTime = convertDateToLocalDateTime(parse);
        System.out.println(now.getYear());
        System.out.println(localDateTime.getYear());

        System.out.println("year" + (now.getYear() - localDateTime.getYear()));
        System.out.println(now.getMonthValue() - localDateTime.getMonthValue());
        int m = (now.getYear() - localDateTime.getYear()) * 12 + (now.getMonthValue() - localDateTime.getMonthValue());
        System.out.println(m);
    }

    public static LocalDateTime convertDateToLocalDateTime(Date d) {
        return  LocalDateTime.ofInstant(d.toInstant(), ZoneId.systemDefault());
    }
}
