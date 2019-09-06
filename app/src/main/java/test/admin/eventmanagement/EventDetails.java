package test.admin.eventmanagement;

import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class EventDetails extends AppCompatActivity implements View.OnClickListener {

    private String title = "", caption = "", date = "", imagePath = "";
    private EditText edtTitle, edtDate, edtCaption;
    private ImageView image, ivDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        init();


    }

    private void init() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            title = bundle.getString("title");
            caption = bundle.getString("caption");
            date = bundle.getString("date");
            imagePath = bundle.getString("imagePath");
        }
        edtTitle = findViewById(R.id.edtTitle);
        edtDate = findViewById(R.id.edtDate);
        edtCaption = findViewById(R.id.edtCaption);
        image = findViewById(R.id.image);
        ivDelete = findViewById(R.id.ivDelete);
        ivDelete.setOnClickListener(this);

        image.setImageURI(Uri.parse(imagePath));
        edtTitle.setText(title);
        edtDate.setText(date);
        edtCaption.setText(caption);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ivDelete:
                DBHelper dbHelper = new DBHelper(getApplicationContext());
                if (dbHelper.deleteEvent(title) == 1){
                    Toast.makeText(this, "Event deleted", Toast.LENGTH_SHORT).show();
                    onBackPressed();
                }

            break;
        }
    }
}
