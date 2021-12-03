package com.example.pms;

import android.app.Application;

public class GlobalVar extends Application {
    private static String IP;
    private static int PORT;

    public String getIP() {
        return IP;
    }

    public void setIP(String IP) {
        this.IP = IP;
    }

    public int getPORT() {
        return PORT;
    }

    public void setPORT(int PORT) {
        this.PORT = PORT;
    }
}
