package com.statistical.time.dialog;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.ViewTreeObserver;

import com.afollestad.materialdialogs.MaterialDialog;
import com.statistical.time.tool.LanguageUtils;



/**
 * 多语言弹出框
 * Created by Din on 2016/10/24.
 */

public class MaterialDialogBuilderL extends MaterialDialog.Builder {


    public MaterialDialogBuilderL(Context context) {
        super(context);
        LanguageUtils.getCurrentLanguage(context);
    }


    @Override
    public MaterialDialog build() {
        return new MaterialDialogL(this);
    }

    public class MaterialDialogL extends MaterialDialog {
        private ViewTreeObserver.OnWindowFocusChangeListener listener;

        protected MaterialDialogL(Builder builder) {
            super(builder);
            if (positiveButton != null) {
                positiveButton.setAllCapsCompat(false);
            }
            if (neutralButton != null) {
                neutralButton.setAllCapsCompat(false);
            }
            if (negativeButton != null) {
                negativeButton.setAllCapsCompat(false);
            }
        }

        public void setOnWindowFocusChangeListener(ViewTreeObserver.OnWindowFocusChangeListener l) {
            this.listener = l;
        }

        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
        @Override
        public void onWindowFocusChanged(boolean hasFocus) {
            super.onWindowFocusChanged(hasFocus);
            if (listener != null) {
                listener.onWindowFocusChanged(hasFocus);
            }
        }
    }

}
