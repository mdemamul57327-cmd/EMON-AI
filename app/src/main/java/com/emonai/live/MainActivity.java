package com.emonai.live;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

    private WindowManager windowManager;
    private TextView floatingButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && !Settings.canDrawOverlays(this)) {

            Intent intent = new Intent(
                    Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:" + getPackageName())
            );

            startActivity(intent);

            Toast.makeText(
                    this,
                    "EMON AI চালু করতে Overlay Permission দিন",
                    Toast.LENGTH_LONG
            ).show();

        } else {
            showFloatingButton();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M
                || Settings.canDrawOverlays(this)) {
            showFloatingButton();
        }
    }

    private void showFloatingButton() {

        if (floatingButton != null) {
            return;
        }

        windowManager =
                (WindowManager) getSystemService(WINDOW_SERVICE);

        floatingButton = new TextView(this);

        floatingButton.setText("EMON\nAI");
        floatingButton.setTextColor(Color.WHITE);
        floatingButton.setTextSize(13);
        floatingButton.setGravity(Gravity.CENTER);
        floatingButton.setBackgroundColor(Color.rgb(20, 100, 220));

        final WindowManager.LayoutParams params =
                new WindowManager.LayoutParams(
                        100,
                        100,
                        Build.VERSION.SDK_INT >= Build.VERSION_CODES.O
                                ? WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
                                : WindowManager.LayoutParams.TYPE_PHONE,
                        WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                        PixelFormat.TRANSLUCENT
                );

        params.gravity = Gravity.TOP | Gravity.START;
        params.x = 30;
        params.y = 300;

        floatingButton.setOnTouchListener(
                new View.OnTouchListener() {

                    private int initialX;
                    private int initialY;
                    private float initialTouchX;
                    private float initialTouchY;

                    @Override
                    public boolean onTouch(
                            View v,
                            MotionEvent event) {

                        switch (event.getAction()) {

                            case MotionEvent.ACTION_DOWN:

                                initialX = params.x;
                                initialY = params.y;

                                initialTouchX = event.getRawX();
                                initialTouchY = event.getRawY();

                                return true;

                            case MotionEvent.ACTION_MOVE:

                                params.x = initialX +
                                        (int) (event.getRawX()
                                                - initialTouchX);

                                params.y = initialY +
                                        (int) (event.getRawY()
                                                - initialTouchY);

                                windowManager.updateViewLayout(
                                        floatingButton,
                                        params
                                );

                                return true;

                            case MotionEvent.ACTION_UP:
                                return true;
                        }

                        return false;
                    }
                });

        windowManager.addView(
                floatingButton,
                params
        );
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (floatingButton != null
                && windowManager != null) {

            try {
                windowManager.removeView(
                        floatingButton
                );
            } catch (Exception ignored) {
            }

            floatingButton = null;
        }
    }
}
