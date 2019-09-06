package test.admin.eventmanagement;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import test.admin.eventmanagement.util.SessionManager;

public class Splash extends AppCompatActivity {
SessionManager sessionManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        sessionManager = new SessionManager(getApplicationContext());


        Thread timerThread = new Thread() {
            public void run() {
                try {
                    sleep(1700);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    {
                        if(sessionManager.isLogin()){
                            startActivity(new Intent(Splash.this,HomeActivity.class));
                            finish();

                        }
                        else {
                            startActivity(new Intent(Splash.this,LoginActivity.class));
                            finish();
                        }

                    }


                }
            }
        };
        timerThread.start();

    }
}
