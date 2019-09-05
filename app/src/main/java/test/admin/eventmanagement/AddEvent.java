package test.admin.eventmanagement;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddEvent extends AppCompatActivity {

    ImageView imagePost;
    TextView etTitle, etDate, etCaption;
    Button btnAdd;
    String imgePath = "";

    DBHelper dbHelper;

    public static final int PICK_IMAGE = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);

        imagePost = findViewById(R.id.imagePost);
        etTitle = findViewById(R.id.etTitle);
        etDate = findViewById(R.id.etDate);
        etCaption = findViewById(R.id.etCaption);
        btnAdd = findViewById(R.id.btnAdd);

        dbHelper = new DBHelper(getApplicationContext());

        imagePost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickGellery();
            }
        });


        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbHelper.insertEvent(imgePath,etTitle.getText().toString(),etDate.getText().toString(), etCaption.getText().toString());
            }
        });

        etDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date date = Calendar.getInstance().getTime();

                SimpleDateFormat output = new SimpleDateFormat("dd-MM-yyyy");
                Date c = Calendar.getInstance().getTime();
                String formattedDate = output.format(c);

                int yr = Integer.parseInt(formattedDate.split("-")[2]);


                DatePickerDialog datePickerDialog = new DatePickerDialog(AddEvent.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        etDate.setText(dayOfMonth+"-"+(month+1)+"-"+year);
                    }
                }, yr , date.getMonth(), date.getDay());

                datePickerDialog.show();
            }
        });

    }

    public void pickGellery(){

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE) {
            imgePath = FileUtils.getPath(getApplicationContext(), data.getData());
            imagePost.setImageURI(Uri.parse(imgePath));
            //TODO: action
        }
    }
}
