package com.statistical.time.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

import java.util.List;

public class Weather {


    /**
     * message : success感谢又拍云(upyun.com)提供CDN赞助
     * status : 200
     * date : 20200908
     * time : 2020-09-08 15:06:00
     * cityInfo : {"city":"北京市","citykey":"101010100","parent":"北京","updateTime":"12:01"}
     * data : {"shidu":"31%","pm25":11,"pm10":20,"quality":"优","wendu":"29","ganmao":"各类人群可自由活动","forecast":[{"date":"08","high":"高温 30℃","low":"低温 19℃","ymd":"2020-09-08","week":"星期二","sunrise":"05:49","sunset":"18:35","aqi":6,"fx":"西北风","fl":"2级","type":"多云","notice":"阴晴之间，谨防紫外线侵扰"},{"date":"09","high":"高温 30℃","low":"低温 19℃","ymd":"2020-09-09","week":"星期三","sunrise":"05:50","sunset":"18:33","aqi":7,"fx":"西北风","fl":"2级","type":"晴","notice":"愿你拥有比阳光明媚的心情"},{"date":"10","high":"高温 27℃","low":"低温 18℃","ymd":"2020-09-10","week":"星期四","sunrise":"05:51","sunset":"18:32","aqi":13,"fx":"北风","fl":"2级","type":"多云","notice":"阴晴之间，谨防紫外线侵扰"},{"date":"11","high":"高温 26℃","low":"低温 17℃","ymd":"2020-09-11","week":"星期五","sunrise":"05:52","sunset":"18:30","aqi":6,"fx":"东北风","fl":"2级","type":"晴","notice":"愿你拥有比阳光明媚的心情"},{"date":"12","high":"高温 27℃","low":"低温 18℃","ymd":"2020-09-12","week":"星期六","sunrise":"05:53","sunset":"18:28","aqi":13,"fx":"东风","fl":"2级","type":"多云","notice":"阴晴之间，谨防紫外线侵扰"},{"date":"13","high":"高温 27℃","low":"低温 18℃","ymd":"2020-09-13","week":"星期日","sunrise":"05:54","sunset":"18:27","aqi":43,"fx":"南风","fl":"2级","type":"多云","notice":"阴晴之间，谨防紫外线侵扰"},{"date":"14","high":"高温 27℃","low":"低温 18℃","ymd":"2020-09-14","week":"星期一","sunrise":"05:55","sunset":"18:25","aqi":67,"fx":"东南风","fl":"2级","type":"小雨","notice":"雨虽小，注意保暖别感冒"},{"date":"15","high":"高温 19℃","low":"低温 16℃","ymd":"2020-09-15","week":"星期二","sunrise":"05:56","sunset":"18:23","aqi":24,"fx":"东北风","fl":"3级","type":"大雨","notice":"出门最好穿雨衣，勿挡视线"},{"date":"16","high":"高温 24℃","low":"低温 15℃","ymd":"2020-09-16","week":"星期三","sunrise":"05:57","sunset":"18:22","aqi":8,"fx":"北风","fl":"2级","type":"多云","notice":"阴晴之间，谨防紫外线侵扰"},{"date":"17","high":"高温 24℃","low":"低温 17℃","ymd":"2020-09-17","week":"星期四","sunrise":"05:57","sunset":"18:20","aqi":12,"fx":"西北风","fl":"2级","type":"晴","notice":"愿你拥有比阳光明媚的心情"},{"date":"18","high":"高温 25℃","low":"低温 17℃","ymd":"2020-09-18","week":"星期五","sunrise":"05:58","sunset":"18:18","aqi":7,"fx":"西北风","fl":"3级","type":"晴","notice":"愿你拥有比阳光明媚的心情"},{"date":"19","high":"高温 27℃","low":"低温 17℃","ymd":"2020-09-19","week":"星期六","sunrise":"05:59","sunset":"18:17","aqi":8,"fx":"北风","fl":"2级","type":"晴","notice":"愿你拥有比阳光明媚的心情"},{"date":"20","high":"高温 27℃","low":"低温 18℃","ymd":"2020-09-20","week":"星期日","sunrise":"06:00","sunset":"18:15","aqi":21,"fx":"东北风","fl":"1级","type":"晴","notice":"愿你拥有比阳光明媚的心情"},{"date":"21","high":"高温 29℃","low":"低温 18℃","ymd":"2020-09-21","week":"星期一","sunrise":"06:01","sunset":"18:14","aqi":52,"fx":"东南风","fl":"1级","type":"多云","notice":"阴晴之间，谨防紫外线侵扰"},{"date":"22","high":"高温 27℃","low":"低温 16℃","ymd":"2020-09-22","week":"星期二","sunrise":"06:02","sunset":"18:12","aqi":38,"fx":"东南风","fl":"2级","type":"阴","notice":"不要被阴云遮挡住好心情"}],"yesterday":{"date":"07","high":"高温 30℃","low":"低温 20℃","ymd":"2020-09-07","week":"星期一","sunrise":"05:48","sunset":"18:37","aqi":22,"fx":"北风","fl":"1级","type":"晴","notice":"愿你拥有比阳光明媚的心情"}}
     */

