package com.example.noteshub.helpers;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.noteshub.R;
import com.example.noteshub.model.NotesModel;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.ViewHolder> {

    private static final String TAG = "NotesAdapterTAG";
    private final List<NotesModel> notesModelList;
    private onItemClickListener listener;

    public NotesAdapter(List<NotesModel> notesModelList) {
        this.notesModelList = notesModelList;
        Log.d(TAG, "NotesAdapter: Called...");
    }


    @NonNull
    @Override
    public NotesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_notes, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotesAdapter.ViewHolder holder, int position) {

        NotesModel model = notesModelList.get(position);
        holder.tvHead.setText(model.heading);
//        holder.tvSelectedTag.setText(model.selectedTag);
//        holder.tvLastUpdatedTime.setText(model.lastUpdatedDate + "");

        String textDescription = model.description;
        if (textDescription.length() > 200) {
            textDescription = textDescription.substring(0, 200) + "...";
        }

        holder.tvDescription.setText(textDescription);


    }

    @Override
    public int getItemCount() {
        return notesModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tvHead, tvDescription, tvSelectedTag, tvLastUpdatedTime;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvHead = itemView.findViewById(R.id.tvHead);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            tvSelectedTag = itemView.findViewById(R.id.tvSelectedTag);
            tvLastUpdatedTime = itemView.findViewById(R.id.tvLastUpdatedTime);

            itemView.setOnClickListener(view -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION && listener != null) {
                    listener.onItemClick(position);
                }
            });
        }
    }

    public interface onItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(onItemClickListener listener) {
        this.listener = listener;
    }

    ;
}
