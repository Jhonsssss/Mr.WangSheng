package com.statistical.time.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.google.gson.Gson;
import com.statistical.greendao.CityInfoDao;
import com.statistical.greendao.RiLiEntityDao;
import com.statistical.time.R;
import com.statistical.time.activity.WebViewActivity;
import com.statistical.time.application.MyApplication;
import com.statistical.time.base.BaseFragment;
import com.statistical.time.base.BasePresenter;
import com.statistical.time.bean.CityInfo;
import com.statistical.time.bean.EventCenter;
import com.statistical.time.bean.RiLiEntity;
import com.statistical.time.bean.RiliBean;
import com.statistical.time.bean.Weather;
import com.statistical.time.calendarview.library.Calendar;
import com.statistical.time.calendarview.library.CalendarLayout;
import com.statistical.time.calendarview.library.CalendarView;
import com.statistical.time.calendarview.library.TrunkBranchAnnals;
import com.statistical.time.net.MyHttpCallback;
import com.statistical.time.net.OKHttpUtils;
import com.statistical.time.tool.SharedPreferencesUtils;
import com.statistical.time.tool.StringUtil;
import com.statistical.time.tool.SystemUtil;
import com.statistical.time.tool.UserUtils;

import org.greenrobot.greendao.query.QueryBuilder;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


public class CalendarFragment extends BaseFragment

        implements CalendarView.OnCalendarSelectListener, CalendarView.OnYearChangeListener

{
    private AMapLocationClient mlocationClient;
    private AMapLocationClientOption mLocationOption;

    @Override
    protected boolean isBindEventBusHere() {
        return false;
    }

    @Override
    protected void onEventComming(EventCenter eventCenter) {

    }
    TextView mTextMonthDay;

    TextView mTextYear;

    TextView mTextLunar;

    TextView mTextCurrentDay;

    @BindView(R.id.ll_wannian_riqi2)
    TextView tv_today_star;
    @BindView(R.id.tv_today_star_date)
    TextView tv_today_star_date;
    @BindView(R.id.tv_start_range)
    TextView tv_start_range;
    @BindView(R.id.tv_star_desc)
    TextView tv_star_desc;
    @BindView(R.id.tv_wannian_day)
    TextView tv_wannian_day;
    @BindView(R.id.tv_wannian_week)
    TextView tv_wannian_week;
    @BindView(R.id.tv_wannian_year_month)
    TextView tv_wannian_year_month;
    @BindView(R.id.tv_wannian_nongli)
    TextView tv_wannian_nongli;
    @BindView(R.id.tv_wannian_jiazi)
    TextView tv_wannian_jiazi;
    @BindView(R.id.tv_wannian_good)
    TextView tv_wannian_good;
    @BindView(R.id.tv_wannian_bad)
    TextView tv_wannian_bad;
    @BindView(R.id.tv_tianqi_riqi)
    TextView tv_tianqi_riqi;


    @BindView(R.id.tv_today_weather)
    TextView tv_today_weather;
    @BindView(R.id.tv_wannian_temp)
    TextView tv_wannian_temp;
    @BindView(R.id.tv_today_tip)
    TextView tv_today_tip;
    @BindView(R.id.tv_today_windy)
    TextView tv_today_windy;
    @BindView(R.id.tv_today_weep)
    TextView tv_today_weep;
    @BindView(R.id.tv_today_address)
    TextView tv_today_address;

    CalendarView mCalendarView;

    RelativeLayout mRelativeTool;
    private int mYear;
    CalendarLayout mCalendarLayout;
//    GroupRecyclerView mRecyclerView;
    @Override
    public BasePresenter getPresenter() {
        return null;
    }
    Unbinder unbinder;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootview  = inflater.inflate(R.layout.fragment_calendar,null,false);
        unbinder  = ButterKnife.bind(this,rootview);
        mDao  = MyApplication.getInstance().getmDaoSession().getRiLiEntityDao();
        initView(rootview);
        initLocation();
        initData();
        initStatusHeight(rootview);

        return rootview;
    }

    private void initLocation() {

        mlocationClient = new AMapLocationClient(getActivity());
        //初始化定位参数
        mLocationOption = new AMapLocationClientOption();
     //设置返回地址信息，默认为true
        mLocationOption.setNeedAddress(true);
        mLocationOption.setSensorEnable(true);
        mlocationClient.setLocationListener(new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation aMapLocation) {

                mlocationClient.stopLocation();
                Log.e("onLocationChanged",aMapLocation.toStr());
//                LatLng mCurLocation = null;
//                mCurLocation = new LatLng(aMapLocation.getLatitude(), aMapLocation.getLongitude());
                //String address = aMapLocation.getAddress();
                // tv_location.setText(address);
                //aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mCurLocation,13));

                String  city =aMapLocation.getCity().replaceAll("市","");
          List<CityInfo>  citys =         MyApplication.getInstance().getmDaoSession().getCityInfoDao().queryBuilder().where(CityInfoDao.Properties.City_name.like(city)).list();
              //  getWeather();

                 tv_today_address.setText(aMapLocation.getProvince()+aMapLocation.getCity()+aMapLocation.getDistrict());

                 UserUtils.setCurrentCity(getActivity(),tv_today_address.getText().toString());

                if (citys.size()>0){
                    city_code =citys.get(0).getCity_code();
                    getWeather();
                }

            }
        });
