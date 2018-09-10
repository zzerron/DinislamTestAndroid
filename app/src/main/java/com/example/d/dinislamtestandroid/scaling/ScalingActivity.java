package com.example.d.dinislamtestandroid.scaling;


import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ZoomControls;

import com.example.d.dinislamtestandroid.R;

import java.io.IOException;

import uk.co.senab.photoview.PhotoViewAttacher;

public class ScalingActivity extends AppCompatActivity {

    private ImageView imageView;
    private PhotoViewAttacher photoViewAttacher;
    private ZoomControls zoomControls;
    private float scaleWidth = 1;
    private float scaleHeight = 1;
    private Bitmap bitmap;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.gallery_activity);
        getSupportActionBar().hide();

        imageView = (ImageView) findViewById(R.id.imageScaling);


        if (getIntent().getStringExtra("what").equals("galery")) {
//            Используем стороннию библеотеку для вывода картинки по Uri
            Uri uri = Uri.parse(getIntent().getStringExtra("uri"));
//            Picasso.get().load(uri).into(imageView);

//            Создаем bitmap по uri, для возможности увеличения или уменьшения с помощью zoom
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
            } catch (IOException e) {
                e.printStackTrace();
            }
            imageView.setImageBitmap(bitmap);
        }
        else {
            bitmap = (Bitmap) getIntent().getBundleExtra("bundle").get("data");
            imageView.setImageBitmap(bitmap);
        }


//       Используем стороннию библиотеку PhotoView для увеличения изображения через мультитач
        photoViewAttacher = new PhotoViewAttacher(imageView);
        photoViewAttacher.update();

//      Задаем Зумконтролер для увеличения изображения через кнопки зума
        zoomControls = (ZoomControls) findViewById(R.id.zoomControls);
        zoomControls.setOnZoomInClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                float scale = photoViewAttacher.getScale();
                photoViewAttacher.setScale(scale + 0.5f);
            }
        });

        zoomControls.setOnZoomOutClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                float scale = photoViewAttacher.getScale();
                photoViewAttacher.setScale(scale - 0.2f);
            }
        });
    }
}
