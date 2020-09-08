package com.statistical.time.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class CityInfo {


    /**
     * id : 1
     * pid : 0
     * city_code : 101010100
     * city_name : 北京
     * post_code : 100000
     * area_code : 010
     * ctime : 2019-07-11 17:30:06
     */

    private int id;
    private int pid;
    private String city_code;
    private String city_name;
    private String post_code;
    private String area_code;
    private String ctime;
    //@Id：主键，通过这个注解标记的字段必须是Long类型的，这个字段在数据库中表示它就是主键，并且它默认就是自增的
    @Id(autoincrement = true)
    public Long cityId;
    @Generated(hash = 2042257405)
    public CityInfo(int id, int pid, String city_code, String city_name,
            String post_code, String area_code, String ctime, Long cityId) {
        this.id = id;
        this.pid = pid;
        this.city_code = city_code;
        this.city_name = city_name;
        this.post_code = post_code;
        this.area_code = area_code;
        this.ctime = ctime;
        this.cityId = cityId;
    }

    @Generated(hash = 300452937)
    public CityInfo() {
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public String getCity_code() {
        return city_code;
    }

    public void setCity_code(String city_code) {
        this.city_code = city_code;
    }

    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

    public String getPost_code() {
        return post_code;
    }

    public void setPost_code(String post_code) {
        this.post_code = post_code;
    }

    public String getArea_code() {
        return area_code;
    }

    public void setArea_code(String area_code) {
        this.area_code = area_code;
    }

    public String getCtime() {
        return ctime;
    }

    public void setCtime(String ctime) {
        this.ctime = ctime;
    }

    public Long getCityId() {
        return this.cityId;
    }

    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }

    @Override
    public String toString() {
        return "CityInfo{" +
                "id=" + id +
                ", pid=" + pid +
                ", city_code='" + city_code + '\'' +
                ", city_name='" + city_name + '\'' +
                ", post_code='" + post_code + '\'' +
                ", area_code='" + area_code + '\'' +
                ", ctime='" + ctime + '\'' +
                ", cityId=" + cityId +
                '}';
    }
}
