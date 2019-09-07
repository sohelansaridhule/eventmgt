package test.admin.eventmanagement;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import test.admin.eventmanagement.util.SessionManager;

public class AddEvent extends AppCompatActivity {

    private static final int IMAGE_PICK = 300;
    private static final int REQUEST_CAPTURE_IMAGE = 100;
    ImageView imagePost;
    TextView etTitle, etDate, etCaption;
    Button btnAdd;
    String imgePath = "";
    SessionManager sessionManager;

    DBHelper dbHelper;

    public static final int PICK_IMAGE = 1;

    private File photoFile;
    private String imageFilePath = "";
    String selectedImagePath = "";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);

        sessionManager = new SessionManager(getApplicationContext());
        imagePost = findViewById(R.id.imagePost);
        etTitle = findViewById(R.id.etTitle);
        etDate = findViewById(R.id.etDate);
        etCaption = findViewById(R.id.etCaption);
        btnAdd = findViewById(R.id.btnAdd);

        dbHelper = new DBHelper(getApplicationContext());

        imagePost.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {

                if (hasCameraAndStoragePermission()) {
                    actionDialogBox();
                } else {
                    requestForCameraAndStorage();
                }

            }
        });


        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (etTitle.getText().toString().isEmpty()){
                    etTitle.setError("This is mandatory");
                }
                else if (etCaption.getText().toString().isEmpty()){
                    etCaption.setError("This is mandatory");
                }
                else if (etDate.getText().toString().isEmpty()){
                    etDate.setError("This is mandatory");
                }
                else {
                    long r =0;
                    r= dbHelper.insertEvent(selectedImagePath , etTitle.getText().toString(),etDate.getText().toString(), etCaption.getText().toString(), sessionManager.getUserColg());
                    if (r > 0){
                        Toast.makeText(AddEvent.this, "Event Added", Toast.LENGTH_SHORT).show();
                        onBackPressed();
                    }
                }

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
                imagePost.performClick();
            } else {
                requestForCameraAndStorage();
            }
        }

    }

    public void actionDialogBox() {
        AlertDialog.Builder adb = new AlertDialog.Builder(AddEvent.this);
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
                Uri photoURI = FileProvider.getUriForFile(AddEvent.this, "test.admin.eventmanagement.provider", photoFile);
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
                    selectedImagePath = getAbsolutePath(intent.getData());
                    File file2 = new File(selectedImagePath);
                    imagePost.setImageURI(Uri.parse(selectedImagePath));
                    break;

                case REQUEST_CAPTURE_IMAGE:
                    File file = new File(imageFilePath);
                    selectedImagePath = imageFilePath;
                    imagePost.setImageURI(Uri.parse(selectedImagePath));
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
