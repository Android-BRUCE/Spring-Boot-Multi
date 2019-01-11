package com.highcharts.common.base;

import java.io.BufferedReader;
import java.io.FileReader;

/**
 * @program: Spring-Boot-Multi
 * @description: ccc
 * @author: Brucezheng
 * @create: 2018-03-30 08:46
 **/
public class Card {

    public static void main(String[] args) {
        try {
            FileReader file = new FileReader("D:\\360MoveData\\Users\\Administrator.SKY-20170531RMO\\Desktop\\card.txt");
            BufferedReader bufferedReader = new BufferedReader(file);
            String temp = null;

            while ((temp = bufferedReader.readLine()) != null) {
                //  StringBuffer(temp).reverse().toString();
               // temp = new StringBuffer(temp).reverse().toString().substring(0, 8);
                StringBuffer temp2 = new StringBuffer();
                for (int i = 0; i <4 ; i++) {
                    String substring = new StringBuffer(temp).substring(temp.length() - i * 2 - 2, temp.length() - i * 2);
                    temp2=temp2.append(substring);
                }
               // System.out.println(temp2);
                long l = Long.parseLong(temp2.toString(), 16);
                System.out.println(l);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
























