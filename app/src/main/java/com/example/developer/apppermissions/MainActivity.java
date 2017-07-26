package com.example.developer.apppermissions;

import android.Manifest;
import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;

import java.util.TimeZone;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

@RuntimePermissions
public class MainActivity extends AppCompatActivity {

    private static final int STARTING_COLOR = Color.RED;
    private static final OvershootInterpolator OVERSHOOT_INTERPOLATOR = new OvershootInterpolator(4);
    ImageView colorView;
    ObjectAnimator colorAnimator;
    ArgbEvaluator colorEvaluator;
    AnimatorSet animatorSet;
    TextView timeZoneView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //MainActivityPermissionsDispatcher.openCameraWithCheck(this);
        setUpBounceAnimator();
        setUpAutoCompleteTextView();
        //setUpMaterialProgress();
        setUpTimeZone();
    }

    private void setUpTimeZone() {
        timeZoneView = (TextView) findViewById(R.id.time_zone_text);
        TimeZone timeZone = TimeZone.getDefault();
        //timeZoneView.setText(tz.getDisplayName());
        //timeZoneView.setText(String.valueOf(tz.getRawOffset()));
        //timeZoneView.setText(String.valueOf(timeZone.getDisplayName(Locale.getDefault())));
        //timeZoneView.setText(String.valueOf(timeZone.getID()));
        timeZoneView.setText(String.valueOf(timeZone.getDisplayName(false, TimeZone.SHORT)));
    }

    private void setUpMaterialProgress() {
        new MaterialDialog.Builder(this)
                .progress(false, 100)
                .show();
    }

    private void setUpBounceAnimator() {
        colorView = (ImageView) findViewById(R.id.color_icon);
        colorView.getDrawable().setColorFilter(STARTING_COLOR, PorterDuff.Mode.MULTIPLY);
        colorEvaluator = new ArgbEvaluator();
        animatorSet = new AnimatorSet();
        colorAnimator = ObjectAnimator.ofObject(colorView, "colorFilter", colorEvaluator, 0, 0);

        colorView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                colorAnimator.setObjectValues(STARTING_COLOR, Color.BLUE);
                colorAnimator.setDuration(1000);
                ObjectAnimator bounceAnimX = ObjectAnimator.ofFloat(colorView, "scaleX", 0.2f, 1f);
                bounceAnimX.setDuration(300);
                bounceAnimX.setInterpolator(OVERSHOOT_INTERPOLATOR);
                ObjectAnimator bounceAnimY = ObjectAnimator.ofFloat(colorView, "scaleY", 0.2f, 1f);
                bounceAnimY.setDuration(300);
                bounceAnimY.setInterpolator(OVERSHOOT_INTERPOLATOR);
                animatorSet.play(colorAnimator).with(bounceAnimX).with(bounceAnimY);
                animatorSet.start();
               /* int currentColor = STARTING_COLOR;
                if (colorAnimator.getAnimatedValue() != null) {
                    currentColor = (Integer) colorAnimator.getAnimatedValue();
                }
                colorAnimator.setObjectValues(currentColor, Color.BLUE);
                colorAnimator.setDuration(2000);
                colorAnimator.start();*/

            }
        });
    }

    private void setUpAutoCompleteTextView() {
        CustomAutoCompleteTextView autoCompleteTextView = (CustomAutoCompleteTextView) findViewById(R.id.user_input);
        autoCompleteTextView.setThreshold(2);
        autoCompleteTextView.setTokenizer(new CustomTokenizer());
        autoCompleteTextView.setLoading(new ProgressBar(this));
        autoCompleteTextView.setAdapter(new CustomAutoCompleteAdapter(this));
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
    }

    @NeedsPermission(Manifest.permission.CAMERA)
    public void openCamera() {
        finish();
    }

    @OnShowRationale(Manifest.permission.CAMERA)
    public void showRationaleForCamera(PermissionRequest request) {
        showRationaleDialog(R.string.permission_title, request);
    }

    @OnPermissionDenied(Manifest.permission.CAMERA)
    public void onCameraDenied() {
        //MainActivityPermissionsDispatcher.openCameraWithCheck(this);
    }

    @OnNeverAskAgain(Manifest.permission.CAMERA)
    public void onCameraNeverAskAgain() {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        MainActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    private void showRationaleDialog(@StringRes int messageResId, final PermissionRequest request) {
        new AlertDialog.Builder(this)
                .setPositiveButton("Allow", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(@NonNull DialogInterface dialog, int which) {
                        request.proceed();
                    }
                })
                .setNegativeButton("Deny", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(@NonNull DialogInterface dialog, int which) {
                        request.cancel();
                    }
                })
                .setCancelable(false)
                .setMessage(messageResId)
                .show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
