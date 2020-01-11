package com.example.timerapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.List;

public class ChecklistAdapter extends RecyclerView.Adapter<ChecklistAdapter.AufgabenViewHolder> {

    private onItemClickListener mListener;

    public interface onItemClickListener{
        void onDeleteListener(int position);
        void onEditListener(int position);

    }

    public void setOnItemClickListener(onItemClickListener listener){
        mListener = listener;
    }
    private Context context;
    private List<Aufgabe> aufgabenList;

    public ChecklistAdapter(Context context, List<Aufgabe> aufgabenList){
        this.context = context;
        this.aufgabenList = aufgabenList;
    }
    @NonNull
    @Override
    public AufgabenViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.checklist_layout,null);
        return new AufgabenViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull AufgabenViewHolder holder, int position) {
        Aufgabe aufgabe = aufgabenList.get(position);
        holder.aufgabe.setText(aufgabe.getAufgabe());
        holder.beschreibung.setText(aufgabe.getBeschreibung());
    }

    @Override
    public int getItemCount() {
        return aufgabenList.size();
    }

    public static class AufgabenViewHolder extends RecyclerView.ViewHolder{
        TextView aufgabe, beschreibung;
        ImageButton edit, remove;

        public AufgabenViewHolder(@NonNull View itemView, final onItemClickListener listener) {
            super(itemView);

            aufgabe = itemView.findViewById(R.id.txt_aufgabe);
            beschreibung = itemView.findViewById(R.id.txt_beschreibung);
            edit = itemView.findViewById(R.id.btn_editAufgabe);
            remove = itemView.findViewById(R.id.btn_deleteAufgabe);

            remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            listener.onDeleteListener(position);
                        }
                    }
                }
            });

            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            listener.onEditListener(position);
                        }
                    }
                }
            });


        }
    }
}
