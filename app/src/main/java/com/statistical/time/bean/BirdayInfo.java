package com.statistical.time.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class BirdayInfo {

    
    //@Id：主键，通过这个注解标记的字段必须是Long类型的，这个字段在数据库中表示它就是主键，并且它默认就是自增的
    @Id(autoincrement = true)
    public Long id;


    public int  year;
    public  int max_age;
    public  int month;
    public  int  day;
    public  int  hour;
    public int  minute;
    public int  secend;
    boolean  isLunar;
    @Generated(hash = 234191327)
    public BirdayInfo(Long id, int year, int max_age, int month, int day, int hour,
            int minute, int secend, boolean isLunar) {
        this.id = id;
        this.year = year;
        this.max_age = max_age;
        this.month = month;
        this.day = day;
        this.hour = hour;
        this.minute = minute;
        this.secend = secend;
        this.isLunar = isLunar;
    }
    @Generated(hash = 1263827919)
    public BirdayInfo() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public int getYear() {
        return this.year;
    }
    public void setYear(int year) {
        this.year = year;
    }
    public int getMax_age() {
        return this.max_age;
    }
    public void setMax_age(int max_age) {
        this.max_age = max_age;
    }
    public int getMonth() {
        return this.month;
    }
    public void setMonth(int month) {
        this.month = month;
    }
    public int getDay() {
        return this.day;
    }
    public void setDay(int day) {
        this.day = day;
    }
    public int getHour() {
        return this.hour;
    }
    public void setHour(int hour) {
        this.hour = hour;
    }
    public int getMinute() {
        return this.minute;
    }
    public void setMinute(int minute) {
        this.minute = minute;
    }
    public int getSecend() {
        return this.secend;
    }
    public void setSecend(int secend) {
        this.secend = secend;
    }
    public boolean getIsLunar() {
        return this.isLunar;
    }
    public void setIsLunar(boolean isLunar) {
        this.isLunar = isLunar;
    }
}
