package test.admin.eventmanagement;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        Thread timerThread = new Thread() {
            public void run() {
                try {
                    sleep(1700);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    {
                       startActivity(new Intent(Splash.this,LoginActivity.class));
                       finish();

                    }


                }
            }
        };
        timerThread.start();

    }
}
