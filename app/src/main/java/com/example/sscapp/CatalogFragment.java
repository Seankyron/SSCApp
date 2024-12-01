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
import com.example.sscapp.adapters.ProductAdapter;
import com.example.sscapp.models.Product;
import java.util.ArrayList;
import java.util.List;

public class CatalogFragment extends Fragment implements ProductAdapter.OnProductClickListener {

    private RecyclerView productsRecyclerView;
    private List<Product> products;
    private OnProductSelectedListener mListener;

    public interface OnProductSelectedListener {
        void onProductSelected(Product product);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (getParentFragment() instanceof OnProductSelectedListener) {
            mListener = (OnProductSelectedListener) getParentFragment();
        } else {
            throw new RuntimeException(getParentFragment().toString() + " must implement OnProductSelectedListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_catalog, container, false);

        productsRecyclerView = view.findViewById(R.id.products_recycler_view);
        setupProducts();

        return view;
    }

    private void setupProducts() {
        products = new ArrayList<>();
        // Add sample products
        products.add(new Product("T-Shirt", 299.00, "Comfortable cotton t-shirt", R.drawable.product_tshirt, "Low-Stock"));
        // Add more products as needed

        ProductAdapter adapter = new ProductAdapter(products, this);
        productsRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        productsRecyclerView.setAdapter(adapter);
    }

    @Override
    public void onProductClick(Product product) {
        if (mListener != null) {
            mListener.onProductSelected(product);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
}