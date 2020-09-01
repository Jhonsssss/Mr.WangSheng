package com.statistical.time.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

import java.io.Serializable;

/**
 * @author Administrator
 */
@Entity
public class WishInfo implements Serializable {


    public  static final long serialVersionUID=1L;
    //@Id：主键，通过这个注解标记的字段必须是Long类型的，这个字段在数据库中表示它就是主键，并且它默认就是自增的
    @Id(autoincrement = true)
    public Long id;


    public String wishName;
    public int wishYear;
    public int wishMonth;
    public int wishDay;
    public int style;
    public boolean isFinish;
    public String theme;
    public int index;
    public  String  finishTime;
    public  String  createTime;
    public String name;


    @Generated(hash = 1565233952)
    public WishInfo(Long id, String wishName, int wishYear, int wishMonth,
            int wishDay, int style, boolean isFinish, String theme, int index,
            String finishTime, String createTime, String name) {
        this.id = id;
        this.wishName = wishName;
        this.wishYear = wishYear;
        this.wishMonth = wishMonth;
        this.wishDay = wishDay;
        this.style = style;
        this.isFinish = isFinish;
        this.theme = theme;
        this.index = index;
        this.finishTime = finishTime;
        this.createTime = createTime;
        this.name = name;
    }

    @Generated(hash = 1945106750)
    public WishInfo() {
    }


    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getWishName() {
        return this.wishName;
    }

    public void setWishName(String wishName) {
        this.wishName = wishName;
    }

    public int getWishYear() {
        return this.wishYear;
    }

    public void setWishYear(int wishYear) {
        this.wishYear = wishYear;
    }

    public int getWishMonth() {
        return this.wishMonth;
    }

    public void setWishMonth(int wishMonth) {
        this.wishMonth = wishMonth;
    }

    public int getWishDay() {
        return this.wishDay;
    }

    public void setWishDay(int wishDay) {
        this.wishDay = wishDay;
    }

    public int getStyle() {
        return this.style;
    }

    public void setStyle(int style) {
        this.style = style;
    }

    public boolean getIsFinish() {
        return this.isFinish;
    }

    public void setIsFinish(boolean isFinish) {
        this.isFinish = isFinish;
    }

    public String getTheme() {
        return this.theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public int getIndex() {
        return this.index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFinishTime() {
        return this.finishTime;
    }

    public void setFinishTime(String finishTime) {
        this.finishTime = finishTime;
    }

    public String getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
