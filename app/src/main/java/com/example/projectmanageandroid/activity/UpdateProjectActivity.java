package com.example.projectmanageandroid.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.icu.util.Calendar;
import android.net.ParseException;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import com.example.projectmanageandroid.ProjectRepository;
import com.example.projectmanageandroid.R;
import com.example.projectmanageandroid.databinding.ActivityUpdateProjectBinding;
import com.example.projectmanageandroid.viewmodel.ProjectActivityViewModel;
import org.ProjectService.Project;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Objects;

public class UpdateProjectActivity extends AppCompatActivity {
    private ActivityUpdateProjectBinding binding;
    private Project updatedProject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUpdateProjectBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        Project project = (Project) getIntent().getSerializableExtra("project");

        binding.projectNameEditText.getEditText().setText(project.getName());
        binding.projectDescriptionEditText.getEditText().setText(project.getDescription());

        java.util.Date startDate = new java.util.Date(project.getDateStarted().getTime());
        java.util.Date endDate = new java.util.Date(project.getDateEnded().getTime());


        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String startDateString = format.format(startDate);
        String endDateString = format.format(endDate);

        binding.projectStartDateEditText.getEditText().setText(startDateString);
        binding.projectEndDateEditText.getEditText().setText(endDateString);

        
        binding.projectStartDateEditText.getEditText().setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                showDatePickerDialog(binding.projectStartDateEditText.getEditText());
            }
        });

        binding.projectEndDateEditText.getEditText().setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                showDatePickerDialog(binding.projectEndDateEditText.getEditText());
            }
        });

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.project_priority_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.projectPrioritySpinner.setAdapter(adapter);
        binding.projectPrioritySpinner.setSelection(adapter.getPosition(project.getPriority()));

        ProjectRepository projectRepository = ProjectRepository.getInstance();

        binding.updateProjectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String projectName = binding.projectNameEditText.getEditText().getText().toString();
                String projectDescription = binding.projectDescriptionEditText.getEditText().getText().toString();
                String projectStartDate = binding.projectStartDateEditText.getEditText().getText().toString();
                String projectEndDate = binding.projectEndDateEditText.getEditText().getText().toString();
                String projectPriority = binding.projectPrioritySpinner.getSelectedItem().toString();

                if (projectName.isEmpty() || projectDescription.isEmpty() || projectPriority.isEmpty()) {
                    Toast.makeText(UpdateProjectActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                } else {


                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                    Date startDate = null;
                    Date endDate = null;
                    try {
                        startDate = new Date(Objects.requireNonNull(format.parse(projectStartDate)).getTime());
                        endDate = new Date(Objects.requireNonNull(format.parse(projectEndDate)).getTime());
                    } catch (java.text.ParseException e) {
                        e.printStackTrace();
                    }
                    updatedProject = new Project(projectName, projectDescription, startDate, endDate, projectPriority);
                    updatedProject.setId(project.getId());
                    projectRepository.updateProject(updatedProject);

                    Intent resultIntent = new Intent();
                    resultIntent.putExtra("project", updatedProject);
                    setResult(RESULT_OK, resultIntent);
                    finish();
                }
            }
        });
    }

    private void showDatePickerDialog(EditText editText) {
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                (view, year, month, dayOfMonth) -> {

                    Calendar calendar = Calendar.getInstance();
                    calendar.set(year, month, dayOfMonth);
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                    editText.setText(format.format(calendar.getTime()));

                    editText.setInputType(InputType.TYPE_NULL);
                    editText.setFocusable(false);

                },
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.setOnDismissListener(dialog -> {
            editText.setFocusableInTouchMode(true);
        });
        datePickerDialog.show();
    }
}
