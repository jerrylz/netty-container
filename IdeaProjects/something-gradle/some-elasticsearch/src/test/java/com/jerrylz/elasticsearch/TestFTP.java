package com.jerrylz.elasticsearch;

import cn.hutool.extra.ftp.Ftp;

/**
 * @author jerrylz
 * @date 2020/9/2
 */
public class TestFTP {
    public static void main(String[] args) {
        Ftp ftp = new Ftp("192.168.1.5", 21);
        boolean testFtp = ftp.cd("ylz");
        System.out.println(testFtp);
    }
}
