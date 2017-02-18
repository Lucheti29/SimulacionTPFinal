package com.github.luksdlt92.simulacion.constant;

public class Technology {

    public static final int ANDROID = 0;
    public static final int IOS = 1;
    public static final int WEB = 2;

    public static final int getValue(String technology) {
        if (technology.equalsIgnoreCase("android")) return ANDROID;
        else if (technology.equalsIgnoreCase("ios")) return IOS;
        else if (technology.equalsIgnoreCase("web")) return WEB;

        return -1;
    }
}
