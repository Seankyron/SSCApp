package com.example.sscapp;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.sscapp.adapters.AdminProductAdapter;
import com.example.sscapp.models.Product;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;
import java.util.List;

public class AdminCatalogFragment extends Fragment implements AdminProductAdapter.OnProductClickListener {

    private RecyclerView productsRecyclerView;
    private List<Product> products;
    private OnAddProductListener mListener;

    public interface OnAddProductListener {
        void onAddProduct();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (getParentFragment() instanceof OnAddProductListener) {
            mListener = (OnAddProductListener) getParentFragment();
        } else {
            throw new RuntimeException(getParentFragment().toString() + " must implement OnAddProductListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.admin_fragment_catalog, container, false);

        productsRecyclerView = view.findViewById(R.id.products_recycler_view);
        setupProducts();

        FloatingActionButton addProductFab = view.findViewById(R.id.addProductFab);
        addProductFab.setOnClickListener(v -> {
            if (mListener != null) {
                mListener.onAddProduct();
            }
        });

        return view;
    }

    private void setupProducts() {
        products = new ArrayList<>();
        // Add sample products
        products.add(new Product("T-Shirt", 299.00, "Comfortable cotton t-shirt", R.drawable.product_tshirt, "Low-Stock"));
        // Add more products as needed

        AdminProductAdapter adapter = new AdminProductAdapter(products, this);
        productsRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        productsRecyclerView.setAdapter(adapter);
    }

    @Override
    public void onProductClick(Product product) {
        // Do nothing, as we don't want to navigate to ProductDetailsFragment
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
}