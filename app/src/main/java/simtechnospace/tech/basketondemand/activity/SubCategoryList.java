package simtechnospace.tech.basketondemand.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

import simtechnospace.tech.basketondemand.R;

public class SubCategoryList extends AppCompatActivity {

    RecyclerView mRecyclerViewSubCategoryList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.subcategory_list_activity);

        mRecyclerViewSubCategoryList = findViewById(R.id.recyclerViewSubCategoryList);




    }
}
