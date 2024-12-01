package com.example.sscapp;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.sscapp.models.CartItem;
import com.example.sscapp.models.Product;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.snackbar.Snackbar;

public class ProductDetailsFragment extends Fragment {

    private static final String ARG_PRODUCT = "product";
    private Product product;

    private ImageView productImage;
    private TextView productName;
    private TextView productPrice;
    private TextView productDescription;
    private ChipGroup sizeChipGroup;
    private TextView quantityText;
    private MaterialButton addToCartButton;
    private TextView productStatus;

    public static ProductDetailsFragment newInstance(Product product) {
        ProductDetailsFragment fragment = new ProductDetailsFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PRODUCT, product);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            product = getArguments().getParcelable(ARG_PRODUCT);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product_details, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initViews(view);
        populateProductDetails();
        setupQuantitySelector();
        setupSizeSelection();
        setupAddToCartButton();
    }

    private void initViews(View view) {
        productImage = view.findViewById(R.id.productImage);
        productName = view.findViewById(R.id.productName);
        productPrice = view.findViewById(R.id.productPrice);
        productDescription = view.findViewById(R.id.productDescription);
        productStatus = view.findViewById(R.id.productStatus);
        sizeChipGroup = view.findViewById(R.id.sizeChipGroup);
        quantityText = view.findViewById(R.id.quantityText);
        addToCartButton = view.findViewById(R.id.addToCartButton);
    }

    private void populateProductDetails() {
        if (product != null) {
            productImage.setImageResource(product.getImageResId());
            productName.setText(product.getName());
            productPrice.setText(String.format("₱%.2f", product.getPrice()));
            productDescription.setText(product.getDescription());
            productStatus.setText(product.getStatus());
        } else {
            Log.e("ProductDetailsFragment", "Product is null");
        }
    }

    private void setupQuantitySelector() {
        ImageView decreaseQuantity = requireView().findViewById(R.id.decreaseQuantity);
        ImageView increaseQuantity = requireView().findViewById(R.id.increaseQuantity);

        quantityText.setText("1"); // Set initial quantity

        decreaseQuantity.setOnClickListener(v -> {
            int quantity = Integer.parseInt(quantityText.getText().toString());
            if (quantity > 1) {
                quantityText.setText(String.valueOf(quantity - 1));
            }
        });

        increaseQuantity.setOnClickListener(v -> {
            int quantity = Integer.parseInt(quantityText.getText().toString());
            quantityText.setText(String.valueOf(quantity + 1));
        });
    }

    private void setupSizeSelection() {
        String[] sizes = {"S", "M", "L", "XL"};

        sizeChipGroup.setSingleSelection(true);

        for (String size : sizes) {
            Chip chip = new Chip(requireContext());
            chip.setText(size);
            chip.setCheckable(true);
            sizeChipGroup.addView(chip);
        }
    }

    private void setupAddToCartButton() {
        addToCartButton.setOnClickListener(v -> addToCart());
    }

    private void addToCart() {
        int quantity = Integer.parseInt(quantityText.getText().toString());
        Chip selectedChip = (Chip) sizeChipGroup.findViewById(sizeChipGroup.getCheckedChipId());

        if (selectedChip == null) {
            Snackbar.make(requireView(), "Please select a size", Snackbar.LENGTH_SHORT).show();
            return;
        }

        String size = selectedChip.getText().toString();
        if (product != null) {
            CartItem cartItem = new CartItem(
                    product.getName(),
                    product.getPrice(),
                    quantity,
                    product.getImageResId(),
                    size
            );

            CartManager.getInstance().addToCart(cartItem);
            Log.d("ProductDetailsFragment", "Added to cart: " + cartItem.toString());


            Snackbar.make(requireView(), "Added to cart", Snackbar.LENGTH_SHORT).show();

            Navigation.findNavController(requireView()).navigateUp();
        } else {
            Log.e("ProductDetailsFragment", "Cannot add to cart: Product is null");
        }
    }
}