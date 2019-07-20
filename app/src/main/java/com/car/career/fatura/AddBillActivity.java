package com.car.career.fatura;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.appcompat.app.AppCompatActivity;

import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.car.career.R;
import com.car.career.database.DBHelper;
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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddBillActivity extends AppCompatActivity {
    private final static int PICK_IMAGE = 1001;

    private ImageView imageView_bill;
    private Spinner spinner_kategori;
    private EditText editText_kurum;
    private EditText editText_tutar;
    private EditText editText_tarih;
    private EditText editText_saat;
    private EditText editText_yakit;
    private EditText editText_adres;
    private ImageButton imageButton_saat;
    private ImageButton imageButton_tarih;
    String[] category = { "Market", "Akaryakıt", "Yemek", "Diğer"};
    String selected_category;
    DBHelper dbHelper ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bill);
        dbHelper = DBHelper.getInstance(this);
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

            if(editText_kurum.getText().toString().equals("")|
                    editText_tutar.getText().toString().equals("")|
                    editText_tarih.getText().toString().equals("")|
                    editText_saat.getText().toString().equals("")|
                    editText_yakit.getText().toString().equals("")
            ){
                Toast.makeText(getApplicationContext(), "boş olamaz", Toast.LENGTH_SHORT).show();
            }else {
                String kurum = editText_kurum.getText().toString();
                String tutar = editText_tutar.getText().toString();
                String tarih = editText_tarih.getText().toString();
                String saat = editText_saat.getText().toString();
                String yakit = editText_yakit.getText().toString();
               // String type = selected_category;

                dbHelper.insertBill(Double.parseDouble(yakit),kurum,saat,tarih,Double.parseDouble(tutar));
                //dbHelper.insertBill(6565,Double.parseDouble(yakit),Double.parseDouble(tutar));

                finish();

                return true;

            }
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


        editText_kurum= findViewById(R.id.editText_kurum);
        editText_tutar = findViewById(R.id.editText_tutar);
        editText_tarih = findViewById(R.id.editText_tarih);
        editText_saat = findViewById(R.id.editText_saat);
        editText_yakit = findViewById(R.id.editText_yakit);


        imageButton_tarih = findViewById(R.id.imageButton_editText_tarih);
        imageButton_tarih.setOnClickListener(new View.OnClickListener() {
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
                        editText_tarih.setText(result);
                    }

                }, year, month, day);

                datePickerDialog.show();

            }
        });

        imageButton_saat = findViewById(R.id.imageButton_editText_saat);
        imageButton_saat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(AddBillActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        if(selectedMinute<10)
                            editText_saat.setText( selectedHour + ":0" + selectedMinute);
                        else
                            editText_saat.setText( selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
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

        FirebaseApp.initializeApp(this);
        FirebaseVisionImage firebaseVisionImage = FirebaseVisionImage.fromBitmap(bitmapImage);
        FirebaseVisionTextRecognizer detector = FirebaseVision.getInstance().getOnDeviceTextRecognizer();
        final Task<FirebaseVisionText> result = detector.processImage(firebaseVisionImage);
        result.addOnSuccessListener(new OnSuccessListener<FirebaseVisionText>() {
            @Override
            public void onSuccess(FirebaseVisionText firebaseVisionText) {
                if (result.getResult() != null) {
                    List<FirebaseVisionText.TextBlock> list = result.getResult().getTextBlocks();

                    String kurum = findKurum(list);
                    String tutar = findTutar(list);
                    String tarih = findTarih(list);
                    String saat = findSaat(list);
                    String yakit = findYakit(list);


                    editText_kurum.setText(kurum);
                    editText_tutar.setText(tutar);
                    editText_tarih.setText(tarih);
                    editText_saat.setText(saat);
                    editText_yakit.setText(yakit);
                }
            }
        });
        result.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });

    }

    public String findKurum(List<FirebaseVisionText.TextBlock> textList){

    String [] keywords = {"a.s.","a.s","a.ş.","a.ş","a.$","a.$.","$i","$ti","$tı","şti","sti","stı","ştı","sirketi",
        "şirketi","sirketı","sırketı","sırketi","şirketı","şırketı","şırketi","subesi","şubesi"
    };

        for (int i=0;i<textList.size();i++){

        String input=textList.get(i).getText();

        String lowcase_input = input.toLowerCase();
        for (int j=0;j<keywords.length;j++){
            if(lowcase_input.contains(keywords[j])){

                String kurum = input.substring(0,lowcase_input.indexOf(keywords[j])+keywords[j].length()).trim();

                return kurum;
            }
        }
    }
        return null;
}

    public String findTutar(List<FirebaseVisionText.TextBlock> textList){
        List<String> tutars= new ArrayList<>();

        String dates_regex = "\\*?(\\d)+,(\\d)+$";

        for (int i=0;i<textList.size();i++){

            String input=textList.get(i).getText();
            System.out.print(i+")  "+ input +"\n");
            Matcher m = Pattern.compile(dates_regex).matcher(input);
            if (m.find()) {
                tutars.add(m.group());
            }

        }

        System.out.println("ALL POSSIBLE TUTAR: " + tutars.toString());

        String[] arr = tutars.toArray(new String[tutars.size()]);

        String tutar = findMostNum(arr);
        tutar=tutar.replaceAll(",", ".");
        return tutar;
    }
    public String findTarih(List<FirebaseVisionText.TextBlock> textList){

        List<String> dates= new ArrayList<>();

        String dates_regex = "((0[1-9]|[12][0-9]|3[01])[- ,/.]0[1-9]|1[012])[- ,/.](19|20)\\d\\d";

        for (int i=0;i<textList.size();i++){

            String input=textList.get(i).getText();
            System.out.print(i+")  "+ input +"\n");
            Matcher m = Pattern.compile(dates_regex).matcher(input);
            if (m.find()) {
                dates.add(m.group());
            }

        }

        System.out.println("ALL POSSIBLE DATES: " + dates.toString());

        String[] arr = dates.toArray(new String[dates.size()]);

        String date = findMostFrequentWord(arr);
        date=date.replaceAll(" ",".");
        date=date.replaceAll("-",".");
        date=date.replaceAll(",",".");
        date=date.replaceAll("/",".");
        return date;
    }

    public String findSaat(List<FirebaseVisionText.TextBlock> textList){

        List<String> hours= new ArrayList<>();

        String hours_regex = "(([01][0-9]|2[0123]) ?[:;/] ?([012345][0-9]))$";



        for (int i=0;i<textList.size();i++){

            String input=textList.get(i).getText();
            Matcher m = Pattern.compile(hours_regex).matcher(input);
            if (m.find()) {
                hours.add(m.group());
            }

        }

        System.out.println("ALL POSSIBLE HOURS: " + hours.toString());

        String[] arr = hours.toArray(new String[hours.size()]);

        String hour = findMostFrequentWord(arr);
        hour=hour.replaceAll(";",":");
        hour=hour.replaceAll(" ","");
        hour=hour.replaceAll("/",":");
        return hour;


    }

    public String findYakit(List<FirebaseVisionText.TextBlock> textList){

        List<String> yakitlar= new ArrayList<>();

        String yakit_regex = "(\\w)+ ?, ?(\\w)+ ?(L|l|1)(T|t) ?(X|x)?";



        for (int i=0;i<textList.size();i++){

            String input=textList.get(i).getText();
            Matcher m = Pattern.compile(yakit_regex).matcher(input);
            if (m.find()) {
                yakitlar.add(m.group());
            }

        }

        System.out.println("ALL POSSIBLE Yakits: " + yakitlar.toString());

        String[] arr = yakitlar.toArray(new String[yakitlar.size()]);

        String yakit = findMostFrequentWord(arr);
       // 18,180LT x
        yakit=yakit.toLowerCase();
        if(yakit.indexOf("l")!=-1) {
            yakit = yakit.substring(0, yakit.indexOf("l"));
            yakit = yakit.replaceAll(" ", "");
            yakit = yakit.replaceAll("o", "0");
            yakit = yakit.replaceAll(",", ".");
        }

        return yakit;


    }


    static String findMostFrequentWord(String[] arr)
    {

        // Create HashMap to store word and it's frequency
        HashMap<String, Integer> hs = new HashMap<String, Integer>();

        // Iterate through array of words
        for (int i = 0; i < arr.length; i++) {
            // If word already exist in HashMap then increase it's count by 1
            if (hs.containsKey(arr[i])) {
                hs.put(arr[i], hs.get(arr[i]) + 1);
            }
            // Otherwise add word to HashMap
            else {
                hs.put(arr[i], 1);
            }
        }

        // Create set to iterate over HashMap
        Set<Map.Entry<String, Integer> > set = hs.entrySet();
        String key = "";
        int value = 0;

        for (Map.Entry<String, Integer> me : set) {
            // Check for word having highest frequency
            if (me.getValue() > value) {
                value = me.getValue();
                key = me.getKey();
            }
        }

        // Return word having highest frequency
        return key;
    }
    static String findMostNum(String[] arr){
        String smax="";
        double  max =0;
        double temp;
        for (int i=0;i<arr.length;i++){
            if(arr[i].contains("*")){
                temp=Double.parseDouble(arr[i].substring(1).replace(",","."));

            }else{
                temp=Double.parseDouble(arr[i].replace(",","."));
            }
            if (max<=temp)
                max=temp;
        }
        smax=(max+"").replace(".",",");
        return smax;
    }

}
