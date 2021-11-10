package com.example.pms;

public class SettingItem {
    private boolean status;
    private String SettingTitle;

   public SettingItem(String SettingTitle, boolean status){
       this.SettingTitle = SettingTitle;
       this.status = status;
   }

   public String getSettingTitle(){
       return SettingTitle;
   }

   public void setSettingTitle(String SettingTitle){
       this.SettingTitle = SettingTitle;
   }

   public boolean isStatus(){
       return status;
   }

   public void setStatus(boolean status){
       this.status = status;
   }
}
