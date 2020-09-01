package com.statistical.time.tool;

/**
 * Created by pcbin on 2016/8/4.
 */
public class SystemSetUtil {
  private static class SingletonHolder {
    private static final SystemSetUtil INSTANCE = new SystemSetUtil();
  }

  public static SystemSetUtil getInstance() {
    return SingletonHolder.INSTANCE;
  }

  private SystemSetUtil() {
  }

  /**
   * 获取当前语言配置，如果用户在当前应用设置过语言，用当前应用设置的语言，没有设置过用系统设置的语言
   */
  public String getCurrentLanguageCode() {
    int languageMode = UserUtils.getUserSettingLanguage("language");
    String _str = "";
      if(languageMode == LanguageUtils.MODE_FOLLOW_SYSTEM){
        _str = "follow_system";
      } else if (languageMode == LanguageUtils.MODE_CHINES_LANGUAGE) {
        _str = "zh";
      } else if (languageMode == LanguageUtils.MODE_JAPANESE_LANGUAGE){
        _str = "jp";
      }else if (languageMode == LanguageUtils.MODE_KOREAN_LANGUAGE){
        _str = "ko";
      }else if (languageMode == LanguageUtils.MODE_HONGKONG_LANGUAGE){
        _str = "hk";
      }else if (languageMode == LanguageUtils.MODE_TAIWAN_LANGUAGE){
        _str = "tw";
      }else if (languageMode == LanguageUtils.MODE_ENGLISH_LANGUAGE){
        _str = "en";
      }else if (languageMode == LanguageUtils.MODE_FRANCE_LANGUAGE){
        _str = "fr";
      }else { //异常情况下，当前手机系统为非中文，加载英文
        _str = "en";
      }
    return _str;
  }

}
