package com.example.sscapp.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.sscapp.fragments.AdminFoundItemsFragment;
import com.example.sscapp.fragments.AdminLostItemsFragment;

public class AdminLostAndFoundPagerAdapter extends FragmentStateAdapter {

    private static final int TOTAL_TABS = 2;
    private static final int LOST_ITEMS_TAB = 0;
    private static final int FOUND_ITEMS_TAB = 1;

    public AdminLostAndFoundPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case LOST_ITEMS_TAB:
                return new AdminLostItemsFragment();
            case FOUND_ITEMS_TAB:
                return new AdminFoundItemsFragment();
            default:
                throw new IllegalStateException("Invalid position: " + position);
        }
    }

    @Override
    public int getItemCount() {
        return TOTAL_TABS;
    }

    public static String getTabTitle(int position) {
        switch (position) {
            case LOST_ITEMS_TAB:
                return "Lost Items";
            case FOUND_ITEMS_TAB:
                return "Found Items";
            default:
                return "";
        }
    }
}