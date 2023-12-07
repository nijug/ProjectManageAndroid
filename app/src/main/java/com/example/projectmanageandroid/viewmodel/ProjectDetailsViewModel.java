package com.example.projectmanageandroid.viewmodel;

import androidx.lifecycle.ViewModel;
import com.example.projectmanageandroid.ProjectRepository;
import org.ProjectService.Project;

public class ProjectDetailsViewModel extends ViewModel {
    private Project project;
    private final ProjectRepository projectRepository;

    public ProjectDetailsViewModel() {
        projectRepository = ProjectRepository.getInstance();
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Project getProject() {
        return project;
    }
    public void fetchProject(int projectId) {
        this.project = projectRepository.getProject(projectId);
    }
    public void deleteProject() {
        projectRepository.deleteProject(project.getId());
    }
}