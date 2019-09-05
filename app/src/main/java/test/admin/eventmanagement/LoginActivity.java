package test.admin.eventmanagement;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    Intent i;
    Button  SubMit;
    TextView Register;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        init();
    }

    void init()
    {
        SubMit=(Button)findViewById(R.id.loginButton);
        Register=(TextView)findViewById(R.id.RegiButton);

        SubMit.setOnClickListener(this);
        Register.setOnClickListener(this);
        }

    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.loginButton:
                startActivity(new Intent(LoginActivity.this,HomeActivity.class));
                finish();
                break;
                case R.id.RegiButton:
                startActivity(new Intent(LoginActivity.this,Register.class));
                finish();
                break;

        }

    }
}
