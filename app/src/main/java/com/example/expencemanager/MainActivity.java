package com.example.expencemanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.os.Bundle;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    LoginFragment fragLog;
    RegisterFragment fragReg;
    @Override
    public void onBackPressed() {
    }
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FragmentManager mFragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        fragLog = new LoginFragment();
        fragmentTransaction.add(R.id.viewPager, fragLog).commit();

//        @SuppressLint("WrongViewCast") ViewPager viewPager = findViewById(R.id.viewPager);
//
//        AuthenticationPagerAdapter pagerAdapter = new AuthenticationPagerAdapter(getSupportFragmentManager());
//        pagerAdapter.addFragmet(new LoginFragment());
//        pagerAdapter.addFragmet(new RegisterFragment());
//        viewPager.setAdapter(pagerAdapter);
    }
    public void loginToSignup(){
        fragReg= new RegisterFragment();
        FragmentManager mFragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.viewPager, fragReg).commit();

    }
}