package com.statistical.time.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

@Entity
public class EventInfo {


    //@Id：主键，通过这个注解标记的字段必须是Long类型的，这个字段在数据库中表示它就是主键，并且它默认就是自增的
    @Id(autoincrement = true)
    public Long id;


    public  float durtion;
    public int  index;
    public  String name;
    public  boolean  isSelect;
    @Generated(hash = 1103613397)
    public EventInfo(Long id, float durtion, int index, String name,
            boolean isSelect) {
        this.id = id;
        this.durtion = durtion;
        this.index = index;
        this.name = name;
        this.isSelect = isSelect;
    }
    @Generated(hash = 1265350165)
    public EventInfo() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
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
    public boolean getIsSelect() {
        return this.isSelect;
    }
    public void setIsSelect(boolean isSelect) {
        this.isSelect = isSelect;
    }
    public float getDurtion() {
        return this.durtion;
    }
    public void setDurtion(float durtion) {
        this.durtion = durtion;
    }
}
