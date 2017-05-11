package com.example.developer.apppermissions;

import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.Log;
import android.widget.MultiAutoCompleteTextView;

/**
 * Created by developer on 10/5/17.
 */

public class CustomTokenizer implements MultiAutoCompleteTextView.Tokenizer {
    @Override
    public int findTokenStart(CharSequence text, int cursor) {
  //      Log.d("cursor", String.valueOf(cursor));
//        Log.d("cursor - 1", String.valueOf(text.charAt(cursor - 1)));
        int i = cursor;
        while (i > 0 && lookUp(text, i)) {
            i--;
        }

        if (i < 1 || lookUp(text, i)) {
            return cursor;
        }

        return i;
    }

    private boolean lookUp(CharSequence text, int cursor) {
        Log.d("look up","entry");
        if (text.charAt(cursor - 1) != '@' && text.charAt(cursor - 1) != '/') {
            Log.d("look up", "entered @");
            return true;
        }
       /* if (text.charAt(cursor - 1) != '/') {
            Log.d("look up", "entered /");
            return true;
        }*/
        Log.d("look up","nothing");
        return false;
    }


    @Override
    public int findTokenEnd(CharSequence text, int cursor) {
        Log.d("End token", "end");
        return 0;
    }

    @Override
    public CharSequence terminateToken(CharSequence text) {
        Log.d("Terminate token", "terminate");
        int i = text.length();
        while (i > 0 && text.charAt(i - 1) == ' ') {
            i--;
        }
        if (text instanceof Spanned) {
            SpannableString sp = new SpannableString(text + " ");
            TextUtils.copySpansFrom((Spanned) text, 0, text.length(), Object.class, sp, 0);
            return sp;
        } else {
            return text + " ";
        }
    }
}
