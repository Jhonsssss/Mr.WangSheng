package com.statistical.time.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.statistical.time.R;
import com.statistical.time.tool.StringUtil;


/**
 * @author  ddnosh
 * @website http://blog.csdn.net/ddnosh
 */
public class LoadingDialog extends Dialog implements View.OnClickListener {

    private final static String TAG = "LoadingDialog";
    private LinearLayout mNoBgLinely;
    private TextView mTipTxt;
    private String mTip;
    private Context loadingDialogContext;

    public LoadingDialog(Context context) {
        super(context, R.style.dialog_loading_view);
        loadingDialogContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        LocaleLanguageUtil.getStance().changeLanguage(loadingDialogContext);
        setContentView(R.layout.dialog_loading);
        mNoBgLinely = (LinearLayout) findViewById(R.id.rl_loading);
        mNoBgLinely.setVisibility(View.VISIBLE);
        ImageView img_progress =findViewById(R.id.dialog_progress);
        AnimationDrawable animationDrawable2  = (AnimationDrawable) img_progress.getBackground();
        animationDrawable2.start();
        mTipTxt = (TextView)findViewById(R.id.tip);
        if(!StringUtil.isEmpty(mTip)){
            mTipTxt.setText(mTip);
        }
    }

    public void setTip(String tip){
        mTip = tip;
    }

    public void onDismiss() {
        if (this.isShowing()) {
            this.dismiss();
        }
    }
    public void setText(String str)
    {
        if(!TextUtils.isEmpty(str))
            mTipTxt.setText(str);
    }
    public Context getLoadingDialogContext() {
        return loadingDialogContext;
    }

    @Override
    public void onClick(View v) {
        onDismiss();
    }

    public interface LoadingDialogCallback {
        void onDialogCallback(int type, String id);
    }
}
