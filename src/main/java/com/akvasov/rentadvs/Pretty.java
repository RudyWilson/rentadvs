package com.akvasov.rentadvs;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by alex on 18.03.15.
 */
public class Pretty {

    public static void main(String[] args) {
        String str = "17099<!><!>0<!>6597<!>0<!>{\"all\":[['4833519','http://cs624922.vk.me/v624922519/1698b/1KN4eOPT1VU.jpg','/akvasov','2','0','Александр Квасов','0','1','0','']\n" +
                ",['27905722','http://cs419123.vk.me/v419123722/41b6/54JwJIf-7pw.jpg','/kirico','2','0','Кирилл Пикассошенков','0','1','709','']\n" +
                ",['295598743','http://cs623226.vk.me/v623226743/20358/LrXlAzDWN7Q.jpg','/id295598743','1','0','Селестина Станкович','0','1','0','']]}<!><!json>{\"0\":null,\"709\":\"ОГУ\",\"-46341014\":\"<a mention mention_id=\\\"club46341014\\\" onmouseover=\\\"mentionOver(this);\\\" hr";

        Pattern pattern = Pattern.compile("(.*)(\\{.*\\})(.*)");
        Matcher matcher = pattern.matcher(str);
        boolean matches = matcher.matches();
        System.out.println("matches = " + matches);
        System.out.println("matcher.group(2) = " + matcher.group(2));
    }

}
