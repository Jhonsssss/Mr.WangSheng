package com.statistical.time.net;

import android.util.Log;

import com.statistical.time.R;
import com.statistical.time.tool.GsonUtil;
import com.statistical.time.tool.LogUtil;
import com.statistical.time.tool.UiUtil;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import okhttp3.Call;

/**
 */
public abstract class MyHttpCallback<T> extends StringCallback {
    private Class<T> entityClass;

    public MyHttpCallback() {
        Type genType = this.getClass().getGenericSuperclass();
       entityClass = (Class<T>) ((ParameterizedType) genType).getActualTypeArguments()[0];
    }

    @Override
    public void onResponse(String response, int id) {
        if(response != null){
            LogUtil.e("MyHttpCallback", "*返*回*参*数*：response = " + response);
        }
        T bean = null;
        String tips = "";
        int status = -1;
        //解析数据
        try {
            LogUtil.e("MyHttpCallback","response = " + response);
            bean = GsonUtil.JsonToEntity(response, entityClass);
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.e("MyHttpCallback","exx=="+e.toString());
            bean = null;
            try {
                JSONObject jsonObject = new JSONObject(response == null ? "" : response); //fixed by yangchao 2016/10/8 :防止空指针崩溃问题
                tips = jsonObject.getString("result_message");
            } catch (JSONException e1) {
                e.printStackTrace();
                bean = null;
            }
        }
        //回调
        if (bean != null) {
                onSuccess(bean); //成功回调
        } else {
                onFailure(tips); //失败回调
        }
    }

    /**
     * 特殊code码处理
     */
    private void handleRequestStatus() {
    }

    @Override
    public void onError(Call call, Exception e, int id) { //无网络、网络超时或者主动call掉网络请求情况下回调
//        if (!call.isCanceled()) { //注意：主动调用call.cancel仍然会执行onError回调
//            onFailure(UiUtil.getString(R.string.str_no_internet));
//        }

        Log.e("MyHttpCallback","e=="+e.getMessage());
    }

    public abstract void onSuccess(T response);

    public abstract void onFailure(String errorMsg);

}
