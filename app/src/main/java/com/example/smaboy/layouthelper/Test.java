package com.example.smaboy.layouthelper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 类名: Test
 * 类作用描述: java类作用描述
 * 作者: Smaboy
 * 创建时间: 2018/12/3 10:10
 */
public class Test {
    public static void main(String args[]) throws IOException {
        char c;
        // 使用 System.in 创建 BufferedReader
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("输入字符串, 当含有字符'q' 退出。");
        // 读取字符
        String s;
        do {
            s = br.readLine();
            System.out.println(s);
        } while (!s.contains("q"));
    }
}
