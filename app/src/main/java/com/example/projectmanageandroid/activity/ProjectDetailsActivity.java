package com.example.projectmanageandroid.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import com.example.projectmanageandroid.ProjectRepository;
import com.example.projectmanageandroid.R;
import com.example.projectmanageandroid.databinding.ActivityProjectDetailsBinding;
import com.example.projectmanageandroid.viewmodel.ProjectDetailsViewModel;
import org.ProjectService.Project;
public class ProjectDetailsActivity extends AppCompatActivity {
    private static final int REQUEST_UPDATE = 1;
    private ProjectDetailsViewModel viewModel;
    private ActivityProjectDetailsBinding binding;

    private int projectId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProjectDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this).get(ProjectDetailsViewModel.class);

        projectId = getIntent().getIntExtra("projectId", -1);
        viewModel.fetchProject(projectId);
        populateViews(viewModel.getProject());

        binding.updateProjectButton.setOnClickListener(v -> updateProject());
        binding.deleteProjectButton.setOnClickListener(v -> deleteProject());
    }

    private void populateViews(Project project) {
        binding.projectNameTextView.setText(project.getName());
        binding.projectDescriptionTextView.setText(getString(R.string.project_description, project.getDescription()));
        binding.projectStartDateTextView.setText(getString(R.string.project_start_date, project.getDateStarted().toString()));
        binding.projectEndDateTextView.setText(getString(R.string.project_end_date, project.getDateEnded().toString()));
        binding.projectPriorityTextView.setText(getString(R.string.project_priority, project.getPriority()));
    }

    private void updateProject() {
        Intent intent = new Intent(ProjectDetailsActivity.this, UpdateProjectActivity.class);
        viewModel.fetchProject(projectId);
        intent.putExtra("project", viewModel.getProject());
        startActivityForResult(intent, REQUEST_UPDATE);
    }

    private void deleteProject() {
        new AlertDialog.Builder(ProjectDetailsActivity.this)
                .setTitle(R.string.delete_project)
                .setMessage(R.string.delete_project_confirmation)
                .setPositiveButton(android.R.string.yes, (dialog, which) -> {
                    viewModel.deleteProject();
                    finish();
                })
                .setNegativeButton(android.R.string.no, null)
                .setIcon(R.drawable.ic_launcher_background)
                .show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_UPDATE && resultCode == RESULT_OK) {
            Project updatedProject = (Project) data.getSerializableExtra("project");
            populateViews(updatedProject);
        }
    }
}
