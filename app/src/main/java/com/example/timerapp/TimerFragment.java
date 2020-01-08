package com.example.timerapp;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class TimerFragment extends Fragment {
    ImageButton addButtonSet;
    ImageButton removeButtonSet;
    ImageButton addButtonWork;
    ImageButton removeButtonWork;
    ImageButton addButtonRest;
    ImageButton removeButtonRest;
    EditText set;
    EditText timeMinutesWork;
    EditText timeSecondsWork;
    EditText timeMinutesRest;
    EditText timeSecondsRest;


    public TimerFragment(){
        //Empty Constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //Prepare the View
        View v = inflater.inflate(R.layout.fragment_timer,container,false);

        //All Klick listeners and Validators
        addListeners(v);

        //Return finished fragment view
        return v;
    }

    private void addListeners(final View v){
        addButtonSet = v.findViewById(R.id.btn_addSet);
        removeButtonSet = v.findViewById(R.id.btn_removeSet);
        set = v.findViewById(R.id.txt_editSet);
        timeMinutesWork = v.findViewById(R.id.txt_editWorkMinute);
        timeSecondsWork = v.findViewById(R.id.txt_editWorkSeconds);
        addButtonWork = v.findViewById(R.id.btn_addWork);
        removeButtonWork = v.findViewById(R.id.btn_removeWork);

        addButtonSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                set.setText( Integer.toString(Integer.parseInt(set.getText().toString())+1));
            }
        });

        removeButtonSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!set.getText().toString().equals("")){
                    int setNumber = Integer.parseInt(set.getText().toString());
                    if( setNumber > 1){
                        set.setText(Integer.toString(setNumber-1));
                    }else{
                        Toast.makeText(v.getContext(), "Setzahl darf nicht null sein!", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    set.setText(Integer.toString(1));
                }

            }
        });

        //Check that only valid Sets can be inserted
        set.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                if(s.length() != 0 && s.charAt(0) == '0'){
                    String tempSequenze = s.subSequence(1,s.length()).toString();
                    set.setText((CharSequence)tempSequenze);
                    Toast.makeText(v.getContext(), "Ungültige Setzahl eingegeben!", Toast.LENGTH_SHORT).show();

                }
            }
        });

        //Convert Seconds to more minutes!
        timeSecondsWork.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length() != 0){
                    int seconds = Integer.parseInt(s.toString());
                    int minutesToAdd = 0;
                    if(!timeMinutesWork.getText().equals("00")){
                        minutesToAdd += Integer.parseInt(timeMinutesWork.getText().toString());
                    }
                    boolean finished = false;
                    if(seconds > 60){
                        while(seconds % 60 != 0 && !finished){
                            int minutes = seconds/60;
                            if(minutes == 0){
                                if(seconds < 10){
                                    timeSecondsWork.setText('0'+ Integer.toString(seconds));
                                }else{
                                    timeSecondsWork.setText(Integer.toString(seconds));
                                }
                                finished = true;
                            }else{
                                minutesToAdd ++;
                                seconds = seconds % 60;
                            }

                        }
                        timeMinutesWork.setText(Integer.toString(minutesToAdd));
                    }
                }
            }
        });

        addButtonWork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!timeSecondsWork.getText().equals("")){
                    if(timeSecondsWork.getText().charAt(0) == '0'){
                        Character toIncrease = timeSecondsWork.getText().charAt(1);
                        if( toIncrease == '9'){
                            timeSecondsWork.setText("10");
                        }else{
                            timeSecondsWork.setText("0" + String.valueOf(Integer.parseInt(toIncrease.toString())+1));
                        }
                    //needs fix!
                    }else if(timeSecondsWork.getText().charAt(1) == '0'){
                        Character toIncrease = timeSecondsWork.getText().charAt(1);
                        Character leading = timeSecondsWork.getText().charAt(0);
                        if( toIncrease == '9'){
                            timeSecondsWork.setText(String.valueOf(Integer.parseInt(leading.toString())+1)+ "0");
                        }else{
                            timeSecondsWork.setText("0" + String.valueOf(Integer.parseInt(toIncrease.toString())+1));
                        }
                    }
                    /*else {
                        if (Integer.parseInt(timeSecondsWork.getText().toString()) == 59) {
                            timeSecondsWork.setText("00");
                            if (!timeMinutesWork.getText().equals("")) {
                                timeMinutesWork.setText(Integer.parseInt(timeMinutesWork.getText().toString()) + 1);
                            } else {
                                timeMinutesWork.setText("1");
                            }
                        } else {
                            timeSecondsWork.setText(Integer.parseInt(timeSecondsWork.getText().toString()) + 1);
                        }
                    }

                     */

                }

            }
        });
    }

}
