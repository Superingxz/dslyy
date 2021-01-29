package com.dsl.util;

import android.content.Context;
import android.os.Looper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.StringRes;

import com.dsl.base.R;


public class ToastKit {
    private static View toastView;
    private static TextView textView;
    private static Toast toast;

    public static void show(Context context, @StringRes int resId) {
        show(context, context.getString(resId));
    }

    public static void show(Context context, String message) {
        if (context == null) {
            return;
        }
        if (Looper.myLooper() != Looper.getMainLooper()) {
            return;
        }
        if (message.contains("No address associated with hostname")) {
            message = "请检查网络状态";
        }
        if (message.contains("HttpException")) {
            message = "[网络异常]";
        }
        if (toastView == null) {
            toastView = LayoutInflater.from(context.getApplicationContext()).inflate(R.layout.toast_text_layout, null);
            textView = toastView.findViewById(R.id.text);
            toast = new Toast(context.getApplicationContext());
            toast = new Toast(context);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.setDuration(Toast.LENGTH_SHORT);
            toast.setView(toastView);
        }
        textView.setText(message);
        toast.show();
    }

}
