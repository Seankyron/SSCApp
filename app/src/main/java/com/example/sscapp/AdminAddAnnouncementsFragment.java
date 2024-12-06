package com.example.sscapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import androidx.fragment.app.Fragment;
import com.example.sscapp.models.Announcement;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.android.material.textfield.TextInputEditText;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AdminAddAnnouncementsFragment extends Fragment {

    private TextInputEditText titleEditText, descriptionEditText;
    private AutoCompleteTextView typeDropdown, categoryDropdown, authorDropdown;
    private MaterialButton uploadImageButton, submitButton;
    private SwitchMaterial pinnedSwitch;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.admin_fragment_add_announcement, container, false);

        titleEditText = view.findViewById(R.id.titleEditText);
        descriptionEditText = view.findViewById(R.id.descriptionEditText);
        typeDropdown = view.findViewById(R.id.typeDropdown);
        categoryDropdown = view.findViewById(R.id.categoryDropdown);
        authorDropdown = view.findViewById(R.id.authorDropdown);
        // uploadImageButton = view.findViewById(R.id.uploadImageButton);
        pinnedSwitch = view.findViewById(R.id.pinnedSwitch);
        submitButton = view.findViewById(R.id.submitButton);

        setupDropdowns();

        return view;
    }

    private void setupDropdowns() {
        String[] types = {"Important", "Urgent", "Normal"};
        ArrayAdapter<String> typeAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_dropdown_item_1line, types);
        typeDropdown.setAdapter(typeAdapter);

        String[] categories = {"Event Updates", "Academic Achievements", "Weather Advisory", "Campus News"};
        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_dropdown_item_1line, categories);
        categoryDropdown.setAdapter(categoryAdapter);

        String[] authors = {"Student Affairs Office", "Office of the Dean", "Office of the President", "College of Engineering"};
        ArrayAdapter<String> authorAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_dropdown_item_1line, authors);
        authorDropdown.setAdapter(authorAdapter);
    }

    private boolean validateInputs() {
        // TODO: Implement input validation
        return true;
    }

    private void createAnnouncement() {
        String title = titleEditText.getText().toString();
        String description = descriptionEditText.getText().toString();
        String type = typeDropdown.getText().toString();
        String category = categoryDropdown.getText().toString();
        String author = authorDropdown.getText().toString();
        boolean isPinned = pinnedSwitch.isChecked();
        String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

        // TODO: Get the last ID from the existing announcements and increment it
        int newId = getLastAnnouncementId() + 1;

        Announcement newAnnouncement = new Announcement(
                newId, title, description, type, category, author,
                "https://example.com/placeholder.jpg", // TODO: Replace with actual uploaded image URL
                isPinned, date
        );

        // TODO: Add the new announcement to the list of announcements
        addAnnouncementToList(newAnnouncement);

        // TODO: Navigate back to the AdminUpdatesFragment
        // getParentFragmentManager().popBackStack();
    }

    private int getLastAnnouncementId() {
        List<Announcement> announcements = UpdatesFragment.getStaticAnnouncements();
        return announcements.isEmpty() ? 0 : announcements.get(announcements.size() - 1).getId();
    }

    private void addAnnouncementToList(Announcement announcement) {
        List<Announcement> announcements = UpdatesFragment.getStaticAnnouncements();
        announcements.add(announcement);
        // TODO: Update the static announcements list in UpdatesFragment
        // UpdatesFragment.setStaticAnnouncements(announcements);
    }
}

