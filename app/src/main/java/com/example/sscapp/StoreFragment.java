package com.example.sscapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.viewpager2.widget.ViewPager2;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.example.sscapp.adapters.StorePagerAdapter;
import com.example.sscapp.models.Product;

public class StoreFragment extends Fragment implements CatalogFragment.OnProductSelectedListener {

    private ViewPager2 viewPager;
    private TabLayout tabLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_store, container, false);

        viewPager = view.findViewById(R.id.view_pager);
        tabLayout = view.findViewById(R.id.tab_layout);

        setupViewPager();

        return view;
    }

    private void setupViewPager() {
        StorePagerAdapter pagerAdapter = new StorePagerAdapter(this);
        viewPager.setAdapter(pagerAdapter);

        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> {
                    switch (position) {
                        case 0:
                            tab.setText("Catalog");
                            break;
                        case 1:
                            tab.setText("My Orders");
                            break;
                    }
                }
        ).attach();
    }

    @Override
    public void onProductSelected(Product product) {
        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
        Bundle args = new Bundle();
        args.putParcelable("product", product);
        navController.navigate(R.id.action_storeFragment_to_productDetailsFragment, args);
    }
}