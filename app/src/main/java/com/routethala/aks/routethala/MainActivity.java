package com.routethala.aks.routethala;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

@EActivity
public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_IMAGE_CAPTURE = 1;

    private BusService service;

    @BindView(R.id.imageView)
    ImageView mImageView;

    @BindView(R.id.spinner)
    Spinner mSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        service = new BusService(new GoogleVisionAPI(getPackageName(), getPackageManager()), this);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_ask_thala)
    public void askThala(View view) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            tagBus(imageBitmap);
            mImageView.setImageBitmap(imageBitmap);
        }
    }

    @Background
    protected void tagBus(Bitmap imageBitmap) {
        service.tagBus(imageBitmap, mSpinner.getSelectedItem().toString());
    }

    @UiThread
    public void drawBusResults(List<BusResult> busResultList) {
        if(busResultList.size() > 0) {
            String message = "Correct bus: ";
            for(int i=0; i < busResultList.size(); i++) {
                message += busResultList.get(i).getRouteNo() + " " + busResultList.get(i).isCorrectBus();
            }
            Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "No bus available", Toast.LENGTH_LONG).show();
        }
    }
}
