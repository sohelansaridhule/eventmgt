package test.admin.eventmanagement;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Register extends AppCompatActivity implements View.OnClickListener {


    EditText textName,textPhone,textColg,textUserName,TextPass;
    Button Submit;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }

    void init()
    {
        dbHelper=new DBHelper(Register.this);
        textName=(EditText)findViewById(R.id.nameEditText);
        textPhone=(EditText)findViewById(R.id.phoneEditText);
        textColg=(EditText)findViewById(R.id.colgEditText);
        textUserName=(EditText)findViewById(R.id.usernameEditText);
        TextPass=(EditText)findViewById(R.id.passwordEditText);
        Submit=(Button)findViewById(R.id.submitButton);
        Submit.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.submitButton:
                //String mobile,String colg,String uname,String pass

                dbHelper.insertUser(textName.getText().toString().trim(),textPhone.getText().toString().trim(),textColg.getText().toString().trim(),
                textUserName.getText().toString().trim(),TextPass.getText().toString().trim());
                break;
        }

    }
}