//        设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
//设置定位间隔,单位毫秒,默认为2000ms
        mLocationOption.setInterval(20000);
//设置定位参数
        mlocationClient.setLocationOption(mLocationOption);



    }



    private void initStatusHeight(View rootView) {
        View status_bar_height =rootView.findViewById(R.id.status_bar_height);
        ViewGroup.LayoutParams layoutParams =  status_bar_height.getLayoutParams();
        layoutParams.height  = SystemUtil.getStatusBarHeight(getActivity());
        status_bar_height.setLayoutParams(layoutParams);

    }
    protected void initView(View rootView) {
        mTextMonthDay = rootView.findViewById(R.id.tv_month_day);
        mTextYear = rootView.findViewById(R.id.tv_year);
        mTextLunar = rootView.findViewById(R.id.tv_lunar);
        mRelativeTool =rootView. findViewById(R.id.rl_tool);
        mCalendarView =  rootView.findViewById(R.id.calendarView);
        mTextCurrentDay = rootView. findViewById(R.id.tv_current_day);
        mTextMonthDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (!mCalendarLayout.isExpand()) {
//                    mCalendarLayout.expand();
//                    return;
//                }
//                mCalendarView.showYearSelectLayout(mYear);
//                mTextLunar.setVisibility(View.GONE);
//                mTextYear.setVisibility(View.GONE);
//                mTextMonthDay.setText(String.valueOf(mYear));
            }
        });
        rootView.findViewById(R.id.fl_current).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCalendarView.scrollToCurrent();
                //mCalendarView.addSchemeDate(getSchemeCalendar(2019, 6, 1, 0xFF40db25, "假"));
//                int year = 2019;
//                int month = 6;
//                Map<String, Calendar> map = new HashMap<>();
//                map.put(getSchemeCalendar(year, month, 3, 0xFF40db25, "假").toString(),
//                        getSchemeCalendar(year, month, 3, 0xFF40db25, "假"));
//                map.put(getSchemeCalendar(year, month, 6, 0xFFe69138, "事").toString(),
//                        getSchemeCalendar(year, month, 6, 0xFFe69138, "事"));
//                map.put(getSchemeCalendar(year, month, 9, 0xFFdf1356, "议").toString(),
//                        getSchemeCalendar(year, month, 9, 0xFFdf1356, "议"));
//                map.put(getSchemeCalendar(year, month, 13, 0xFFedc56d, "记").toString(),
//                        getSchemeCalendar(year, month, 13, 0xFFedc56d, "记"));
//                mCalendarView.addSchemeDate(map);
            }
        });
        mCalendarLayout = rootView.findViewById(R.id.calendarLayout);
        mCalendarView.setOnCalendarSelectListener(this);
        mCalendarView.setOnYearChangeListener(this);
        mTextYear.setText(String.valueOf(mCalendarView.getCurYear()));
        mYear = mCalendarView.getCurYear();
        mTextMonthDay.setText(mCalendarView.getCurMonth() + "月" + mCalendarView.getCurDay() + "日");
        mTextLunar.setText("今日");
        mTextCurrentDay.setText(String.valueOf(mCalendarView.getCurDay()));

