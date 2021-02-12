package com.nathanielbennett.tweeter.view.admission;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.nathanielbennett.tweeter.R;


public class AdmissionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admission);


        // Set up fragments associated with each tab
        AdmissionPagerAdapter tabPagerAdapter = new AdmissionPagerAdapter(getSupportFragmentManager(), this);
        ViewPager viewPager = findViewById(R.id.admission_tab_pager);
        viewPager.setAdapter(tabPagerAdapter);
        TabLayout tabs = findViewById(R.id.admission_tabs);
        tabs.setupWithViewPager(viewPager);
    }
}
