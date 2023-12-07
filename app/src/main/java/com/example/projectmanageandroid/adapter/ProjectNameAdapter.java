package com.example.projectmanageandroid.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.projectmanageandroid.R;
import com.example.projectmanageandroid.activity.ProjectDetailsActivity;
import org.ProjectService.Project;

public class ProjectNameAdapter extends RecyclerView.Adapter<ProjectNameAdapter.ProjectViewHolder> {
    private String[] projectNames;
    private Integer[] projectIds;

    public ProjectNameAdapter(Integer [] projectIds, String[] projectNames) {
        this.projectNames = projectNames;
        this.projectIds = projectIds;
    }

    @NonNull
    @Override
    public ProjectViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.project_item , parent, false);
        return new ProjectViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProjectViewHolder holder, int position) {
        holder.projectNameTextView.setText(projectNames[position]);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    Intent intent = new Intent(v.getContext(), ProjectDetailsActivity.class);
                    intent.putExtra("projectId", projectIds[position]);
                    v.getContext().startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return projectNames.length;
    }

    static class ProjectViewHolder extends RecyclerView.ViewHolder {
        TextView projectNameTextView;

        public ProjectViewHolder(@NonNull View itemView) {
            super(itemView);
            projectNameTextView = itemView.findViewById(R.id.projectNameTextView);
        }
    }


}
