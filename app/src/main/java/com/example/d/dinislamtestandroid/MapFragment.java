package com.example.d.dinislamtestandroid;


import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yandex.mapkit.Animation;
import com.yandex.mapkit.MapKitFactory;
import com.yandex.mapkit.geometry.Point;
import com.yandex.mapkit.location.LocationManagerUtils;
import com.yandex.mapkit.location.LocationStatus;
import com.yandex.mapkit.location.internal.LocationManagerBinding;
import com.yandex.mapkit.map.CameraListener;
import com.yandex.mapkit.map.CameraPosition;
import com.yandex.mapkit.map.CameraUpdateSource;
import com.yandex.mapkit.map.Map;
import com.yandex.mapkit.mapview.MapView;



public class MapFragment extends Fragment {
    private MapView mapView;
    private LocationManager locationManager;
    private TextView textGPS;
    private Point point;
    private Double lattd;
    private Double longtd;
    private Float zoom;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        Если ничего не сохраненно, выставим координаты москвы и зум в 11. Иначе покажем  последнию выбранную точку на карте
        if (savedInstanceState != null){
            lattd = savedInstanceState.getDouble("lattd");
            longtd = savedInstanceState.getDouble("longtd");
            zoom = savedInstanceState.getFloat("zoom");
        }
        else {
            lattd = 55.751574;
            longtd = 37.573856;
            zoom = 11.0f;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

//        Сначала указываем ключь, потом вью, иначе не работает
        MapKitFactory.setApiKey("5f9923e7-7386-488e-88de-a2ddbb1b8781");

        View rootView = inflater.inflate(R.layout.fragment_map, container, false);

        MapKitFactory.initialize(rootView.getContext());

        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);


        mapView = (MapView) rootView.findViewById(R.id.map_yandex);
        mapView.getMap().move(
                new CameraPosition(new Point(lattd, longtd), 11.0f, 0.0f, 0.0f),
                new Animation(Animation.Type.SMOOTH, 0),
                null);
        mapView.getMap().addCameraListener(cameraListener);


        textGPS = (TextView) rootView.findViewById(R.id.gps_location);
        textGPS.setText(String.format(getResources().getString(R.string.coordinates), lattd, longtd));
        return rootView;


    }

    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
        MapKitFactory.getInstance().onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();
        MapKitFactory.getInstance().onStop();
    }


    @Override
    public void onResume() {
        super.onResume();
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                1000 * 2, 10, locationListener);
//        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
//                1000 * 2, 10, locationListener);
    }

//    Сохраним текущее местоположение камеры на карте
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putDouble("lattd", lattd);
        outState.putDouble("longtd", longtd);
        outState.putFloat("zoom", zoom);
    }

    @Override
    public void onPause() {
        super.onPause();
        locationManager.removeUpdates(locationListener);
    }

//    Запишем текущее положение камеры на карте
    private final CameraListener cameraListener = new CameraListener() {
        @Override
        public void onCameraPositionChanged(Map map, CameraPosition cameraPosition, CameraUpdateSource cameraUpdateSource, boolean b) {
            lattd = cameraPosition.getTarget().getLatitude();
            longtd = cameraPosition.getTarget().getLongitude();
            zoom = cameraPosition.getZoom();
            textGPS.setText(String.format(getResources().getString(R.string.coordinates), lattd, longtd));
        }
    };

// Устанавливаем слушателя который определяет методы при выключение, включении, и изменении состояния GPS
    private LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            showLocation2(location);
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {
            showLocation2(locationManager.getLastKnownLocation(s));
        }

        @Override
        public void onProviderEnabled(String s) {
            showLocation2(locationManager.getLastKnownLocation(s));
        }

        @Override
        public void onProviderDisabled(String s) {
            textGPS.setText(getResources().getString(R.string.gpsstatus));
        }
    };

    private void showLocation2(Location location){
        if (location == null)
            return;
        textGPS.setText(formatLocation(location));
        mapView.getMap().move(
                new CameraPosition(new Point(location.getLatitude(), location.getLongitude()), 11.0f, 0.0f, 0.0f),
                new Animation(Animation.Type.SMOOTH, 0),
                null);
    }

    private String formatLocation(Location location){
        if (location == null)
            return "";
        return String.format(getResources().getString(R.string.coordinates), location.getLatitude(), location.getLongitude());
    }

}
