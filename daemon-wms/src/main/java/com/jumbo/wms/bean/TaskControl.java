package com.jumbo.wms.bean;

public class TaskControl {

    private static Boolean allTaskIsRun = false;


    public static void on() {
        allTaskIsRun = true;
    }


    public static void off() {
        allTaskIsRun = false;
    }


    public static Boolean isOn() {
        return allTaskIsRun;
    }
}
