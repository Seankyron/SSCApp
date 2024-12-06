package com.example.sscapp.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.sscapp.R;
import com.example.sscapp.adapters.AdminItemAdapter;
import com.example.sscapp.models.Item;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;
import java.util.List;

public class AdminLostItemsFragment extends Fragment {
    private RecyclerView recyclerView;
    private AdminItemAdapter adapter;
    private List<Item> items;
    private List<Item> filteredItems;
    private SearchView searchView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_admin_lost_items, container, false);

        setupRecyclerView(view);
        setupSearchView(view);
        setupFab(view);
        loadPlaceholderData();

        return view;
    }

    private void setupRecyclerView(View view) {
        recyclerView = view.findViewById(R.id.adminLostItemsRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        items = new ArrayList<>();
        filteredItems = new ArrayList<>();
        adapter = new AdminItemAdapter(getContext(), filteredItems);
        recyclerView.setAdapter(adapter);
    }

    private void setupSearchView(View view) {
        searchView = view.findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                filterItems(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterItems(newText);
                return false;
            }
        });
    }

    private void setupFab(View view) {
        FloatingActionButton fab = view.findViewById(R.id.fabAddLostItem);
        fab.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), AddLostItemActivity.class);
            startActivity(intent);
        });
    }

    private void filterItems(String query) {
        filteredItems.clear();
        for (Item item : items) {
            if (item.getName().toLowerCase().contains(query.toLowerCase())) {
                filteredItems.add(item);
            }
        }
        adapter.notifyDataSetChanged();
    }

    private void loadPlaceholderData() {
        items.clear();
        items.add(new Item("Blue Backpack", "Library - 2nd Floor", "Lost on: Apr 20, 2024", R.drawable.image_placeholder));
        items.add(new Item("Water Bottle", "Cafeteria", "Lost on: Apr 19, 2024", R.drawable.image_placeholder));
        items.add(new Item("Calculator", "Room 301", "Lost on: Apr 18, 2024", R.drawable.image_placeholder));
        items.add(new Item("Textbook", "Student Center", "Lost on: Apr 17, 2024", R.drawable.image_placeholder));
        filteredItems.addAll(items);
        adapter.notifyDataSetChanged();
    }
}