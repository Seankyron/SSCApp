package com.example.sscapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sscapp.adapters.OfficerAdapter;
import com.example.sscapp.models.Officer;

import java.util.ArrayList;
import java.util.List;

public class CurrentOfficersFragment extends Fragment {
    private TextView presidentNameText;
    private TextView presidentPositionText;
    private TextView presidentDetailsText;
    private TextView adviserNameText;
    private TextView adviserPositionText;
    private TextView adviserDetailsText;
    private RecyclerView officersRecyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_current_officers, container, false);

        // President TextViews
        presidentNameText = view.findViewById(R.id.presidentNameText);
        presidentPositionText = view.findViewById(R.id.presidentPositionText);
        presidentDetailsText = view.findViewById(R.id.presidentDetailsText);

        // Adviser TextViews
        adviserNameText = view.findViewById(R.id.adviserNameText);
        adviserPositionText = view.findViewById(R.id.adviserPositionText);
        adviserDetailsText = view.findViewById(R.id.adviserDetailsText);

        officersRecyclerView = view.findViewById(R.id.officersRecyclerView);

        // Set President details
        presidentNameText.setText("Franceise Bhien C. Almirol");
        presidentPositionText.setText("SSC President");
        presidentDetailsText.setText("4th Year / BS Chemical Engineering");

        // Set Adviser details
        adviserNameText.setText("Jefferson C. Canada");
        adviserPositionText.setText("SSC Adviser");
        adviserDetailsText.setText("Head, Office of Student Organization");

        // Set up RecyclerView for other officers
        List<Officer> officers = getOfficersList();
        OfficerAdapter adapter = new OfficerAdapter(officers);
        officersRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        officersRecyclerView.setAdapter(adapter);

        return view;
    }

    private List<Officer> getOfficersList() {
        List<Officer> officers = new ArrayList<>();

        // Add officers from the PDF file
        officers.add(new Officer("Lhanz Andrei R. Balmes", "Executive Vice President", "4th Year / BS Architecture"));
        officers.add(new Officer("John Philip A. Gulmatico", "VP Student Development (Alangilan)", "4th Year / BS Transportation Engineering"));
        officers.add(new Officer("Shanel Ann D. Manimtim", "VP Student Development (Balayan)", "4th Year / BS Information Technology"));
        officers.add(new Officer("Mcdoel Andrei M. Famini", "VP Student Development (Mabini)", "3rd Year / BS Information Technology"));
        officers.add(new Officer("Nathanlie Joseph D. De Claro", "VP Student Development (Lobo)", "4th Year / BS Agriculture"));
        officers.add(new Officer("Mhekyla Ramirez", "Secretary General", "2nd Year / BS Biomedical Engineering"));
        officers.add(new Officer("Janahritzel M. Calanog", "Deputy Secretary General", "3rd Year / BS Civil Engineering"));
        officers.add(new Officer("Ma. Therese Lynne N. Arellano", "Finance Officer", "4th Year / BS Sanitary Engineering"));
        officers.add(new Officer("Hazel E. Torres", "Deputy Finance Officer", "3rd Year / BS Electrical Engineering"));
        officers.add(new Officer("Mckenzie U. Ongcal", "Auditor", "2nd Year / BS Industrial Engineering"));
        officers.add(new Officer("Allan Jhasper P. Arago", "Public Relations Officer", "4th Year / BS Mechatronics Engineering"));
        officers.add(new Officer("Kristi Nicole A. Andea", "Public Relations Officer", "3rd Year / BS Petroleum Engineering"));
        officers.add(new Officer("Iemerie Jom C. Manguit", "Business Manager", "3rd Year / BS Computer Science"));
        officers.add(new Officer("Jamir G. Ariola", "Business Manager", "4th Year / BS Electrical Engineering"));
        officers.add(new Officer("Victor Manuel S. Dalisay", "CICS Alangilan Governor", "4th Year / BS Computer Science"));
        officers.add(new Officer("Dorothy Marie L. Servan", "CAF Governor", "3rd Year / BS Agriculture"));
        officers.add(new Officer("Cristel Nicole V. Bruel", "CoE III Governor", "4th Year / BS Electronics Engineering"));
        officers.add(new Officer("Christian Ed. M. Bathan", "CoE I Governor", "3rd Year / BS Mechanical Engineering"));
        officers.add(new Officer("Nathaniel M. Regodon", "CoE II Governor", "4th Year / BS Sanitary Engineering"));
        officers.add(new Officer("Frank Joe J. Matusalem", "Chairperson, Culture and Arts", "3rd Year / BFAD Visual Communication"));
        officers.add(new Officer("Charles Aaron Untalan", "Chairperson, Branding and Creative Design", "4th Year / BS Architecture"));

        // Add more officers as necessary
        return officers;
    }
}
