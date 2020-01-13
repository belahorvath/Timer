package com.example.timerapp;

import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ChecklistFragment extends Fragment {

    RecyclerView recyclerView;
    ChecklistAdapter globalAdapter;
    List<Aufgabe> aufgabeList;
    private ImageButton addAufgabe;
    private ImageButton saveAufgabe;
    private EditText idAufgabe;
    private EditText aufgabenTitel;
    private  EditText aufgabenBeschreibung;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_checklist,container,false);
        recyclerView = v.findViewById(R.id.recyclerView);

        createList();
        buildRecyclerView();
        addAufgabe = v.findViewById(R.id.btn_addAufgabe);
        aufgabenTitel = v.findViewById(R.id.titelAufgabe);
        aufgabenBeschreibung = v.findViewById(R.id.beschreibungAufgabe);
        saveAufgabe = v.findViewById(R.id.btn_saveAufgabe);
        idAufgabe = v.findViewById(R.id.aufgabenId);

        saveAufgabe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(int i = 0;i<aufgabeList.size();i++){
                    if(aufgabeList.get(i).getId() == Integer.parseInt(idAufgabe.getText().toString())) {
                        Aufgabe aufgabe = new Aufgabe(aufgabeList.get(i).getId(),aufgabenTitel.getText().toString(),aufgabenBeschreibung.getText().toString());
                        aufgabeList.set(i,aufgabe);
                        globalAdapter.notifyDataSetChanged();
                    }
                }

                saveAufgabe.setEnabled(false);
                saveAufgabe.setVisibility(View.INVISIBLE);
                addAufgabe.setEnabled(true);
                addAufgabe.setVisibility(View.VISIBLE);
                aufgabenTitel.getText().clear();
                aufgabenBeschreibung.getText().clear();
                idAufgabe.getText().clear();
            }
        });

        addAufgabe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Aufgabe aufgabe;
                if(aufgabeList.size() == 0){
                    aufgabe = new Aufgabe(0,aufgabenTitel.getText().toString(),aufgabenBeschreibung.getText().toString());
                }else{
                    aufgabe = new Aufgabe(aufgabeList.size(),aufgabenTitel.getText().toString(),aufgabenBeschreibung.getText().toString());
                }

                aufgabeList.add(aufgabe);
                globalAdapter.notifyItemInserted(aufgabeList.indexOf(aufgabe));

                aufgabenTitel.getText().clear();
                aufgabenBeschreibung.getText().clear();
            }
        });
        return v;
    }

    public void createList(){
        aufgabeList = new ArrayList<Aufgabe>();
    }

    public void buildRecyclerView(){
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        globalAdapter = new ChecklistAdapter(this.getContext(),aufgabeList);
        recyclerView.setAdapter(globalAdapter);

        globalAdapter.setOnItemClickListener(new ChecklistAdapter.onItemClickListener() {
            @Override
            public void onDeleteListener(int position) {
                removeAufgabe(position);
            }

            @Override
            public void onEditListener(int position) {
                editAufgabe(position);
            }
        });

    }

    public void removeAufgabe(int position){
        aufgabeList.remove(position);
        globalAdapter.notifyItemRemoved(position);
    }

    public void editAufgabe(int position){
        Aufgabe a = aufgabeList.get(position);
        aufgabenTitel.setText(a.getAufgabe());
        aufgabenBeschreibung.setText(a.getBeschreibung());
        idAufgabe.setText(Integer.toString(a.getId()));

        addAufgabe.setEnabled(false);
        addAufgabe.setVisibility(View.INVISIBLE);
        saveAufgabe.setEnabled(true);
        saveAufgabe.setVisibility(View.VISIBLE);

    }
}
