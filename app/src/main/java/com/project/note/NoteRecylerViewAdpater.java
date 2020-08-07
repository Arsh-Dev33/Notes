package com.project.note;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class NoteRecylerViewAdpater extends ListAdapter<Notes,NoteRecylerViewAdpater.ViewHolder> {
   //  List<Notes>  notes = new ArrayList<>();
    public OnItemClickListener listener;

    protected NoteRecylerViewAdpater() {
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<Notes> DIFF_CALLBACK = new DiffUtil.ItemCallback<Notes>() {
        @Override
        public boolean areItemsTheSame(@NonNull Notes oldItem, @NonNull Notes newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Notes oldItem, @NonNull Notes newItem) {
            return oldItem.getTitle().equals(newItem.getTitle()) &&
                    oldItem.getDescription().equals(newItem.getDescription()) &&
                    oldItem.getPriority() == newItem.getPriority();

        }
    };

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item_row,null,false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Notes currentNote = getItem(position);
        holder.tvTitle.setText(currentNote.getTitle());
        holder.tvDesc.setText(currentNote.getDescription());
        holder.tvPriority.setText(String.valueOf(currentNote.getPriority()));
    }


    public Notes getNotesAt (int position){
        return getItem(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvTitle,tvDesc,tvPriority;

        public ViewHolder(View view){
            super(view);

            tvTitle = view.findViewById(R.id.tv_title);
            tvDesc = view.findViewById(R.id.tv_des);
            tvPriority = view.findViewById(R.id.tv_priority);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if(listener != null && position != RecyclerView.NO_POSITION){
                        listener.OnItemClick(getItem(position));
                    }
                }
            });
        }
    }

    public interface OnItemClickListener{
        void OnItemClick(Notes notes);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }
}
