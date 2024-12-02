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

import java.util.ArrayList;
import java.util.List;

public class OrgStructureFragment extends Fragment {

    // Data model for Executive Committee
    public static class ExecutiveCommittee {
        String title;
        List<String> departments;

        public ExecutiveCommittee(String title, List<String> departments) {
            this.title = title;
            this.departments = departments;
        }
    }

    // Adapter for Executive Committee
    public class ExecutiveCommitteeAdapter extends RecyclerView.Adapter<ExecutiveCommitteeViewHolder> {
        private final List<ExecutiveCommittee> committees;

        public ExecutiveCommitteeAdapter(List<ExecutiveCommittee> committees) {
            this.committees = committees;
        }

        @NonNull
        @Override
        public ExecutiveCommitteeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_executive_committee, parent, false);
            return new ExecutiveCommitteeViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ExecutiveCommitteeViewHolder holder, int position) {
            holder.bind(committees.get(position));
        }

        @Override
        public int getItemCount() {
            return committees.size();
        }
    }

    // ViewHolder for Executive Committee
    public class ExecutiveCommitteeViewHolder extends RecyclerView.ViewHolder {
        private final TextView committeeTitle;
        private final RecyclerView departmentsRecyclerView;

        public ExecutiveCommitteeViewHolder(@NonNull View itemView) {
            super(itemView);
            committeeTitle = itemView.findViewById(R.id.tv_committee_title);
            departmentsRecyclerView = itemView.findViewById(R.id.rv_departments);
        }

        public void bind(ExecutiveCommittee committee) {
            committeeTitle.setText(committee.title);

            // Set up departments RecyclerView
            departmentsRecyclerView.setLayoutManager(new LinearLayoutManager(itemView.getContext()));
            departmentsRecyclerView.setAdapter(new DepartmentAdapter(committee.departments));
        }
    }

    // Adapter for Departments
    public static class DepartmentAdapter extends RecyclerView.Adapter<DepartmentViewHolder> {
        private final List<String> departments;

        public DepartmentAdapter(List<String> departments) {
            this.departments = departments;
        }

        @NonNull
        @Override
        public DepartmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_department, parent, false);
            return new DepartmentViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull DepartmentViewHolder holder, int position) {
            holder.bind(departments.get(position));
        }

        @Override
        public int getItemCount() {
            return departments.size();
        }
    }

    // ViewHolder for Departments
    public static class DepartmentViewHolder extends RecyclerView.ViewHolder {
        private final TextView departmentName;

        public DepartmentViewHolder(@NonNull View itemView) {
            super(itemView);
            departmentName = itemView.findViewById(R.id.tv_department_name);
        }

        public void bind(String department) {
            departmentName.setText(department);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_org_structure, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.rv_executive_committee);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        List<ExecutiveCommittee> executiveCommittees = createExecutiveCommitteeList();
        ExecutiveCommitteeAdapter adapter = new ExecutiveCommitteeAdapter(executiveCommittees);
        recyclerView.setAdapter(adapter);

        return view;
    }

    private List<ExecutiveCommittee> createExecutiveCommitteeList() {
        List<ExecutiveCommittee> committees = new ArrayList<>();

        // Add Executive Committees with their departments
        committees.add(new ExecutiveCommittee("President", createDepartmentList(
                "Technical Affairs",
                "Spiritual Development and Multi-Faith Services",
                "Students' Rights and Welfare"
        )));

        committees.add(new ExecutiveCommittee("Executive Vice President", createDepartmentList(
                "Publicity and Media Affairs",
                "Extension Services and Community Relations",
                "Gender and Development"
        )));

        committees.add(new ExecutiveCommittee("Finance Officer", createDepartmentList(
                "Resource Generation",
                "Procurement and Acquisition",
                "Finance Matters"
        )));

        committees.add(new ExecutiveCommittee("Secretary General", createDepartmentList(
                "Academic Affairs",
                "Culture and Arts",
                "External Affairs"
        )));

        committees.add(new ExecutiveCommittee("Environmental Protection, Health, and Sanitation", createDepartmentList(
                "Disaster Risk Reduction and Management",
                "Sports Development",
                "Branding and Creative Design"
        )));

        return committees;
    }

    private List<String> createDepartmentList(String... departments) {
        List<String> departmentList = new ArrayList<>();
        for (String department : departments) {
            departmentList.add(department);
        }
        return departmentList;
    }
}
