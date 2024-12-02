package com.example.sscapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.sscapp.adapters.OfficersPagerAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class OfficersFragment extends Fragment {
    private ViewPager2 viewPager;
    private TabLayout tabLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_officers, container, false);

        viewPager = view.findViewById(R.id.officersViewPager);
        tabLayout = view.findViewById(R.id.officersTabs);

        OfficersPagerAdapter adapter = new OfficersPagerAdapter(this);
        viewPager.setAdapter(adapter);

        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            switch (position) {
                case 0:
                    tab.setText("Current Officers");
                    break;
                case 1:
                    tab.setText("Org Structure");
                    break;
                case 2:
                    tab.setText("Contact");
                    break;
            }
        }).attach();

        return view;
    }
}