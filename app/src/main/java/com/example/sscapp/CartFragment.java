package com.example.sscapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.sscapp.adapters.CartAdapter;
import com.example.sscapp.models.CartItem;
import com.example.sscapp.models.Order;
import com.example.sscapp.models.GroupedCartItem;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class CartFragment extends Fragment {

    private RecyclerView cartRecyclerView;
    private TextView totalPriceTextView;
    private Button checkoutButton;
    private List<CartItem> cartItems;
    private CartAdapter cartAdapter;
    private View emptyCartView;
    private TextInputEditText referenceNumberEditText;
    private MaterialButton uploadReceiptButton;
    private MaterialButton submitButton;
    private ImageView receiptImageView;
    private Uri receiptImageUri;
    private TextView statusTextView;
    private boolean isSubmitted = false;
    private LinearLayout submitPayment;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container, false);

        emptyCartView = view.findViewById(R.id.empty_cart_view);
        cartRecyclerView = view.findViewById(R.id.cart_recycler_view);
        totalPriceTextView = view.findViewById(R.id.total_price_text_view);
        submitPayment = view.findViewById(R.id.submit_payment);

        referenceNumberEditText = view.findViewById(R.id.referenceNumberEditText);
        uploadReceiptButton = view.findViewById(R.id.uploadReceiptButton);
        submitButton = view.findViewById(R.id.submitButton);
        receiptImageView = view.findViewById(R.id.receiptImageView);
        statusTextView = view.findViewById(R.id.statusTextView);

        setupCartItems();
        setupRecyclerView();
        updateTotalPrice();

        uploadReceiptButton.setOnClickListener(v -> openImagePicker());
        submitButton.setOnClickListener(v -> submitPayment());

        return view;
    }

    private void setupCartItems() {
        cartItems = CartManager.getInstance().getCartItems();
        updateCartView();
    }

    private void setupRecyclerView() {
        cartAdapter = new CartAdapter(cartItems, this::updateTotalPrice);
        cartRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        cartRecyclerView.setAdapter(cartAdapter);
    }

    private void updateTotalPrice() {
        double total = CartManager.getInstance().getTotalPrice();
        totalPriceTextView.setText(String.format("Total: ₱%.2f", total));
    }

    private void updateCartView() {
        if (cartItems.isEmpty()) {
            emptyCartView.setVisibility(View.VISIBLE);
            cartRecyclerView.setVisibility(View.GONE);
            totalPriceTextView.setVisibility(View.GONE);
            submitPayment.setVisibility(View.GONE);
        } else {
            emptyCartView.setVisibility(View.GONE);
            cartRecyclerView.setVisibility(View.VISIBLE);
            totalPriceTextView.setVisibility(View.VISIBLE);
            submitPayment.setVisibility(View.VISIBLE);
        }
    }

    private void openImagePicker() {
        if (!isSubmitted) {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, 1);
        } else {
            Toast.makeText(getContext(), "Payment already submitted. Cannot modify.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == getActivity().RESULT_OK && data != null) {
            receiptImageUri = data.getData();
            receiptImageView.setImageURI(receiptImageUri);
        }
    }

    private void submitPayment() {
        if (isSubmitted) {
            Toast.makeText(getContext(), "Payment already submitted. Cannot submit again.", Toast.LENGTH_SHORT).show();
            return;
        }

        String referenceNumber = referenceNumberEditText.getText().toString();
        if (referenceNumber.isEmpty() || receiptImageUri == null) {
            Toast.makeText(getContext(), "Please fill all fields and upload a receipt", Toast.LENGTH_SHORT).show();
            return;
        }

        isSubmitted = true;
        submitButton.setEnabled(false);
        uploadReceiptButton.setEnabled(false);
        referenceNumberEditText.setEnabled(false);

        performCheckout();

        Toast.makeText(getContext(), "Payment submitted for verification", Toast.LENGTH_LONG).show();
    }

    private void performCheckout() {
        HashMap<String, GroupedCartItem> groupedItems = new HashMap<>();
        for (CartItem item : cartItems) {
            String key = item.getName();
            if (!groupedItems.containsKey(key)) {
                groupedItems.put(key, new GroupedCartItem(item.getName(), item.getPrice(), item.getImageResId()));
            }
            groupedItems.get(key).addSize(item.getSize(), item.getQuantity());
        }

        // Create a new order
        int orderId = OrderManager.getInstance().getNextOrderId();
        String strOrderId = "Order " + String.format("%04d", orderId);
        String date = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).format(new Date());
        String status = "Processing";
        double totalAmount = CartManager.getInstance().getTotalPrice();

        List<GroupedCartItem> orderItems = new ArrayList<>(groupedItems.values());

        Order newOrder = new Order(strOrderId, date, status, totalAmount, orderItems);
        // Add the order to OrderManager
        OrderManager.getInstance().addOrder(newOrder);

        // Clear the cart
        CartManager.getInstance().clearCart();
        setupCartItems();
        updateCartView();

        // Navigate to the StoreFragment
        Navigation.findNavController(requireView()).navigate(R.id.action_cartFragment_to_storeFragment);
    }

    @Override
    public void onResume() {
        super.onResume();
        setupCartItems();
        cartAdapter.notifyDataSetChanged();
        updateTotalPrice();
        updateCartView();
    }
}

