package edu.upenn.cis350.projectcopy;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

public class ProfileActivity extends AppCompatActivity {
    String updatedBio;
    String updatedPath;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        String name = getIntent().getStringExtra("name");
        String bio = getIntent().getStringExtra("bio");
        updatedBio = bio;
        String imageString = getIntent().getStringExtra("image");
        updatedPath = imageString;

        TextView nameView = findViewById(R.id.personName);
        TextView bioView = findViewById(R.id.bio);

        nameView.setText(name);
        bioView.setText(bio);

        FloatingActionButton fab = findViewById(R.id.cameraButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                takePhotoFromCamera();
            }
        });
        if (!imageString.equals("")) {
            ImageView profileImage = findViewById(R.id.profile_pic);

            File sd = Environment.getExternalStorageDirectory();
            File image = new File(sd + imageString);
            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
            Bitmap bitmap = BitmapFactory.decodeFile(image.getAbsolutePath(),bmOptions);
            profileImage.setImageBitmap(bitmap);
        }
        FloatingActionButton returnButton = findViewById(R.id.returnButton);
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = getIntent();
                TextView bioStuff = findViewById(R.id.bio);
                updatedBio = bioStuff.getText().toString();
                Toast toast = Toast.makeText(getApplicationContext(), updatedBio, Toast.LENGTH_SHORT);
                toast.show();
                i.putExtra("newBio", updatedBio);
                i.putExtra("newImage", updatedPath);
                finish();
            }
        });

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bundle extras = data.getExtras();
        Bitmap thumbnail = (Bitmap) extras.get("data");
        ImageView profileImage = findViewById(R.id.profile_pic);
        profileImage.setImageBitmap(thumbnail);
        updatedPath = saveImage(thumbnail);
    }
    private void takePhotoFromCamera() {
        if (!checkPermission()) {
            requestPermission();
        } else {
            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(cameraIntent, 2);

        }
    }
    private void requestPermission(){
        String [] permissions = new String[]{Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE};
        ActivityCompat.requestPermissions(this, permissions, 200);
    }
    private boolean checkPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) !=
                PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) !=
                PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) !=
                PackageManager.PERMISSION_GRANTED) {
            return false;
        }
        return true;
    }
    private String saveImage(Bitmap myBitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File wallpaperDirectory = new File(
                (Environment.getExternalStorageDirectory()).toString());
        if (!wallpaperDirectory.exists())
        {
            wallpaperDirectory.mkdirs();
        }
        try
        {
            File f = new File(wallpaperDirectory, (Calendar.getInstance().getTimeInMillis() + ".jpg"));
            f.createNewFile();
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
            String [] fPath = new String[]{f.getPath()};
            String [] jPath = new String[]{"image/jpeg"};
            MediaScannerConnection.scanFile(this,
                    fPath,
                    jPath, null);
            fo.close();
            return f.getAbsolutePath();
        }
        catch (IOException e1) {
            e1.printStackTrace();
        }
        return "";
    }
}
