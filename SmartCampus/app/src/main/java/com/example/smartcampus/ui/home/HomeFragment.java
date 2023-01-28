package com.example.smartcampus.ui.home;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.slidingpanelayout.widget.SlidingPaneLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.ImageView;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.smartcampus.R;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.material.slider.Slider;

import java.util.ArrayList;


public class HomeFragment extends Fragment  {
    private ImageSlider imageSlider;
    private ImageView map;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_home, container, false);

        imageSlider = view.findViewById(R.id.image_slider);
        ArrayList<SlideModel> slideModels = new ArrayList<>();
        slideModels.add
                (new SlideModel("https://firebasestorage.googleapis.com/v0/b/smartcampus-efff3.appspot.com/o/gallery%2F%5BB%4074e4427jpg?alt=media&token=863da63d-705c-4bf3-b5de-eba6c44443c4",ScaleTypes.FIT));
        slideModels.add
                (new SlideModel("https://firebasestorage.googleapis.com/v0/b/smartcampus-efff3.appspot.com/o/gallery%2F%5BB%40df8f4dfjpg?alt=media&token=d212ef96-cc81-42aa-8ad5-f206d840a7d6",ScaleTypes.FIT));
        slideModels.add
                (new SlideModel("https://firebasestorage.googleapis.com/v0/b/smartcampus-efff3.appspot.com/o/gallery%2F%5BB%40bc4a9d8jpg?alt=media&token=b36909e6-6ba5-40b2-9a4a-09a3bab0d863",ScaleTypes.FIT));

        imageSlider.setImageList(slideModels,ScaleTypes.FIT);

        map = view.findViewById(R.id.map);
        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMap();
            }
        });
        return  view;
    }

    private void openMap() {
        Uri uri = Uri.parse("geo:0 0?q=Daffodil International University");
        Intent intent = new Intent(Intent.ACTION_VIEW,uri);
        intent.setPackage("com.google.android.apps.maps");
        startActivity(intent);


    }

}