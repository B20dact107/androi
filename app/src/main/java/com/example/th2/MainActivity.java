package com.example.th2;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import com.example.th2.Activity.AddBaihat;
import com.example.th2.Adapter.HorizontalFlipTransformation;
import com.example.th2.Adapter.Viewpager;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tabLayout = findViewById(R.id.tablayout);
        viewPager = findViewById(R.id.viewpager);

        SQLiteHelper db = new SQLiteHelper(this);
        long result1 = db.addCasi("Singer 1", "https://duanecosmartcitythuthiem.com/wp-content/uploads/2022/10/avatar-dep-ngau-nam-5.jpg");
        long result2 = db.addCasi("Singer 2", "https://tiemchupanh.com/wp-content/uploads/2023/07/9342fea1a56739ba80054fa7d4cdb91f-683x1024.jpg");
//

        FragmentManager manager = getSupportFragmentManager();
        Viewpager viewpager = new Viewpager(manager,3);
        viewPager.setPageTransformer(true,new HorizontalFlipTransformation());
        viewPager.setAdapter(viewpager);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()){
                    case 0:tabLayout.setTabTextColors(Color.BLACK,getResources().getColor(R.color.colortext));
                        break;
                    case 1:tabLayout.setTabTextColors(Color.BLACK,getResources().getColor(R.color.colortext));
                        break;
                    case 2:tabLayout.setTabTextColors(Color.BLACK,getResources().getColor(R.color.colortext));
                        break;

                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


    }

    // tạo menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }
    //bắt sự kiện cho menu

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemid = item.getItemId();
        if(itemid==R.id.addbaihat){
            Intent intent = new Intent(MainActivity.this, AddBaihat.class);
            startActivity(intent);
        }else if(itemid==R.id.exit) {
            System.exit(0);
        }
        return super.onOptionsItemSelected(item);
    }
}