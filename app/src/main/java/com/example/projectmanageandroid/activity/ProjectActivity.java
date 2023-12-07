package com.example.projectmanageandroid.activity;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.example.projectmanageandroid.adapter.ProjectNameAdapter;
import androidx.lifecycle.ViewModelProvider;
import com.example.projectmanageandroid.databinding.ActivityProjectBinding;
import com.example.projectmanageandroid.viewmodel.ProjectActivityViewModel;
public class ProjectActivity extends AppCompatActivity {
    private static final int REQUEST_ADD_PROJECT = 1;

    private ProjectActivityViewModel viewModel;

    private ActivityProjectBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProjectBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this).get(ProjectActivityViewModel.class);
        setupProjectsRecyclerView();

        binding.addProjectButton.setOnClickListener(v -> addProject());
    }

    private void setupProjectsRecyclerView() {
        binding.projectsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.projectsRecyclerView.setAdapter(new ProjectNameAdapter(viewModel.getProjectIds(), viewModel.getProjectNames()));
    }

    private void addProject() {
        Intent intent = new Intent(ProjectActivity.this, AddProjectActivity.class);
        startActivityForResult(intent, REQUEST_ADD_PROJECT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_ADD_PROJECT && resultCode == RESULT_OK) {
            setupProjectsRecyclerView();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        viewModel = new ViewModelProvider(this).get(ProjectActivityViewModel.class);
        setupProjectsRecyclerView();
    }


}
