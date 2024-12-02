package com.example.sscapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ContactFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contact, container, false);

        Button emailButton = view.findViewById(R.id.emailButton);
        Button fbButton = view.findViewById(R.id.fbButton);

        emailButton.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_SENDTO);
            intent.setData(Uri.parse("sscalangilan@g.batstate-u.edu.ph"));
            startActivity(intent);
        });

        fbButton.setOnClickListener(v -> {
            String fbPageId = "sscalangilan"; // Replace with actual Facebook page
            try {
                // Try to open Facebook app
                Uri uri = Uri.parse("https://www.facebook.com/" + fbPageId);
                startActivity(new Intent(Intent.ACTION_VIEW, uri));
            } catch (Exception e) {
                // If Facebook app is not available, open in browser
                Uri uri = Uri.parse("https://www.facebook.com/" + fbPageId);
                startActivity(new Intent(Intent.ACTION_VIEW, uri));
            }
        });

        return view;
    }
}