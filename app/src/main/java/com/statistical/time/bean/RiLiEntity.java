package com.statistical.time.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class RiLiEntity {
    /**
     * id : 3805
     * yangli : 2020-09-03
     * yinli : 庚子(鼠)年七月十六
     * wuxing : 大驿土 除执位
     * chongsha : 冲兔(癸卯)煞东
     * baiji : 己不破券二比并亡 酉不宴客醉坐颠狂
     * jishen : 天德 时德 官日 吉期 除神 鸣犬
     * yi : 祭祀 解除 沐浴 理发 整手足甲 入殓 移柩 破土 安葬 扫舍
     * xiongshen : 大时 大败 咸池 九坎 九焦 往亡 五离 元武
     * ji : 嫁娶 会亲友 进人口 出行  入宅 移徙 赴任 作灶
     */
    //@Id：主键，通过这个注解标记的字段必须是Long类型的，这个字段在数据库中表示它就是主键，并且它默认就是自增的
    @Id(autoincrement = true)
    public Long idSelf;
    public String id;
    public String yangli;
    public String yinli;
    public String wuxing;
    public String chongsha;
    public String baiji;
    public String jishen;
    public String yi;
    public String xiongshen;
    public String ji;
    public int  year ;
    public int  month ;
    public  int day;
    @Generated(hash = 1670613856)
    public RiLiEntity(Long idSelf, String id, String yangli, String yinli,
            String wuxing, String chongsha, String baiji, String jishen, String yi,
            String xiongshen, String ji, int year, int month, int day) {
        this.idSelf = idSelf;
        this.id = id;
        this.yangli = yangli;
        this.yinli = yinli;
        this.wuxing = wuxing;
        this.chongsha = chongsha;
        this.baiji = baiji;
        this.jishen = jishen;
        this.yi = yi;
        this.xiongshen = xiongshen;
        this.ji = ji;
        this.year = year;
        this.month = month;
        this.day = day;
    }
    @Generated(hash = 877418057)
    public RiLiEntity() {
    }
    public Long getIdSelf() {
        return this.idSelf;
    }
    public void setIdSelf(Long idSelf) {
        this.idSelf = idSelf;
    }
    public String getId() {
        return this.id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getYangli() {
        return this.yangli;
    }
    public void setYangli(String yangli) {
        this.yangli = yangli;
    }
    public String getYinli() {
        return this.yinli;
    }
    public void setYinli(String yinli) {
        this.yinli = yinli;
    }
    public String getWuxing() {
        return this.wuxing;
    }
    public void setWuxing(String wuxing) {
        this.wuxing = wuxing;
    }
    public String getChongsha() {
        return this.chongsha;
    }
    public void setChongsha(String chongsha) {
        this.chongsha = chongsha;
    }
    public String getBaiji() {
        return this.baiji;
    }
    public void setBaiji(String baiji) {
        this.baiji = baiji;
    }
    public String getJishen() {
        return this.jishen;
    }
    public void setJishen(String jishen) {
        this.jishen = jishen;
    }
    public String getYi() {
        return this.yi;
    }
    public void setYi(String yi) {
        this.yi = yi;
    }
    public String getXiongshen() {
        return this.xiongshen;
    }
    public void setXiongshen(String xiongshen) {
        this.xiongshen = xiongshen;
    }
    public String getJi() {
        return this.ji;
    }
    public void setJi(String ji) {
        this.ji = ji;
    }
    public int getYear() {
        return this.year;
    }
    public void setYear(int year) {
        this.year = year;
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

}
