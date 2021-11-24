package com.example.pms;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;


public class Splashscreen extends Activity {
    private final String On = "on";

    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        Window window = getWindow();
        window.setFormat(PixelFormat.RGBA_8888);
    }

    Thread splashTread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);
        overridePendingTransition(R.anim.horizon_enter, R.anim.none);
        StartAnimations();
        PrefsHelper.init(getApplicationContext());
    }

    private void StartAnimations() {
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.alpha);
        anim.reset();
        LinearLayout l = (LinearLayout) findViewById(R.id.lin_lay);
        l.clearAnimation();
        l.startAnimation(anim);

        splashTread = new Thread() {
            @Override
            public void run() {
                try {
                    int waited = 0;
                    // Splash screen pause time(3000)
                    while (waited < 1500) {
                        sleep(100);
                        waited += 100;
                    }
                    if (On.equals(PrefsHelper.read("AutoLogin", ""))) {
                        if (On.equals(PrefsHelper.read("Lock", ""))) {
                            Intent intent = new Intent(Splashscreen.this,
                                    ScreenLockMain.class);
                            startActivity(intent);
                            overridePendingTransition(R.anim.horizon_enter, R.anim.none);
                            Splashscreen.this.finish();
                        } else {
                            Intent intent = new Intent(Splashscreen.this, MainActivity.class);
                            startActivity(intent);
                            overridePendingTransition(R.anim.horizon_enter, R.anim.none);
                            Splashscreen.this.finish();
                        }
                    } else {
                        Intent intent = new Intent(Splashscreen.this,
                                Login.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(intent);
                        overridePendingTransition(R.anim.horizon_enter, R.anim.none);
                        Splashscreen.this.finish();
                    }
                } catch (InterruptedException e) {
                    // do nothing
                } finally {
                    Splashscreen.this.finish();
                }

            }
        };
        splashTread.start();
    }
}