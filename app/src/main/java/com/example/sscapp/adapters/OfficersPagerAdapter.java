package com.example.sscapp.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.sscapp.ContactFragment;
import com.example.sscapp.CurrentOfficersFragment;
import com.example.sscapp.OrgStructureFragment;

public class OfficersPagerAdapter extends FragmentStateAdapter {
    public OfficersPagerAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new CurrentOfficersFragment();
            case 1:
                return new OrgStructureFragment();
            case 2:
                return new ContactFragment();
            default:
                return new CurrentOfficersFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}