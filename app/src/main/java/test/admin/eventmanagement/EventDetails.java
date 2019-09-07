package test.admin.eventmanagement;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import test.admin.eventmanagement.util.SessionManager;

public class EventDetails extends AppCompatActivity implements View.OnClickListener {

    private int eventId = 0;
    private String title = "", caption = "", date = "", imagePath = "";
    private EditText edtTitle, edtDate, edtCaption;
    private ImageView image, ivDelete;
    Button btnUpdate;

    private File photoFile;
    private String imageFilePath = "";
    String selectedImagePath = "";
    private static final int IMAGE_PICK = 300;
    private static final int REQUEST_CAPTURE_IMAGE = 100;
    SessionManager sessionManager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        init();


    }

    private void init() {
        sessionManager = new SessionManager(getApplicationContext());
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            title = bundle.getString("title");
            caption = bundle.getString("caption");
            date = bundle.getString("date");
            imagePath = bundle.getString("imagePath");
            eventId = bundle.getInt("eventId");
        }
        edtTitle = findViewById(R.id.edtTitle);
        edtDate = findViewById(R.id.edtDate);
        edtCaption = findViewById(R.id.edtCaption);
        image = findViewById(R.id.image);
        image.setOnClickListener(this);
        ivDelete = findViewById(R.id.ivDelete);
        ivDelete.setOnClickListener(this);

        btnUpdate = findViewById(R.id.btnUpdate);
        btnUpdate.setOnClickListener(this);

        if (sessionManager.getUserType().equalsIgnoreCase(DBHelper.STUDENT))
            btnUpdate.setText("Participate");

        image.setImageURI(Uri.parse(imagePath));
        edtTitle.setText(title);
        edtDate.setText(date);
        edtCaption.setText(caption);

        edtTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                title = charSequence.toString();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        edtCaption.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                caption = charSequence.toString();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        edtDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Date dated = Calendar.getInstance().getTime();

                SimpleDateFormat output = new SimpleDateFormat("dd-MM-yyyy");
                Date c = Calendar.getInstance().getTime();
                String formattedDate = output.format(c);

                int yr = Integer.parseInt(formattedDate.split("-")[2]);


                DatePickerDialog datePickerDialog = new DatePickerDialog(EventDetails.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        edtDate.setText(dayOfMonth+"-"+(month+1)+"-"+year);
                        date = dayOfMonth+"-"+(month+1)+"-"+year;
                    }
                }, yr , dated.getMonth(), dated.getDay());

                datePickerDialog.show();

            }
        });

        if (sessionManager.getUserType().equalsIgnoreCase(DBHelper.STUDENT))
            ivDelete.setVisibility(View.GONE);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ivDelete:
                new AlertDialog.Builder(EventDetails.this)
                        .setMessage("Are you sure to delete the event?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                DBHelper dbHelper = new DBHelper(getApplicationContext());
                                if (dbHelper.deleteEvent(title) == 1){
                                    dialogInterface.dismiss();
                                    onBackPressed();
                                }

                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                            }
                        }).create().show();

            break;

            case R.id.btnUpdate:
                if (sessionManager.getUserType().equalsIgnoreCase(DBHelper.STUDENT)){
                    DBHelper dbHelper = new DBHelper(getApplicationContext());
                    if (!sessionManager.getUserName().equals("") && eventId != 0){
                        if (dbHelper.insertParticipte(sessionManager.getUserName(),String.valueOf(eventId), title, sessionManager.getUserColg()) > 0){
                            Toast.makeText(EventDetails.this, "Participated", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else {
                        Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    updateEvent();
                }

                break;

            case R.id.image:
                if (hasCameraAndStoragePermission())
                    actionDialogBox();
                else
                    requestForCameraAndStorage();
                break;
        }
    }

    public void updateEvent(){
        if (edtTitle.getText().toString().isEmpty()){
            Toast.makeText(this, "Title is mandatory", Toast.LENGTH_SHORT).show();
        }
        else if (edtCaption.getText().toString().isEmpty()){
            Toast.makeText(this, "Caption is mandatory", Toast.LENGTH_SHORT).show();
        }
        else if (edtDate.getText().toString().isEmpty()){
            Toast.makeText(this, "Date is mandatory", Toast.LENGTH_SHORT).show();
        }
        else{
            DBHelper dbHelper1 = new DBHelper(getApplicationContext());
            dbHelper1.updateEvent(eventId, title, caption, date, imagePath);
            Toast.makeText(this, "Updated", Toast.LENGTH_SHORT).show();
            onBackPressed();
        }

    }

    public boolean hasCameraAndStoragePermission() {
        int result = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        int result1 = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        if (result == PackageManager.PERMISSION_GRANTED && result1 == PackageManager.PERMISSION_GRANTED)
            return true;
        else
            return false;
    }

    private void requestForCameraAndStorage() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 101);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 101) {
            if (hasCameraAndStoragePermission()) {
                image.performClick();
            } else {
                requestForCameraAndStorage();
            }
        }

    }

    public void actionDialogBox() {
        AlertDialog.Builder adb = new AlertDialog.Builder(EventDetails.this);
        adb.setTitle("Select Image Using");
        final String[] values = new String[]{"Take Picture", "Choose From Gallery"};


        adb.setItems(values, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                String clickedItemValue = Arrays.asList(values).get(which);
                String actionType = clickedItemValue;

                switch (actionType) {
                    case "Choose From Gallery":
                        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        intent.setType("image/*");
                        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        startActivityForResult(Intent.createChooser(intent, "Select Photo"), IMAGE_PICK);

                        break;

                    case "Take Picture":
                        openCameraIntent();
                        break;

                }
            }
        });
        adb.show();
    }

    private void openCameraIntent() {
        Intent pictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (pictureIntent.resolveActivity(getPackageManager()) != null) {

            photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {

                ex.printStackTrace();
            }
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(EventDetails.this, "test.admin.eventmanagement.provider", photoFile);
                pictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                pictureIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                startActivityForResult(pictureIntent, REQUEST_CAPTURE_IMAGE);
            }
        }
    }

    private File createImageFile() throws IOException {
        String timeStamp =
                new SimpleDateFormat("yyyyMMdd_HHmmss",
                        Locale.getDefault()).format(new Date());
        String imageFileName = "IMG_" + timeStamp + "_";
        File storageDir =
                getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpeg",         /* suffix */
                storageDir      /* directory */
        );

        imageFilePath = image.getAbsolutePath();
        return image;
    }


    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case IMAGE_PICK:
                    imagePath = selectedImagePath = getAbsolutePath(intent.getData());
                    File file2 = new File(selectedImagePath);
                    image.setImageURI(Uri.parse(selectedImagePath));
                    break;

                case REQUEST_CAPTURE_IMAGE:
                    File file = new File(imageFilePath);
                    imagePath =  selectedImagePath = imageFilePath;
                    image.setImageURI(Uri.parse(selectedImagePath));
                    break;

                default:
                    break;
            }
        }
    }

    public String getAbsolutePath(Uri uri) {
        String[] projection = {MediaStore.MediaColumns.DATA};
        @SuppressWarnings("deprecation")
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        if (cursor != null) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } else
            return null;
    }

}
