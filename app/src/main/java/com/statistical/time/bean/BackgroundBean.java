package com.statistical.time.bean;

public class BackgroundBean  {
  public   String  theme;
    public int index;

    public BackgroundBean( String theme,int  index) {
        this.theme = theme;
        this.index =index;
    }

    @Override
    public String toString() {
        return "BackgroundBean{" +
                ", theme='" + theme + '\'' +
                '}';
    }
}