    private String message;
    private int status;
    private String date;
    private String time;
    private CityInfoBean cityInfo;
    private DataBean data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public CityInfoBean getCityInfo() {
        return cityInfo;
    }

    public void setCityInfo(CityInfoBean cityInfo) {
        this.cityInfo = cityInfo;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class CityInfoBean {
        /**
         * city : 北京市
         * citykey : 101010100
         * parent : 北京
         * updateTime : 12:01
         */

        private String city;
        private String citykey;
        private String parent;
        private String updateTime;

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getCitykey() {
            return citykey;
        }

        public void setCitykey(String citykey) {
            this.citykey = citykey;
        }

        public String getParent() {
            return parent;
        }

        public void setParent(String parent) {
            this.parent = parent;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }
    }

    public static class DataBean {
        /**
         * shidu : 31%
         * pm25 : 11.0
         * pm10 : 20.0
         * quality : 优
         * wendu : 29
         * ganmao : 各类人群可自由活动
         * forecast : [{"date":"08","high":"高温 30℃","low":"低温 19℃","ymd":"2020-09-08","week":"星期二","sunrise":"05:49","sunset":"18:35","aqi":6,"fx":"西北风","fl":"2级","type":"多云","notice":"阴晴之间，谨防紫外线侵扰"},{"date":"09","high":"高温 30℃","low":"低温 19℃","ymd":"2020-09-09","week":"星期三","sunrise":"05:50","sunset":"18:33","aqi":7,"fx":"西北风","fl":"2级","type":"晴","notice":"愿你拥有比阳光明媚的心情"},{"date":"10","high":"高温 27℃","low":"低温 18℃","ymd":"2020-09-10","week":"星期四","sunrise":"05:51","sunset":"18:32","aqi":13,"fx":"北风","fl":"2级","type":"多云","notice":"阴晴之间，谨防紫外线侵扰"},{"date":"11","high":"高温 26℃","low":"低温 17℃","ymd":"2020-09-11","week":"星期五","sunrise":"05:52","sunset":"18:30","aqi":6,"fx":"东北风","fl":"2级","type":"晴","notice":"愿你拥有比阳光明媚的心情"},{"date":"12","high":"高温 27℃","low":"低温 18℃","ymd":"2020-09-12","week":"星期六","sunrise":"05:53","sunset":"18:28","aqi":13,"fx":"东风","fl":"2级","type":"多云","notice":"阴晴之间，谨防紫外线侵扰"},{"date":"13","high":"高温 27℃","low":"低温 18℃","ymd":"2020-09-13","week":"星期日","sunrise":"05:54","sunset":"18:27","aqi":43,"fx":"南风","fl":"2级","type":"多云","notice":"阴晴之间，谨防紫外线侵扰"},{"date":"14","high":"高温 27℃","low":"低温 18℃","ymd":"2020-09-14","week":"星期一","sunrise":"05:55","sunset":"18:25","aqi":67,"fx":"东南风","fl":"2级","type":"小雨","notice":"雨虽小，注意保暖别感冒"},{"date":"15","high":"高温 19℃","low":"低温 16℃","ymd":"2020-09-15","week":"星期二","sunrise":"05:56","sunset":"18:23","aqi":24,"fx":"东北风","fl":"3级","type":"大雨","notice":"出门最好穿雨衣，勿挡视线"},{"date":"16","high":"高温 24℃","low":"低温 15℃","ymd":"2020-09-16","week":"星期三","sunrise":"05:57","sunset":"18:22","aqi":8,"fx":"北风","fl":"2级","type":"多云","notice":"阴晴之间，谨防紫外线侵扰"},{"date":"17","high":"高温 24℃","low":"低温 17℃","ymd":"2020-09-17","week":"星期四","sunrise":"05:57","sunset":"18:20","aqi":12,"fx":"西北风","fl":"2级","type":"晴","notice":"愿你拥有比阳光明媚的心情"},{"date":"18","high":"高温 25℃","low":"低温 17℃","ymd":"2020-09-18","week":"星期五","sunrise":"05:58","sunset":"18:18","aqi":7,"fx":"西北风","fl":"3级","type":"晴","notice":"愿你拥有比阳光明媚的心情"},{"date":"19","high":"高温 27℃","low":"低温 17℃","ymd":"2020-09-19","week":"星期六","sunrise":"05:59","sunset":"18:17","aqi":8,"fx":"北风","fl":"2级","type":"晴","notice":"愿你拥有比阳光明媚的心情"},{"date":"20","high":"高温 27℃","low":"低温 18℃","ymd":"2020-09-20","week":"星期日","sunrise":"06:00","sunset":"18:15","aqi":21,"fx":"东北风","fl":"1级","type":"晴","notice":"愿你拥有比阳光明媚的心情"},{"date":"21","high":"高温 29℃","low":"低温 18℃","ymd":"2020-09-21","week":"星期一","sunrise":"06:01","sunset":"18:14","aqi":52,"fx":"东南风","fl":"1级","type":"多云","notice":"阴晴之间，谨防紫外线侵扰"},{"date":"22","high":"高温 27℃","low":"低温 16℃","ymd":"2020-09-22","week":"星期二","sunrise":"06:02","sunset":"18:12","aqi":38,"fx":"东南风","fl":"2级","type":"阴","notice":"不要被阴云遮挡住好心情"}]
         * yesterday : {"date":"07","high":"高温 30℃","low":"低温 20℃","ymd":"2020-09-07","week":"星期一","sunrise":"05:48","sunset":"18:37","aqi":22,"fx":"北风","fl":"1级","type":"晴","notice":"愿你拥有比阳光明媚的心情"}
         */

        private String shidu;
        private double pm25;
        private double pm10;
        private String quality;
        private String wendu;
        private String ganmao;
        private YesterdayBean yesterday;
        private List<ForecastBean> forecast;

        public String getShidu() {
            return shidu;
        }

        public void setShidu(String shidu) {
            this.shidu = shidu;
        }

        public double getPm25() {
            return pm25;
        }

        public void setPm25(double pm25) {
            this.pm25 = pm25;
        }

        public double getPm10() {
            return pm10;
        }

        public void setPm10(double pm10) {
            this.pm10 = pm10;
        }

        public String getQuality() {
            return quality;
        }

        public void setQuality(String quality) {
            this.quality = quality;
        }

        public String getWendu() {
            return wendu;
        }

        public void setWendu(String wendu) {
            this.wendu = wendu;
        }

        public String getGanmao() {
            return ganmao;
        }

        public void setGanmao(String ganmao) {
            this.ganmao = ganmao;
        }

        public YesterdayBean getYesterday() {
            return yesterday;
        }

        public void setYesterday(YesterdayBean yesterday) {
            this.yesterday = yesterday;
        }

        public List<ForecastBean> getForecast() {
            return forecast;
        }

        public void setForecast(List<ForecastBean> forecast) {
            this.forecast = forecast;
        }

        public static class YesterdayBean {
            /**
             * date : 07
             * high : 高温 30℃
             * low : 低温 20℃
             * ymd : 2020-09-07
             * week : 星期一
             * sunrise : 05:48
             * sunset : 18:37
             * aqi : 22
             * fx : 北风
             * fl : 1级
             * type : 晴
             * notice : 愿你拥有比阳光明媚的心情
             */

            private String date;
            private String high;
            private String low;
            private String ymd;
            private String week;
            private String sunrise;
            private String sunset;
            private int aqi;
            private String fx;
            private String fl;
            private String type;
            private String notice;

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
            }

            public String getHigh() {
                return high;
            }

            public void setHigh(String high) {
                this.high = high;
            }

            public String getLow() {
                return low;
            }

            public void setLow(String low) {
                this.low = low;
            }

            public String getYmd() {
                return ymd;
            }

            public void setYmd(String ymd) {
                this.ymd = ymd;
            }

            public String getWeek() {
                return week;
            }

            public void setWeek(String week) {
                this.week = week;
            }

            public String getSunrise() {
                return sunrise;
            }

            public void setSunrise(String sunrise) {
                this.sunrise = sunrise;
            }

            public String getSunset() {
                return sunset;
            }

            public void setSunset(String sunset) {
                this.sunset = sunset;
            }

            public int getAqi() {
                return aqi;
            }

            public void setAqi(int aqi) {
                this.aqi = aqi;
            }

            public String getFx() {
                return fx;
            }

            public void setFx(String fx) {
                this.fx = fx;
            }

            public String getFl() {
                return fl;
            }

            public void setFl(String fl) {
                this.fl = fl;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getNotice() {
                return notice;
            }

            public void setNotice(String notice) {
                this.notice = notice;
            }
        }

        public static class ForecastBean {
            /**
             * date : 08
             * high : 高温 30℃
             * low : 低温 19℃
             * ymd : 2020-09-08
             * week : 星期二
             * sunrise : 05:49
             * sunset : 18:35
             * aqi : 6
             * fx : 西北风
             * fl : 2级
             * type : 多云
             * notice : 阴晴之间，谨防紫外线侵扰
             */

            private String date;
            private String high;
            private String low;
            private String ymd;
            private String week;
            private String sunrise;
            private String sunset;
            private int aqi;
            private String fx;
            private String fl;
            private String type;
            private String notice;

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
            }

            public String getHigh() {
                return high;
            }

            public void setHigh(String high) {
                this.high = high;
            }

            public String getLow() {
                return low;
            }

            public void setLow(String low) {
                this.low = low;
            }

            public String getYmd() {
                return ymd;
            }

            public void setYmd(String ymd) {
                this.ymd = ymd;
            }

            public String getWeek() {
                return week;
            }

            public void setWeek(String week) {
                this.week = week;
            }

            public String getSunrise() {
                return sunrise;
            }

            public void setSunrise(String sunrise) {
                this.sunrise = sunrise;
            }

            public String getSunset() {
                return sunset;
            }

            public void setSunset(String sunset) {
                this.sunset = sunset;
            }

            public int getAqi() {
                return aqi;
            }

            public void setAqi(int aqi) {
                this.aqi = aqi;
            }

            public String getFx() {
                return fx;
            }

            public void setFx(String fx) {
                this.fx = fx;
            }

            public String getFl() {
                return fl;
            }

            public void setFl(String fl) {
                this.fl = fl;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getNotice() {
                return notice;
            }

            public void setNotice(String notice) {
                this.notice = notice;
            }
        }
    }


}
