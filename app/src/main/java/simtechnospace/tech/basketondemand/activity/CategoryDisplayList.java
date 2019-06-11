package simtechnospace.tech.basketondemand.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.Volley;

import simtechnospace.tech.basketondemand.R;

public class CategoryDisplayList extends AppCompatActivity {

    ImageView mStatic_image;
    TextView  mCategory_name;
    RecyclerView mCategory_displaylist;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories_displaylist);

        mStatic_image = findViewById(R.id.imgstatic);
        mCategory_name = findViewById(R.id.categoryname);
        mCategory_displaylist = findViewById(R.id.category_itemlist_recyclerview);





    }
}