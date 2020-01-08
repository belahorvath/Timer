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
        timeSecondsRest = v.findViewById(R.id.txt_editRestSeconds);
        timeMinutesRest = v.findViewById(R.id.txt_editRestMinute);
        addButtonWork = v.findViewById(R.id.btn_addWork);
        removeButtonWork = v.findViewById(R.id.btn_removeWork);
        addButtonRest = v.findViewById(R.id.btn_addRest);
        removeButtonRest = v.findViewById(R.id.btn_removeRest);

        addButtonSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(set.getText().toString().equals("")){
                    set.setText("1");
                }else{
                    set.setText( Integer.toString(Integer.parseInt(set.getText().toString())+1));
                }

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
                    set.setText(tempSequenze);
                    Toast.makeText(v.getContext(), "Ungültige Setzahl eingegeben!", Toast.LENGTH_SHORT).show();

                }
            }
        });

        //Validates the Minutes after you leave the field
        timeSecondsWork.setOnFocusChangeListener(new CustomFocus(timeSecondsWork,timeMinutesWork));
        timeSecondsRest.setOnFocusChangeListener(new CustomFocus(timeSecondsRest, timeMinutesRest));

        //Validates calculates the + button
        addButtonWork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!timeSecondsWork.getText().toString().equals("")) {
                    if (timeSecondsWork.length() == 1) {
                        timeSecondsWork.setText("0" + timeSecondsWork.getText());
                    }
                    if(timeMinutesWork.getText().toString().equals("")){
                        timeMinutesWork.setText("00");
                    }
                    Character temp0 = timeSecondsWork.getText().charAt(0);
                    Character temp1 = timeSecondsWork.getText().charAt(1);
                    int toIncrease = Integer.parseInt(temp1.toString());
                    if (toIncrease == 9) {
                        if (Integer.parseInt(timeSecondsWork.getText().toString()) == 59) {
                            timeSecondsWork.setText("00");
                            if (!timeMinutesWork.getText().toString().equals("")) {
                                int toIncreaseMinutes = Integer.parseInt(timeMinutesWork.getText().toString());
                                timeMinutesWork.setText(Integer.toString(toIncreaseMinutes+1));
                            } else {
                                timeMinutesWork.setText("00");
                            }
                        }else{
                            timeSecondsWork.setText((Integer.parseInt(temp0.toString()) + 1) + "0");
                        }
                    } else {
                        timeSecondsWork.setText(temp0.toString() + (toIncrease + 1));
                    }
                }else{
                    timeSecondsWork.setText("00");
                }
            }
        });

        removeButtonWork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!timeSecondsWork.getText().toString().equals("")) {
                    //Return if your Numbers are all 00:00 or 0:00
                    if(!timeMinutesWork.getText().toString().equals("")){
                        if(Integer.parseInt(timeMinutesWork.getText().toString()) + Integer.parseInt(timeSecondsWork.getText().toString()) == 0){
                            return;
                        }
                    }else{
                        timeMinutesWork.setText("00");
                    }
                    //Check for completeness of Numbers
                    if (timeSecondsWork.length() == 1) {
                        timeSecondsWork.setText("0" + timeSecondsWork.getText());
                    }
                    Character temp0 = timeSecondsWork.getText().charAt(0);
                    Character temp1 = timeSecondsWork.getText().charAt(1);
                    int toLower = Integer.parseInt(temp1.toString());
                    if (toLower == 0) {
                        if (Integer.parseInt(timeSecondsWork.getText().toString()) == 0) {
                            timeSecondsWork.setText("59");
                            if (!timeMinutesWork.getText().toString().equals("")) {
                                int toLowerMinutes = Integer.parseInt(timeMinutesWork.getText().toString());
                                timeMinutesWork.setText(Integer.toString(toLowerMinutes-1));
                            } else {
                                timeMinutesWork.setText("00");
                            }
                        }else{
                            timeSecondsWork.setText((Integer.parseInt(temp0.toString())-1) + "9");
                        }
                    } else {
                        timeSecondsWork.setText(temp0.toString() + (toLower - 1));
                    }
                }else{
                    timeSecondsWork.setText("00");
                }
            }
        });
    }

}
