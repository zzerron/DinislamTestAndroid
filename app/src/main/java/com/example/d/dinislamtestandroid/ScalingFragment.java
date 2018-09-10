package com.example.d.dinislamtestandroid;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.d.dinislamtestandroid.scaling.ScalingActivity;


public class ScalingFragment extends Fragment {
    private View rootView;
    private static final int SELECT_PICTURE = 1;
    private static final int CAMERA_CAPTURE = 2;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_scaling, container, false);

        Button onClickGalley = (Button) rootView.findViewById(R.id.inGallery);
        onClickGalley.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                caller("galery");
            }
        });

        Button onClickCamera = (Button) rootView.findViewById(R.id.inCamera);
        onClickCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                caller("camera");
            }
        });

        return rootView;
    }

//    В зависимость от кнопки, вызывается или галерея или камера
    private void caller(String what){
        if (what.equals("galery")) {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            startActivityForResult(intent, SELECT_PICTURE);
        }
        if (what.equals("camera")){
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, CAMERA_CAPTURE);
        }

    }

//    Результатом работы является вызов новой активити, куда передается: в случаи галереи URI.
//    в случаи камеры, bundle
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == getActivity().RESULT_OK){
            if (requestCode == SELECT_PICTURE){
                Intent intent = new Intent(rootView.getContext(), ScalingActivity.class);
                String uri = data.getData().toString();
                intent.putExtra("uri", uri);
                intent.putExtra("what", "galery");
                startActivity(intent);
            }
            if (requestCode == CAMERA_CAPTURE){
                Intent intent = new Intent(rootView.getContext(), ScalingActivity.class);
                Bundle bundle = data.getExtras();
                intent.putExtras(data);
                intent.putExtra("bundle", bundle);
                intent.putExtra("what", "camera");
                startActivity(intent);
            }
        }
    }

}
