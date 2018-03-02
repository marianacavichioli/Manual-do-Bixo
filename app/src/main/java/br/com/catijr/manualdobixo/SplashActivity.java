package br.com.catijr.manualdobixo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.mixpanel.android.mpmetrics.MixpanelAPI;
import com.rollbar.android.Rollbar;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Rollbar.init(this, "66b476965743417c8b9fc2e5237cce29", "production");

        TrackerService.getInstance().track("Splash Screen opened", getApplicationContext());

        (new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    sleep(3000);
                } catch (InterruptedException e) {
                    Rollbar.reportException(e, "warning", "Error on opening thread");
                    e.printStackTrace();
                }

                Intent i = new Intent(SplashActivity.this, HomeActivity.class);
                startActivity(i);
                finish();
            }
        }).start();
    }
}
