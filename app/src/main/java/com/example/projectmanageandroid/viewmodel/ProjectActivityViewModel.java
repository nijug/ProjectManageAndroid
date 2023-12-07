package com.example.projectmanageandroid.viewmodel;

import androidx.lifecycle.ViewModel;
import com.example.projectmanageandroid.ProjectRepository;
import org.ProjectService.Project;
import androidx.lifecycle.ViewModel;
import org.ProjectService.Project;

public class ProjectActivityViewModel extends ViewModel {
    private final ProjectRepository projectRepository;

    private  Project[] projects;
    public ProjectActivityViewModel() {
        projectRepository = ProjectRepository.getInstance();
    }

    public Integer[] getProjectIds() {
        projects = projectRepository.fetchProjects();
        Integer[] projectIds = new Integer[projects.length];
        for (int i = 0; i < projects.length; i++) {
            projectIds[i] = projects[i].getId();
        }
        return projectIds;
    }

    public String[] getProjectNames() {
        projects = projectRepository.fetchProjects();
        String[] projectNames = new String[projects.length];
        for (int i = 0; i < projects.length; i++) {
            projectNames[i] = projects[i].getName();
        }
        return projectNames;
    }

}
