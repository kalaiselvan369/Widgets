package com.example.developer.apppermissions;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ProgressBar;

/**
 * Created by developer on 8/5/17.
 */

public class CustomAutoCompleteTextView extends android.support.v7.widget.AppCompatMultiAutoCompleteTextView {

    private static final int MESSAGE_TEXT_CHANGED = 100;
    private static final int DEFAULT_AUTOCOMPLETE_DELAY = 750;
    private int mAutoCompleteDelay = DEFAULT_AUTOCOMPLETE_DELAY;

    public CustomAutoCompleteTextView(Context context) {
        super(context);
    }

    public CustomAutoCompleteTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            //performFiltering((CharSequence) msg.obj, msg.arg1);
            CustomAutoCompleteTextView.super.performFiltering((CharSequence) msg.obj, msg.arg1);
        }
    };

    public void setLoading(ProgressBar progressBar) {
        progressBar.setVisibility(VISIBLE);
    }

    public void stopLoading(ProgressBar progressBar) {
        progressBar.setVisibility(GONE);
    }

    @Override
    protected void performFiltering(CharSequence text, int keyCode) {
        super.performFiltering(text, keyCode);
    }

    @Override
    protected void performFiltering(CharSequence text, int start, int end, int keyCode) {
        Log.d("Character", text.toString());
        Log.d("Start", String.valueOf(start));
        Log.d("End", String.valueOf(end));
        Log.d("Keycode", String.valueOf(keyCode));
        Log.d("text", String.valueOf(text.charAt(start)));
        if (text.charAt(start) == '@') {
            Log.d("@Character", text.toString());
            Log.d("@Start", String.valueOf(start));
            Log.d("@End", String.valueOf(end));
            Log.d("@Keycode", String.valueOf(keyCode));
            start = start + 1;
        } else if (text.charAt(start) == '/') {
            Log.d("/Character", text.toString());
            Log.d("/Start", String.valueOf(start));
            Log.d("/End", String.valueOf(end));
            Log.d("/Keycode", String.valueOf(keyCode));
            start = start + 1;
        }
        start = start - 1;
        //mHandler.removeMessages(MESSAGE_TEXT_CHANGED);
        //mHandler.sendMessageDelayed(mHandler.obtainMessage(MESSAGE_TEXT_CHANGED, text), mAutoCompleteDelay);
        super.performFiltering(text, start, end, keyCode);
    }

    @Override
    protected void replaceText(CharSequence text) {
        super.replaceText(text);
    }

    @Override
    public void onFilterComplete(int count) {
        super.onFilterComplete(count);
    }
}
