package simtechnospace.tech.basketondemand.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;

import simtechnospace.tech.basketondemand.R;
import simtechnospace.tech.basketondemand.pojoclass.URLClass;

public class MainActivity extends AppCompatActivity implements BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener {

    SliderLayout mImageSliderLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mImageSliderLayout = findViewById(R.id.slider);

        final String banner_image_url = URLClass.sliderImageUrl;



        final RequestQueue requestQueue = Volley.newRequestQueue( this );
        final RequestQueue requestQueueForBannerImages = Volley.newRequestQueue(this);






    }

    @Override
    public void onSliderClick(BaseSliderView slider) {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
