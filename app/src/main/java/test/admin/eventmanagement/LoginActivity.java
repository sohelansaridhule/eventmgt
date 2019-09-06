package test.admin.eventmanagement;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import test.admin.eventmanagement.util.SessionManager;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    Intent i;
    Button  SubMit;
    TextView Register;
    AutoCompleteTextView colgnameEditText;
    EditText usernameEditText, userPass;
    DBHelper dbHelper;
    SessionManager sessionManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        init();
    }

    void init()
    {
        sessionManager = new SessionManager(getApplicationContext());
        dbHelper = new DBHelper(getApplicationContext());
        SubMit=(Button)findViewById(R.id.loginButton);
        Register=(TextView)findViewById(R.id.RegiButton);
        colgnameEditText = findViewById(R.id.colgnameEditText);
        usernameEditText = findViewById(R.id.usernameEditText);
        userPass = findViewById(R.id.userPass);

        SubMit.setOnClickListener(this);
        Register.setOnClickListener(this);


        String[] clgNames = dbHelper.getAllColleges();
        if (clgNames.length >0){
            ArrayAdapter adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, clgNames);
            colgnameEditText.setAdapter(adapter);
            colgnameEditText.setThreshold(2);
        }

    }

    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.loginButton:
                if (dbHelper.login(colgnameEditText.getText().toString(),usernameEditText.getText().toString(),userPass.getText().toString())){
                    sessionManager.setIsLogin();
                    startActivity(new Intent(LoginActivity.this,HomeActivity.class));
                    finish();
                }
                else {
                    Toast.makeText(this, "Invalid College or login details", Toast.LENGTH_SHORT).show();
                }
                break;
                case R.id.RegiButton:
                startActivity(new Intent(LoginActivity.this,Register.class));
                finish();
                break;

        }

    }
}
