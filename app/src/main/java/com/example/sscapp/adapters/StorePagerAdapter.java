package com.example.sscapp.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.sscapp.CartFragment;
import com.example.sscapp.CatalogFragment;
import com.example.sscapp.OrdersFragment;
import com.example.sscapp.StoreFragment;

public class StorePagerAdapter extends FragmentStateAdapter {

    public StorePagerAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new CatalogFragment();
            case 1:
                return new OrdersFragment();
            default:
                throw new IllegalStateException("Unexpected position " + position);
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}

