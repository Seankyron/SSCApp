package com.example.sscapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.sscapp.R;
import com.example.sscapp.adapters.ItemAdapter;
import com.example.sscapp.models.Item;
import java.util.ArrayList;
import java.util.List;

public class LostItemsFragment extends Fragment {
    private RecyclerView recyclerView;
    private ItemAdapter adapter;
    private List<Item> items;
    private List<Item> filteredItems;
    private SearchView searchView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lost_items, container, false);


        setupRecyclerView(view);
        setupSearchView(view);
        loadPlaceholderData();

        return view;
    }

    private void setupRecyclerView(View view) {
        recyclerView = view.findViewById(R.id.lostItemsRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        items = new ArrayList<>();
        filteredItems = new ArrayList<>();
        adapter = new ItemAdapter(getContext(), filteredItems);

        adapter.setOnItemClickListener((item, position) -> {
            Toast.makeText(getContext(),
                    "Contact SSC office about: " + item.getName(),
                    Toast.LENGTH_SHORT).show();
        });

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

