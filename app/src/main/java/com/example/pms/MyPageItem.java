package com.example.pms;

public class MyPageItem {
    private int ImgResource;
    private String InfoTitle;
    private String Info;
    private int divColor;

    public void setImgResource(int imgResource) {
        ImgResource = imgResource;
    }

    public void setInfoTitle(String infoTitle) {
        InfoTitle = infoTitle;
    }

    public void setInfo(String info) {
        Info = info;
    }

    public void setDivColor(int divcolor) {
        divColor = divcolor;
    }

    public int getDivColor() {
        return this.divColor;
    }

    public String getInfoTitle() {
        return this.InfoTitle;
    }

    public String getInfo() {
        return this.Info;
    }

    public int getImgResource() {
        return this.ImgResource;
    }
}
