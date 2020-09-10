package com.touhid.personalnotes.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.touhid.personalnotes.Model.Note;
import com.touhid.personalnotes.R;

import java.util.ArrayList;
import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder>
{
    private List<Note> allNotes = new ArrayList<>();
    private OnItemClickListener listener;

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_row, parent, false);
        return new NoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position)
    {
        holder.titleTXV.setText(allNotes.get(position).getTitle());
        holder.descriptionTXV.setText(allNotes.get(position).getDescription());
    }

    @Override
    public int getItemCount() {
        return allNotes.size();
    }

    public void setAllNotes(List<Note> note)
    {
        this.allNotes = note;
        notifyDataSetChanged();
    }

    public Note getNoteAt(int position)
    {
        return allNotes.get(position);
    }

    class NoteViewHolder extends RecyclerView.ViewHolder
    {
        public TextView titleTXV, descriptionTXV;

        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTXV = itemView.findViewById(R.id.tiltle_txv);
            descriptionTXV = itemView.findViewById(R.id.description_txv);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view)
                {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION)
                    {
                        listener.onItemClick(allNotes.get(position));
                    }
                }
            });
        }
    }

    public interface OnItemClickListener
    {
        void onItemClick(Note note);
    }

    public void setOnItemClickListener(OnItemClickListener listener)
    {
        this.listener = listener;
    }
}
