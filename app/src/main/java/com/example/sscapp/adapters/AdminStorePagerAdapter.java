package com.example.sscapp.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.sscapp.AdminCatalogFragment;
import com.example.sscapp.AdminOrdersFragment;
import com.example.sscapp.CatalogFragment;
import com.example.sscapp.OrdersFragment;

public class AdminStorePagerAdapter extends FragmentStateAdapter {

    public AdminStorePagerAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new AdminCatalogFragment();
            case 1:
                return new AdminOrdersFragment();
            default:
                throw new IllegalStateException("Unexpected position " + position);
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}

