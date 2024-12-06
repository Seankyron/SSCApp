package com.example.sscapp.admin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.viewpager2.widget.ViewPager2;

import com.example.sscapp.R;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.example.sscapp.adapters.AdminStorePagerAdapter;

public class AdminStoreFragment extends Fragment implements AdminCatalogFragment.OnAddProductListener {

    private ViewPager2 viewPager;
    private TabLayout tabLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.admin_fragment_store, container, false);

        viewPager = view.findViewById(R.id.view_pager);
        tabLayout = view.findViewById(R.id.tab_layout);

        setupViewPager();

        return view;
    }

    @Override
    public void onAddProduct() {
        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
        navController.navigate(R.id.action_adminStoreFragment_to_adminAddProductFragment);
    }

    private void setupViewPager() {
        AdminStorePagerAdapter pagerAdapter = new AdminStorePagerAdapter(this);
        viewPager.setAdapter(pagerAdapter);

        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> {
                    switch (position) {
                        case 0:
                            tab.setText("Catalog");
                            break;
                        case 1:
                            tab.setText("Orders");
                            break;
                    }
                }
        ).attach();
    }
}