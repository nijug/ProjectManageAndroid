package com.example.projectmanageandroid.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.icu.util.Calendar;
import android.net.ParseException;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.projectmanageandroid.ProjectRepository;
import com.example.projectmanageandroid.R;
import com.example.projectmanageandroid.databinding.ActivityAddProjectBinding;
import org.ProjectService.Project;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Objects;
public class AddProjectActivity extends AppCompatActivity {
    private ActivityAddProjectBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddProjectBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.project_priority_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.projectPrioritySpinner.setAdapter(adapter);

        binding.addProjectButton.setOnClickListener(v -> addProject());

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
    }

    private void addProject() {
        String projectName = binding.projectNameEditText.getEditText().getText().toString();
        String projectDescription = binding.projectDescriptionEditText.getEditText().getText().toString();
        String projectStartDate = binding.projectStartDateEditText.getEditText().getText().toString();
        String projectEndDate = binding.projectEndDateEditText.getEditText().getText().toString();
        String projectPriority = binding.projectPrioritySpinner.getSelectedItem().toString();

        if (projectName.isEmpty() || projectDescription.isEmpty() || projectStartDate.isEmpty() || projectEndDate.isEmpty() || projectPriority.isEmpty()) {
            Toast.makeText(AddProjectActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
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

            Project project = new Project(projectName, projectDescription, startDate, endDate, projectPriority);
            ProjectRepository projectRepository = ProjectRepository.getInstance();
            projectRepository.addProject(project);

            Intent returnIntent = new Intent();
            setResult(RESULT_OK, returnIntent);
            finish();
        }
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
