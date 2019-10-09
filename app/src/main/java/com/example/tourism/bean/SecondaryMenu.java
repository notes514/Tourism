package com.example.tourism.bean;

public class SecondaryMenu {

    public int menu_pic;
    public String menu_name;

    public SecondaryMenu(int menu_pic, String menu_name) {
        this.menu_pic = menu_pic;
        this.menu_name = menu_name;
    }

    public int getMenu_pic() {
        return menu_pic;
    }

    public void setMenu_pic(int menu_pic) {
        this.menu_pic = menu_pic;
    }

    public String getMenu_name() {
        return menu_name;
    }

    public void setMenu_name(String menu_name) {
        this.menu_name = menu_name;
    }
}
