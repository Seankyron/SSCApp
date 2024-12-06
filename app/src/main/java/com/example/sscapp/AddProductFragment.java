package com.example.sscapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class AddProductFragment extends Fragment {

    private TextInputEditText productNameEditText;
    private TextInputEditText productPriceEditText;
    private AutoCompleteTextView productStatusDropdown;
    private TextInputEditText productDescriptionEditText;
    private ImageView productImageView;
    private MaterialButton uploadImageButton;
    private MaterialButton addProductButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.admin_add_product, container, false);

        productNameEditText = view.findViewById(R.id.product_name_edit_text);
        productPriceEditText = view.findViewById(R.id.product_price_edit_text);
        productStatusDropdown = view.findViewById(R.id.product_status_dropdown);
        productDescriptionEditText = view.findViewById(R.id.product_description_edit_text);
        productImageView = view.findViewById(R.id.receiptImageView);
        uploadImageButton = view.findViewById(R.id.uploadReceiptButton);
        addProductButton = view.findViewById(R.id.add_product_button);

        setupStatusDropdown();
        setupButtons();

        return view;
    }

    private void setupStatusDropdown() {
        String[] statusOptions = {"Available", "Limited"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_dropdown_item_1line, statusOptions);
        productStatusDropdown.setAdapter(adapter);
    }

    private void setupButtons() {
        uploadImageButton.setOnClickListener(v -> {
            // Implement image upload functionality
        });

        addProductButton.setOnClickListener(v -> {
            // Implement add product functionality
        });
    }
}