//        mRecyclerView = rootView.findViewById(R.id.recyclerView);
//        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
//        mRecyclerView.addItemDecoration(new GroupItemDecoration<String, Article>());
//        mRecyclerView.setAdapter(new ArticleAdapter(getActivity()));
//        mRecyclerView.notifyDataSetChanged();
    }
    String[]  star_names;
    String[]  star_durtions;
    String[]  star_desc;
    String[]  lunar_str;
    int mIndex;
    protected void initData() {
        star_names=getResources().getStringArray(R.array.Start_names);
        star_durtions=getResources().getStringArray(R.array.Start_drutions);
        lunar_str=getResources().getStringArray(R.array.lunar_str);
        star_desc=getResources().getStringArray(R.array.star_desc);
        Calendar calendar = mCalendarView.getCurCalendar();
        TrunkBranchAnnals.init(getActivity());

        tv_tianqi_riqi.setText(String.format(getString(R.string.date_format), calendar.getYear(), calendar.getMonth(), calendar.getDay()));
        tv_today_address.setText( UserUtils.getCurrentCity(getActivity()));

        Weather weather =UserUtils.getWeather(getActivity());
        setWeatherData(weather);


        setStarDate(calendar);
//        Map<String, Calendar> map = new HashMap<>();
//        map.put(getSchemeCalendar(year, month, 3, 0xFF40db25, "假").toString(),
//                getSchemeCalendar(year, month, 3, 0xFF40db25, "假"));
//        map.put(getSchemeCalendar(year, month, 6, 0xFFe69138, "事").toString(),
//                getSchemeCalendar(year, month, 6, 0xFFe69138, "事"));
//        map.put(getSchemeCalendar(year, month, 9, 0xFFdf1356, "议").toString(),
//                getSchemeCalendar(year, month, 9, 0xFFdf1356, "议"));
//        map.put(getSchemeCalendar(year, month, 13, 0xFFedc56d, "记").toString(),
//                getSchemeCalendar(year, month, 13, 0xFFedc56d, "记"));
//        map.put(getSchemeCalendar(year, month, 14, 0xFFedc56d, "记").toString(),
//                getSchemeCalendar(year, month, 14, 0xFFedc56d, "记"));
//        map.put(getSchemeCalendar(year, month, 15, 0xFFaacc44, "假").toString(),
//                getSchemeCalendar(year, month, 15, 0xFFaacc44, "假"));
//        map.put(getSchemeCalendar(year, month, 18, 0xFFbc13f0, "记").toString(),
//                getSchemeCalendar(year, month, 18, 0xFFbc13f0, "记"));
//        map.put(getSchemeCalendar(year, month, 25, 0xFF13acf0, "假").toString(),
//                getSchemeCalendar(year, month, 25, 0xFF13acf0, "假"));
//        map.put(getSchemeCalendar(year, month, 27, 0xFF13acf0, "多").toString(),
//                getSchemeCalendar(year, month, 27, 0xFF13acf0, "多"));
//        //此方法在巨大的数据量上不影响遍历性能，推荐使用
//        mCalendarView.setSchemeDate(map);


        getCityInfo();

    }


    Gson gson =  new Gson();
    private void getCityInfo() {



        if ( MyApplication.getInstance().getmDaoSession().getCityInfoDao().loadAll().size()==0) {

            new Thread(new Runnable() {
                @Override
                public void run() {


                    try {
                        InputStream is = getActivity().getAssets().open("json/citycode-2019-08-23.json");
                        String s = null;
                        //格式转换
                        Scanner scanner = new Scanner(is, "UTF-8").useDelimiter("\\A");
                        if (scanner.hasNext()) {
                            s = scanner.next();
                        }
                        is.close();


                        JSONArray jsonArray  =new JSONArray(s);

             for (int i =  0 ;i<jsonArray.length();i++){
                     String ss  =jsonArray.get(i).toString();

                 CityInfo cityInfo =     gson.fromJson(ss, CityInfo.class);
                 Log.e("cityInfo","cityInfo==="+cityInfo.toString());
                 MyApplication.getInstance().getmDaoSession().getCityInfoDao().insert(cityInfo);


             }

                        mlocationClient.startLocation();
//                        Log.e("cityInfo", "onLocationChanged==" + s);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();

        }else{
            mlocationClient.startLocation();
        }

    }

    private void setStarDate(Calendar calendar) {


        int year = calendar.getYear();
        int month = calendar.getMonth();
        int day = calendar.getDay();
        int  week =  calendar.getWeek();
        Calendar  lunarCalendar=  calendar.getLunarCalendar() ;

        StringBuilder  lunarMonthDay = new StringBuilder("");
        if (lunarCalendar.getLeapMonth()>0){
            lunarMonthDay.append("闰");
            lunarMonthDay.append(StringUtil.getMonthyStr(lunarCalendar.getMonth()));

        }else{
            lunarMonthDay.append(StringUtil.getMonthyStr(lunarCalendar.getMonth()));
        }
        lunarMonthDay.append(lunar_str[lunarCalendar.getDay()-1]);
        mIndex = star(month,day);

        tv_today_star.setText(star_names[mIndex]);

        tv_today_star_date.setText(String.format(getString(R.string.date_format), year, month, day));
        tv_start_range.setText("时间:"+star_durtions[mIndex]);
        tv_star_desc.setText("星座性格:"+star_desc[mIndex]);
        tv_wannian_day.setText(String.valueOf(day));
        tv_wannian_week.setText(StringUtil.getweekStr(week));
        tv_wannian_year_month.setText(year+"年"+month+"月");
        tv_wannian_nongli.setText(lunarMonthDay);
        tv_wannian_jiazi.setText(getGanZhi(calendar.getYear(),calendar.getMonth(),calendar.getDay()));
        getLaohuangLi(calendar.getYear(),calendar.getMonth(),calendar.getDay());

    }

    public  int star(int month, int day) {
        int  index =0;
        if (month == 1 && day >= 20 || month == 2 && day <= 18) {
            index =0;
        }
        if (month == 2 && day >= 19 || month == 3 && day <= 20) {
            index =1;
        }
        if (month == 3 && day >= 21 || month == 4 && day <= 19) {
            index =2;
        }
        if (month == 4 && day >= 20 || month == 5 && day <= 20) {
            index =3;
        }
        if (month == 5 && day >= 21 || month == 6 && day <= 21) {
            index =4;
        }
        if (month == 6 && day >= 22 || month == 7 && day <= 22) {
            index =5;
        }
        if (month == 7 && day >= 23 || month == 8 && day <= 22) {
            index =6;
        }
        if (month == 8 && day >= 23 || month == 9 && day <= 22) {
            index =7;
        }
        if (month == 9 && day >= 23 || month == 10 && day <= 23) {
            index =8;
        }
        if (month == 10 && day >= 24 || month == 11 && day <= 22) {
            index =9;
        }
        if (month == 11 && day >= 23 || month == 12 && day <= 21) {
            index =10;
        }
        if (month == 12 && day >= 22 || month == 1 && day <= 19) {
            index =11;
        }
        return index;
    }

    private Calendar getSchemeCalendar(int year, int month, int day, int color, String text) {
        Calendar calendar = new Calendar();
        calendar.setYear(year);
        calendar.setMonth(month);
        calendar.setDay(day);
        calendar.setSchemeColor(color);//如果单独标记颜色、则会使用这个颜色
        calendar.setScheme(text);
        calendar.addScheme(new Calendar.Scheme());
        calendar.addScheme(0xFF008800, "假");
        calendar.addScheme(0xFF008800, "节");
        return calendar;
    }

    @Override
    public void onCalendarOutOfRange(Calendar calendar) {

    }
    RiLiEntityDao mDao;
    @Override
    public void onCalendarSelect(Calendar calendar, boolean isClick) {
        mTextLunar.setVisibility(View.VISIBLE);
        mTextYear.setVisibility(View.VISIBLE);
        mTextMonthDay.setText(calendar.getMonth() + "月" + calendar.getDay() + "日");
        mTextYear.setText(String.valueOf(calendar.getYear()));
        mTextLunar.setText(calendar.getLunar());
        mYear = calendar.getYear();


        setStarDate(calendar);
    }

    @Override
    public void onYearChange(int year) {
        mTextMonthDay.setText(String.valueOf(year));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }


    /**
     * 对于年月日的天干地支
     */
    private int year_ganZhi;
    private int month_ganZhi;
    private int day_ganZhi;
    /**
     * 关于阴历的相关信息
     */
    private static int[] lunar_info = {0x04bd8, 0x04ae0, 0x0a570, 0x054d5,
            0x0d260, 0x0d950, 0x16554, 0x056a0, 0x09ad0, 0x055d2, 0x04ae0,
            0x0a5b6, 0x0a4d0, 0x0d250, 0x1d255, 0x0b540, 0x0d6a0, 0x0ada2,
            0x095b0, 0x14977, 0x04970, 0x0a4b0, 0x0b4b5, 0x06a50, 0x06d40,
            0x1ab54, 0x02b60, 0x09570, 0x052f2, 0x04970, 0x06566, 0x0d4a0,
            0x0ea50, 0x06e95, 0x05ad0, 0x02b60, 0x186e3, 0x092e0, 0x1c8d7,
            0x0c950, 0x0d4a0, 0x1d8a6, 0x0b550, 0x056a0, 0x1a5b4, 0x025d0,
            0x092d0, 0x0d2b2, 0x0a950, 0x0b557, 0x06ca0, 0x0b550, 0x15355,
            0x04da0, 0x0a5d0, 0x14573, 0x052d0, 0x0a9a8, 0x0e950, 0x06aa0,
            0x0aea6, 0x0ab50, 0x04b60, 0x0aae4, 0x0a570, 0x05260, 0x0f263,
            0x0d950, 0x05b57, 0x056a0, 0x096d0, 0x04dd5, 0x04ad0, 0x0a4d0,
            0x0d4d4, 0x0d250, 0x0d558, 0x0b540, 0x0b5a0, 0x195a6, 0x095b0,
            0x049b0, 0x0a974, 0x0a4b0, 0x0b27a, 0x06a50, 0x06d40, 0x0af46,
            0x0ab60, 0x09570, 0x04af5, 0x04970, 0x064b0, 0x074a3, 0x0ea50,
            0x06b58, 0x055c0, 0x0ab60, 0x096d5, 0x092e0, 0x0c960, 0x0d954,
            0x0d4a0, 0x0da50, 0x07552, 0x056a0, 0x0abb7, 0x025d0, 0x092d0,
            0x0cab5, 0x0a950, 0x0b4a0, 0x0baa4, 0x0ad50, 0x055d9, 0x04ba0,
            0x0a5b0, 0x15176, 0x052b0, 0x0a930, 0x07954, 0x06aa0, 0x0ad50,
            0x05b52, 0x04b60, 0x0a6e6, 0x0a4e0, 0x0d260, 0x0ea65, 0x0d530,
            0x05aa0, 0x076a3, 0x096d0, 0x04bd7, 0x04ad0, 0x0a4d0, 0x1d0b6,
            0x0d250, 0x0d520, 0x0dd45, 0x0b5a0, 0x056d0, 0x055b2, 0x049b0,
            0x0a577, 0x0a4b0, 0x0aa50, 0x1b255, 0x06d20, 0x0ada0};
    /**
     * 记录天干的信息
     */
    private String[] gan_info = {"甲", "乙", "丙", "丁", "戊", "己", "庚", "辛",
            "壬", "癸"};
    private String[] zhi_info = {"子", "丑", "寅", "卯", "辰", "巳", "午", "未",
            "申", "酉", "戌", "亥"};

    /**
     * 获取农历某年的总天数
     *
     * @param year
     * @return
     */
    private int daysOfYear(int year) {
        int sum = 348;
        for (int i = 0x8000; i > 0x8; i >>= 1) {
            sum += (lunar_info[year - 1900] & i) == 0 ? 0 : 1;
        }
        //获取闰月的天数
        int daysOfLeapMonth;
        if ((lunar_info[year - 1900] & 0xf) != 0) {
            daysOfLeapMonth = (lunar_info[year - 1900] & 0x10000) == 0 ? 29 : 30;
        } else {
            daysOfLeapMonth = 0;
        }
        return sum + daysOfLeapMonth;
    }

    /**
     * 初始化年月日对应的天干地支
     * @param year
     * @param month
     * @param day
     */
    public String getGanZhi(int year, int month, int day) {
        //获取现在的时间
        java.util.Calendar calendar_now = java.util.Calendar.getInstance();
        calendar_now.set(year, month - 1, day);
        long date_now = calendar_now.getTime().getTime();
        //获取1900-01-31的时间
        java.util.Calendar calendar_ago = java.util.Calendar.getInstance();
        calendar_ago.set(1900, 0 ,31);
        long date_ago = calendar_ago.getTime().getTime();
        //86400000 = 24 * 60 * 60 * 1000
        long days_distance = (date_now - date_ago) / 86400000L;
        float remainder = (date_now - date_ago) % 86400000L;
        //余数大于0算一天
        if (remainder > 0) {
            days_distance += 1;
        }
        //都是从甲子开始算起以1900-01-31为起点
        //1899-12-21是农历1899年腊月甲子日  40：相差1900-01-31有40天
        day_ganZhi = (int)days_distance + 40;
        //1898-10-01是农历甲子月  14：相差1900-01-31有14个月
        month_ganZhi = 14;
        int daysOfYear = 0;
        int i;
        for (i = 1900; i < 2050 && days_distance > 0; i++) {
            daysOfYear = daysOfYear(i);
            days_distance -= daysOfYear;
            month_ganZhi += 12;
        }
        if (days_distance < 0) {
            days_distance += daysOfYear;
            i--;
            month_ganZhi -= 12;
        }
        //农历年份
        int myYear = i;
        //1864年是甲子年
        year_ganZhi = myYear - 1864;
        //哪个月是闰月
        int leap = lunar_info[myYear - 1900] & 0xf;
        boolean isLeap = false;
        int daysOfLeapMonth = 0;
        for (i = 1; i < 13 && days_distance > 0; i++) {
            //闰月
            if (leap > 0 && i == (leap + 1) && !isLeap) {
                isLeap = true;
                if ((lunar_info[myYear - 1900] & 0xf) != 0) {
                    daysOfLeapMonth = (lunar_info[myYear - 1900] & 0x10000) == 0 ? 29 : 30;
                } else {
                    daysOfLeapMonth = 0;
                }
                --i;
            } else {
                daysOfLeapMonth = (lunar_info[myYear - 1900] & (0x10000 >> i)) == 0 ? 29 : 30;
            }
            //设置非闰月
            if (isLeap && i == (leap + 1)) {
                isLeap = false;
            }
            days_distance -= daysOfLeapMonth;
            if (!isLeap) {
                month_ganZhi++;
            }
        }
        if (days_distance == 0 && leap > 0 && i == leap + 1 && !isLeap) {
            --month_ganZhi;
        }
        if (days_distance < 0) {
            --month_ganZhi;
        }

       return ganZhi(month_ganZhi) + "月" + ganZhi(day_ganZhi) + "日";
    }

    /**
     * 将年月日转化为天干地支的显示方法
     * @param index
     * @return
     */
    private String ganZhi(int index) {
        return gan_info[index % 10] + zhi_info[index % 12];
    }


    String  city_code =  "101010100";
    boolean  isNeedGetWeather  ;
    private void getWeather() {
        String  url =  "http://t.weather.itboy.net/api/weather/city/"+city_code;

        OKHttpUtils.getIntance().oKHttpGet(url, this,new MyHttpCallback<Weather>() {
            @Override
            public void onSuccess(Weather response) {


                UserUtils.setWeather(getActivity(),gson.toJson(response));

                setWeatherData(response);

            }

            @Override
            public void onFailure(String errorMsg) {

            }
        });
    }

    private void setWeatherData(Weather response) {
        if (response==null) return;

        String  wendu  =     response.getData().getWendu();
        String  shidu  =     response.getData().getShidu();
        String  ganmao  =     response.getData().getGanmao();
        tv_wannian_temp.setText(wendu+"°");
        tv_today_weep.setText("湿度:"+shidu);

        List<Weather.DataBean.ForecastBean>  forecastBeans =    response.getData().getForecast();
        if (forecastBeans.size()>0){

            Weather.DataBean.ForecastBean today  = forecastBeans.get(0);

            tv_today_tip.setText(today.getNotice());
            tv_today_weather.setText(today.getType());
            tv_today_windy.setText(today.getFx()+today.getFl());
        }

    }

    public void  getLaohuangLi(final int  year , final int  month , final int day){


     List<RiLiEntity> mlist = mDao.queryBuilder().where(RiLiEntityDao.Properties.Day.eq(day),RiLiEntityDao.Properties.Year.eq(year),RiLiEntityDao.Properties.Month.eq(month)).list();
      if (mlist!=null&&mlist.size()>0){
          setRiliText(mlist.get(0));
      }else{
          Map<String,String> params  = new HashMap<>();
          params.put("date",year+"-"+month+"-"+day);
          params.put("key","01374531eee62bbcac71d725094b3457");
          OKHttpUtils.getIntance().oKHttpGetParam("http://v.juhe.cn/laohuangli/d?", this, params, new MyHttpCallback<RiliBean>() {
              @Override
              public void onSuccess(RiliBean response) {

                  Log.e("getLaohuangLi","   "+response.getResult().getYi()+ "   "+response.getResult().getJi());
                  
                

                  RiliBean.ResultBean resultBean  = response.getResult();

                  if (resultBean!=null){
                      RiLiEntity riLiEntity  = new RiLiEntity();
                      riLiEntity.id =resultBean.id ;
                      riLiEntity.yangli=resultBean.yangli ;
                      riLiEntity.yinli=resultBean.yinli ;
                      riLiEntity.wuxing=resultBean.wuxing ;
                      riLiEntity.chongsha=resultBean.chongsha ;
                      riLiEntity.baiji=resultBean.baiji ;
                      riLiEntity.jishen=resultBean.jishen ;
                      riLiEntity.yi=resultBean.yi ;
                      riLiEntity.xiongshen=resultBean.xiongshen ;
                      riLiEntity.ji=resultBean.ji ;
                      riLiEntity.year =year;
                      riLiEntity. month =month;
                      riLiEntity. day=day;
                      mDao.insert(riLiEntity);

                      setRiliText(riLiEntity);
                  }
                

              }

              @Override
              public void onFailure(String errorMsg) {

              }
          });
          
      }
     
      

    }

    private void setRiliText(RiLiEntity riLiEntity) {
        if (riLiEntity.yi==null||TextUtils.isEmpty(riLiEntity.yi.trim())){
            tv_wannian_good.setText("宜：无");
        }else{
            tv_wannian_good.setText("宜："+riLiEntity.yi.trim());
        }
        if (riLiEntity.ji==null||TextUtils.isEmpty(riLiEntity.ji.trim())){
            tv_wannian_bad.setText("忌：无");

        }else{

            tv_wannian_bad.setText("忌："+riLiEntity.ji.trim());
        }



    }


    @OnClick(R.id.tv_click_detail)
    public  void  clickDetailStar(){
      String  url  = "file:///android_asset/star_detail/start_detail_"+(mIndex+1)+".html";
        WebViewActivity.gotoWebViewActivity(getActivity(),url , true);
    }
}
