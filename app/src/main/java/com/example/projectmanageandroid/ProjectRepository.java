package com.example.projectmanageandroid;

import org.ProjectService.Project;
import org.ProjectService.ProjectService;

import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.ExecutionException;

public class ProjectRepository {
    private static ProjectRepository instance = null;
    private final ProjectService projectService;
    private final ExecutorService executorService;

    private ProjectRepository() {
        ProjectService.setBaseUrl("http://10.0.2.2:8080/project/");
        projectService = ProjectService.getInstance();
        executorService = Executors.newSingleThreadExecutor();
    }

    public static synchronized ProjectRepository getInstance() {
        if (instance == null) {
            instance = new ProjectRepository();
        }
        return instance;
    }

    public Project[] fetchProjects() {
        Future<Project[]> future = executorService.submit(projectService::readProjects);
        try {
            return future.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Project getProject(Integer id){
        Future<Project> future = executorService.submit(() -> projectService.getProjectById(id));
        try {
            return future.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return null;
        }
    }
    public void addProject(Project project) {
        executorService.submit(() -> projectService.addProjects(project));
    }
    public void updateProject(Project project) {
        executorService.submit(() -> projectService.updateProjects(project));
    }

    public void deleteProject(Integer projectID) {
        executorService.submit(() -> projectService.deleteProjects(projectID));
    }

    public void shutdown() {
        executorService.shutdown();
    }
}
