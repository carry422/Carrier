package com.car.career.fatura;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.appcompat.app.AppCompatActivity;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.car.career.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.text.FirebaseVisionText;
import com.google.firebase.ml.vision.text.FirebaseVisionTextRecognizer;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.List;

public class AddBillActivity extends AppCompatActivity {
    private final static int PICK_IMAGE = 1001;

    private ImageView imageView_bill;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bill);

        load();
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE) {
            Bitmap bitmap = null;
            if (data.getData()!=null) {
                try {

                    InputStream inputStream = getContentResolver().openInputStream(data.getData());
                    bitmap = BitmapFactory.decodeStream(inputStream);

                    if (inputStream != null)
                        inputStream.close();
                }
                catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }

            }
            else {
                if (data.getExtras() != null)
                    bitmap = (Bitmap) data.getExtras().get("data");
            }

            imageView_bill.setImageBitmap(bitmap);
            TextView textView = findViewById(R.id.textView_hint);
            textView.setVisibility(View.GONE);
            detectText(bitmap);
        }
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add_bill, menu);
        return super.onCreateOptionsMenu(menu);
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();

            return true;
        }
        else if (item.getItemId() == R.id.action_kaydet) {
            finish();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void load() {
        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            final String[] permissions = new String[]{Manifest.permission.CAMERA};
            if (!ActivityCompat.shouldShowRequestPermissionRationale(AddBillActivity.this, Manifest.permission.CAMERA)) {
                ActivityCompat.requestPermissions(AddBillActivity.this, permissions, 1);
            }
        }

        setTitle("Faturalarım");
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        final EditText editText_startDate = findViewById(R.id.editText_startDate);
        final EditText editText_endDate = findViewById(R.id.editText_endDate);

        ImageButton imageButton_startDate = findViewById(R.id.imageButton_startDate);
        imageButton_startDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(AddBillActivity.this, new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        String result = dayOfMonth + "." + (monthOfYear + 1) + "." + year;
                        editText_startDate.setText(result);
                    }

                }, year, month, day);

                datePickerDialog.show();

            }
        });

        ImageButton imageButton_endDate = findViewById(R.id.imageButton_endDate);
        imageButton_endDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(AddBillActivity.this, new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                String result = dayOfMonth + "." + (monthOfYear + 1) + "." + year;
                                editText_endDate.setText(result);
                            }

                 }, year, month, day);

                datePickerDialog.show();
            }
        });

        imageView_bill = findViewById(R.id.imageView_bill);
        imageView_bill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT,null);
                galleryIntent.setType("image/*");
                galleryIntent.addCategory(Intent.CATEGORY_OPENABLE);

                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                Intent[] intentArray =  { cameraIntent };

                Intent chooser = new Intent(Intent.ACTION_CHOOSER);
                chooser.putExtra(Intent.EXTRA_INTENT, galleryIntent);
                chooser.putExtra(Intent.EXTRA_INITIAL_INTENTS, intentArray);
                chooser.putExtra(Intent.EXTRA_TITLE, "Take or choose an image");

                startActivityForResult(chooser, PICK_IMAGE);
            }
        });
    }

    public void detectText(Bitmap bitmapImage) {
        final TextView editText_title = findViewById(R.id.editText_title);
        final TextView editText_startDate = findViewById(R.id.editText_startDate);
        final TextView editText_endDate = findViewById(R.id.editText_endDate);

        FirebaseApp.initializeApp(this);
        FirebaseVisionImage firebaseVisionImage = FirebaseVisionImage.fromBitmap(bitmapImage);
        FirebaseVisionTextRecognizer detector = FirebaseVision.getInstance().getOnDeviceTextRecognizer();
        final Task<FirebaseVisionText> result = detector.processImage(firebaseVisionImage);
        result.addOnSuccessListener(new OnSuccessListener<FirebaseVisionText>() {
            @Override
            public void onSuccess(FirebaseVisionText firebaseVisionText) {
                if (result.getResult() != null) {
                    List<FirebaseVisionText.TextBlock> list = result.getResult().getTextBlocks();

                    editText_title.setText(list.get(0).getText());
                    editText_startDate.setText(list.get(1).getText());
                    editText_endDate.setText(list.get(2).getText());
                }
            }
        });
        result.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });

    }

}
