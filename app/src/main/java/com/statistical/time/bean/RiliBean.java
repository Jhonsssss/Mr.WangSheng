package com.statistical.time.bean;


import org.greenrobot.greendao.annotation.Id;

public class RiliBean  {


    /**
     * reason : successed
     * result : {"id":"3805","yangli":"2020-09-03","yinli":"庚子(鼠)年七月十六","wuxing":"大驿土 除执位","chongsha":"冲兔(癸卯)煞东","baiji":"己不破券二比并亡 酉不宴客醉坐颠狂","jishen":"天德 时德 官日 吉期 除神 鸣犬","yi":"祭祀 解除 沐浴 理发 整手足甲 入殓 移柩 破土 安葬 扫舍","xiongshen":"大时 大败 咸池 九坎 九焦 往亡 五离 元武","ji":"嫁娶 会亲友 进人口 出行  入宅 移徙 赴任 作灶"}
     * error_code : 0
     */

    public String reason;
    public ResultBean result;
    public int error_code;

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public static class ResultBean {
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

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getYangli() {
            return yangli;
        }

        public void setYangli(String yangli) {
            this.yangli = yangli;
        }

        public String getYinli() {
            return yinli;
        }

        public void setYinli(String yinli) {
            this.yinli = yinli;
        }

        public String getWuxing() {
            return wuxing;
        }

        public void setWuxing(String wuxing) {
            this.wuxing = wuxing;
        }

        public String getChongsha() {
            return chongsha;
        }

        public void setChongsha(String chongsha) {
            this.chongsha = chongsha;
        }

        public String getBaiji() {
            return baiji;
        }

        public void setBaiji(String baiji) {
            this.baiji = baiji;
        }

        public String getJishen() {
            return jishen;
        }

        public void setJishen(String jishen) {
            this.jishen = jishen;
        }

        public String getYi() {
            return yi;
        }

        public void setYi(String yi) {
            this.yi = yi;
        }

        public String getXiongshen() {
            return xiongshen;
        }

        public void setXiongshen(String xiongshen) {
            this.xiongshen = xiongshen;
        }

        public String getJi() {
            return ji;
        }

        public void setJi(String ji) {
            this.ji = ji;
        }
    }
}
