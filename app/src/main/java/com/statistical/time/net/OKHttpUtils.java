package com.statistical.time.net;

import android.util.Log;

import com.facebook.common.file.FileUtils;
import com.statistical.time.tool.FileUtil;
import com.statistical.time.tool.LogUtil;
import com.statistical.time.tool.UiUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.builder.PostFormBuilder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by chengbin on 2016/4/21.
 * OKHttp抽取的工具类，方便调用
 * modify by dingchenghao 服务器升级，仅在注册、登录、找回密码时需要用到sessid，无需请求GET_HEADER，用到该类的地方需要用到HeaderCallback
 */
public class OKHttpUtils {

  public static OKHttpUtils myOKHttpUtils;

  public static OKHttpUtils getIntance() {
    if (myOKHttpUtils == null) {
      myOKHttpUtils = new OKHttpUtils();
    }
    return myOKHttpUtils;
  }

  static String  TAG =OKHttpUtils.class.getSimpleName();
  /**
   * 无参数的get请求
   */
  public void oKHttpGet(final String url, final Object tag, final MyHttpCallback myCallback) {
    excuteOKHttpGet(url, tag, myCallback);
  }

  private void excuteOKHttpGet(String url, Object tag, MyHttpCallback myCallback) {
    try {
      OkHttpUtils.get()
          .url(url)
          .headers(getHeader())
          .tag(tag)
          .build()
          .execute(myCallback);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  private HashMap<String,String> getHeader(){
    HashMap<String,String> map = new HashMap<>();
    Locale locale = UiUtil.getContext().getResources().getConfiguration().locale;
    String language = locale.getLanguage();
    LogUtil.e("language","language= "+ language);
    if(language.equals("zh")) {
      map.put("language", String.valueOf(1));
    }
    else {
      map.put("language", String.valueOf(2));
    }
    return map;
  }
  /**
   * 有参数的get请求，需签名
   */
  public void oKHttpGetParam(final String url, final Object tag, final Map<String, String> params,
                            final MyHttpCallback mCallback) {
    excuteOKHttpGetParam(url, tag, params, mCallback);
  }

  private void excuteOKHttpGetParam(String url, Object tag,
                                  Map<String, String> params, final MyHttpCallback mCallback) {

    try {
      String param = getMapParamStr(params);
      String urls=url+param;
      Log.e("url",urls);
      OkHttpUtils.get()
          .url(urls)
//          .headers(getHeader())
          .tag(tag)
          .build()
          .execute(mCallback);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * post请求
   */
  public void oKHttpPost(final String url, final Object tag, final Map<String, String> params,
                         final MyHttpCallback mCallback) {
    exOKHttpPost(url, tag, params, mCallback);
  }

  private  void exOKHttpPost(String url, Object tag, Map<String, String> params,
                           MyHttpCallback mMyCallback) {

    try {

      Log.e("POST","url=="+url);
      OkHttpUtils.post()
          .url(url)
        //  .headers(getHeader())
          .tag(tag)
          .params(params)
          .build()
          .execute(mMyCallback);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  public  void exOKHttpPostWithFile(String url, Object tag, Map<String, String> params,String  fileName,String suffixName,File file,
                           MyHttpCallback mMyCallback) {

    try {
      OkHttpUtils.post()
          .url(url)
          .headers(getHeader())
          .tag(tag)
          .params(params)
              .addFile(fileName,suffixName,file)
          .build()
          .execute(mMyCallback);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  public  void exOKHttpPostWithHeart(String url, Object tag, Map<String, String> params,File  pcmFile,File imageFile, File mp3Filepath,
                           MyHttpCallback mMyCallback) {

    try {
      OkHttpUtils.post()
          .url(url)
          .headers(getHeader())
          .tag(tag)
          .params(params)
              .addFile("data_file","pcm",pcmFile)
              .addFile("mp3_file","mp3",mp3Filepath)
              .addFile("image_path","png",imageFile)
          .build()
          .execute(mMyCallback);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }


  public  void exOKHttpPostWithData(String url, Object tag, Map<String, String> params,File  pcmFile,File imageFile, File mp3Filepath,
                                     MyHttpCallback mMyCallback) {

    try {
      OkHttpUtils.post()
              .url(url)
              .headers(getHeader())
              .tag(tag)
              .params(params)
              .addFile("data_file","json",pcmFile)
              .addFile("mp3_file","mp3",mp3Filepath)
              .addFile("image_path","png",imageFile)
              .build()
              .execute(mMyCallback);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void exPostWithImages(String url, Object tag, Map<String, String> params, ArrayList<String> list, String paramImg,String filepath,
                              MyHttpCallback mMyCallback){
 /*   MultipartBody.Builder multipartBodyBuilder = new MultipartBody.Builder();
    multipartBodyBuilder.setType(MultipartBody.FORM);
    if(params != null){

      for(String key : params.keySet()){
        multipartBodyBuilder.addFormDataPart(key,params.get(key));
      }
    }
    int i = 0;
    for(String path : list){
      File file = new File(path);
      if(file.exists()){
        multipartBodyBuilder.addFormDataPart("image_path"+i);
      }
    }*/


    Map<String, String> headers = new HashMap<>();
    headers.put("Content-Disposition", "form-data;filename=enctype");
    PostFormBuilder builder = OkHttpUtils.post();
    builder.url(url);
    int j = 1;
    for(int i = 0;i < list.size();i++){
      File f = new File(list.get(i));
      if(!f.exists()){
        UiUtil.showToast("文件不存在");
        return;
      }
      String filename = f.getAbsolutePath();
      builder.addFile(paramImg + j,filename,f);
      j++;
    }
      try {
          builder.params(params)
                  .headers(getHeader())
                  .build()
                  .execute(mMyCallback);
      } catch (Exception e) {
          e.printStackTrace();
      }
  }

  public static String getMapParamStr(Map<String, String> map) {
    StringBuilder builder = new StringBuilder();
    Iterator<Map.Entry<String, String>> iter = map.entrySet().iterator();
    Map.Entry<String, String> entry;
    while (iter.hasNext()) {
      entry = iter.next();
      try {
        builder.append('&');
        builder.append(entry.getKey());
        builder.append('=');
        builder.append(entry.getValue());

      } catch (Exception e) {
                e.printStackTrace();
      }
    }
    return builder.substring(1, builder.length());
  }
  public static void downloadFile(final String localurl, String downloadUrl, final DownloadListener listener) {

    FileUtil.createDirs(localurl);
    final long startTime = System.currentTimeMillis();
    LogUtil.i(TAG,"startTime="+startTime);
    OkHttpClient okHttpClient = new OkHttpClient();
    Request request = new Request.Builder()
            .url(downloadUrl)
            .addHeader("Connection", "close")
            .build();
    okHttpClient.newCall(request).enqueue(new Callback() {
      @Override
      public void onFailure(Call call, IOException e) {
        e.printStackTrace();
        LogUtil.i(TAG,"download failed");
        if (listener!=null){
          listener.fail(400,e.getMessage());
        }
      }
      @Override
      public void onResponse(Call call, Response response) throws IOException {
        InputStream is = null;
        if (listener!=null){
          listener.start(0);
        }
        byte[] buf = new byte[2048];
        int len = 0;
        FileOutputStream fos = null;
        // 储存下载文件的目录
        try {
          is = response.body().byteStream();
          long total = response.body().contentLength();
          File file = new File(localurl);
          fos = new FileOutputStream(file);
          long sum = 0;
          while ((len = is.read(buf)) != -1) {
            fos.write(buf, 0, len);
            sum += len;
            int progress = (int) (sum * 1.0f / total * 100);
            LogUtil.e(TAG,"download progress : " + progress);
            if (listener!=null){
              listener.loading(progress);
            }
          }
          fos.flush();
          LogUtil.e(TAG,"download success");
          LogUtil.e(TAG,"totalTime="+ (System.currentTimeMillis() - startTime));
        } catch (Exception e) {
          e.printStackTrace();
          LogUtil.e(TAG,"download failed : "+e.getMessage());
        } finally {
          try {
            if (is != null)
              is.close();
          } catch (IOException e) {
          }
          try {
            if (fos != null)
              fos.close();
          } catch (IOException e) {
          }

          if (listener!=null){
            listener.complete("");
          }
        }
      }
    });
  }


  public interface DownloadListener {

    /**
     *  开始下载
     */
    void start(long max);
    /**
     *  正在下载
     */
    void loading(int progress);
    /**
     *  下载完成
     */
    void complete(String path);
    /**
     *  请求失败
     */
    void fail(int code, String message);
    /**
     *  下载过程中失败
     */
    void loadfail(String message);
  }


  public void cancelTag(Object tag) {
    OkHttpUtils.getInstance().cancelTag(tag);
  }
}
