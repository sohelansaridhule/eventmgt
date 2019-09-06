package test.admin.eventmanagement;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class Register extends AppCompatActivity implements View.OnClickListener {


    EditText textName,textPhone,textColg,textUserName,TextPass;
    Button Submit;
    Spinner spUserType;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        init();
    }

    void init()
    {
        dbHelper=new DBHelper(Register.this);
        spUserType = findViewById(R.id.spUserType);
        textName=(EditText)findViewById(R.id.nameEditText);
        textPhone=(EditText)findViewById(R.id.phoneEditText);
        textColg=(EditText)findViewById(R.id.colgEditText);
        textUserName=(EditText)findViewById(R.id.usernameEditText);
        TextPass=(EditText)findViewById(R.id.passwordEditText);
        Submit=(Button)findViewById(R.id.submitButton);
        Submit.setOnClickListener(this);


        String[] types = {"Head","Student"};
        ArrayAdapter adapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1, types);
        spUserType.setAdapter(adapter);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.submitButton:
                //String mobile,String colg,String uname,String pass

                if (textName.getText().toString().isEmpty())
                    textName.setError("Enter this filed");
                else if (textPhone.getText().toString().isEmpty())
                    textPhone.setError("Enter this filed");
                else if (textColg.getText().toString().isEmpty())
                    textColg.setError("Enter this filed");
                else if (textUserName.getText().toString().isEmpty())
                    textUserName.setError("Enter this filed");
                else if (TextPass.getText().toString().isEmpty())
                    TextPass.setError("Enter this filed");
                else {
                    long no =  dbHelper.insertUser(textName.getText().toString().trim(),textPhone.getText().toString().trim(),textColg.getText().toString().trim(),
                            textUserName.getText().toString().trim(),TextPass.getText().toString().trim(), spUserType.getSelectedItem().toString().trim());
                    Toast.makeText(this, "User added", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(Register.this, LoginActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));

                }

                break;
        }

    }
}
