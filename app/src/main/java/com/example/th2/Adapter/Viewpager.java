package com.example.th2.Adapter;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.th2.Fragment.FragmentDanhsach;
import com.example.th2.Fragment.FragmentThongtin;
import com.example.th2.Fragment.FragmentTimkiem;

public class Viewpager extends FragmentPagerAdapter {
    private int pagenumber;
    public Viewpager(@NonNull FragmentManager fm, int behavior) {
        super(fm,behavior);
        this.pagenumber = behavior;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                FragmentDanhsach fragmentDanhsach = new FragmentDanhsach();
                return fragmentDanhsach;
            case 1:
                FragmentThongtin fragmentThongtin = new FragmentThongtin();
                return fragmentThongtin;
            case 2:
                FragmentTimkiem fragmentTimkiem = new FragmentTimkiem();
                return fragmentTimkiem;

        }
        return null;
    }

    @Override
    public int getCount() {
        return pagenumber;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return "Danh sách";
            case 1:
                return "Thông tin";
            case 2:
                return "Tìm kiếm và thống kê";

        }
        return null;
    }
}
