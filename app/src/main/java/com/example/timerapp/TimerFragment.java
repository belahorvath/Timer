package com.example.timerapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.sql.Time;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TimerFragment extends Fragment {
    public static final String SHARED_PREF ="sharedPrefs";
    public static final String PARAM = "parameters";
    private ImageButton addButtonSet;
    private ImageButton removeButtonSet;
    private ImageButton addButtonWork;
    private ImageButton removeButtonWork;
    private ImageButton addButtonRest;
    private ImageButton removeButtonRest;
    private ImageButton playButton;
    private Spinner dropdownSpinner;
    private ImageButton saveButton;
    private EditText set;
    private EditText timeMinutesWork;
    private EditText timeSecondsWork;
    private EditText timeMinutesRest;
    private EditText timeSecondsRest;
    ArrayList<TimerParameter> dropdownOptions=new ArrayList<TimerParameter>();



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
        loadSpinnerData(v);


        //Return finished fragment view
        return v;
    }

    private void loadSpinnerData(View v) {
        dropdownSpinner = v.findViewById(R.id.dropdown_timer);

        SharedPreferences sharedPreferences = v.getContext().getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE);

        Gson gson = new Gson();
        String json = sharedPreferences.getString(PARAM, "");
        if(json.equals("")){
            dropdownOptions.add(new TimerParameter("00","05","00","05","3","Default"));
        }else{
            Type type = new TypeToken<List<TimerParameter>>(){}.getType();
            dropdownOptions = gson.fromJson(json, type);
        }

        ArrayAdapter<TimerParameter> adapter = new ArrayAdapter<TimerParameter>(v.getContext(),android.R.layout.simple_spinner_item, dropdownOptions);
        dropdownSpinner.setAdapter(adapter);

    }

    private void addListeners(final View v){
        addButtonSet = v.findViewById(R.id.btn_addSet);
        removeButtonSet = v.findViewById(R.id.btn_removeSet);
        set = v.findViewById(R.id.txt_editSet);
        timeMinutesWork = v.findViewById(R.id.txt_editWorkMinute);
        timeSecondsWork = v.findViewById(R.id.txt_editWorkSeconds);
        timeSecondsRest = v.findViewById(R.id.txt_editSeconds);
        timeMinutesRest = v.findViewById(R.id.txt_editMinute);
        addButtonWork = v.findViewById(R.id.btn_addWork);
        removeButtonWork = v.findViewById(R.id.btn_removeWork);
        addButtonRest = v.findViewById(R.id.btn_addRest);
        removeButtonRest = v.findViewById(R.id.btn_removeRest);
        playButton = v.findViewById(R.id.btn_play);
        saveButton = v.findViewById(R.id.btn_save);
        dropdownSpinner = v.findViewById(R.id.dropdown_timer);

        dropdownSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                TimerParameter param = (TimerParameter) dropdownSpinner.getSelectedItem();
                set.setText(param.getSet());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = v.getContext().getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                TimerParameter tempParameter = new TimerParameter(timeMinutesWork.getText().toString(), timeSecondsWork.getText().toString(),
                        timeMinutesRest.getText().toString(),timeSecondsRest.getText().toString(),set.getText().toString(),Integer.toString(dropdownOptions.size()+1));
                dropdownOptions.add(tempParameter);

                Gson gson = new Gson();
                String json = gson.toJson(dropdownOptions);
                editor.putString(PARAM, json);


                Toast.makeText(v.getContext(), "Saved preset!", Toast.LENGTH_SHORT).show();
            }
        });

        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((Integer.parseInt(timeMinutesWork.getText().toString()) + Integer.parseInt(timeSecondsWork.getText().toString())) != 0 &&
                        (Integer.parseInt(timeMinutesRest.getText().toString()) + Integer.parseInt(timeSecondsRest.getText().toString())) != 0) {
                    FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.fragment_container, new TimerRunningFragment(Integer.parseInt(timeMinutesWork.getText().toString()),Integer.parseInt(timeSecondsWork.getText().toString()),
                            Integer.parseInt(timeMinutesRest.getText().toString()),Integer.parseInt(timeSecondsRest.getText().toString()),Integer.parseInt(set.getText().toString())));
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                }else{
                    Toast.makeText(v.getContext(), "Bitte gib einen Work und Rest Intervall an!", Toast.LENGTH_SHORT).show();
                }

            }
        });

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

        addButtonWork.setOnTouchListener(new RepeatListener(400, 100, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // the code to execute repeatedly
                add(timeSecondsWork,timeMinutesWork);
            }
        }));

        removeButtonWork.setOnTouchListener(new RepeatListener(400, 100, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                remove(timeSecondsWork, timeMinutesWork);
            }
        }));

        addButtonRest.setOnTouchListener(new RepeatListener(400, 100, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add(timeSecondsRest,timeMinutesRest);
            }
        }));

        removeButtonRest.setOnTouchListener(new RepeatListener(400,100, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                remove(timeSecondsRest,timeMinutesRest);
            }
        }));

    }

    private void add(EditText timeSeconds, EditText timeMinutes){
        if(!timeSeconds.getText().toString().equals("")) {
            if (timeSeconds.length() == 1) {
                timeSeconds.setText("0" + timeSeconds.getText());
            }
            if(timeMinutes.getText().toString().equals("")){
                timeMinutes.setText("00");
            }
            Character temp0 = timeSeconds.getText().charAt(0);
            Character temp1 = timeSeconds.getText().charAt(1);
            int toIncrease = Integer.parseInt(temp1.toString());
            if (toIncrease == 9) {
                if (Integer.parseInt(timeSeconds.getText().toString()) == 59) {
                    timeSeconds.setText("00");
                    if (!timeMinutes.getText().toString().equals("")) {
                        int toIncreaseMinutes = Integer.parseInt(timeMinutes.getText().toString());
                        timeMinutes.setText(Integer.toString(toIncreaseMinutes+1));
                    } else {
                        timeMinutes.setText("00");
                    }
                }else{
                    timeSeconds.setText((Integer.parseInt(temp0.toString()) + 1) + "0");
                }
            } else {
                timeSeconds.setText(temp0.toString() + (toIncrease + 1));
            }
        }else{
            timeSeconds.setText("00");
        }
    }

    private void remove(EditText timeSeconds, EditText timeMinutes){
        if(timeMinutes.getText().toString().equals("")){
            timeMinutes.setText("00");
        }
        if(!timeSeconds.getText().toString().equals("")) {
            //Return if your Numbers are all 00:00 or 0:00
            if(!timeMinutes.getText().toString().equals("")){
                if(Integer.parseInt(timeMinutes.getText().toString()) + Integer.parseInt(timeSeconds.getText().toString()) == 0){
                    return;
                }
            }else{
                timeMinutes.setText("00");
            }
            //Check for completeness of Numbers
            if (timeSeconds.length() == 1) {
                timeSeconds.setText("0" + timeSeconds.getText());
            }
            Character temp0 = timeSeconds.getText().charAt(0);
            Character temp1 = timeSeconds.getText().charAt(1);
            int toLower = Integer.parseInt(temp1.toString());
            if (toLower == 0) {
                if (Integer.parseInt(timeMinutes.getText().toString()) != 0) {
                    timeSeconds.setText("59");
                    int toLowerMinutes = Integer.parseInt(timeMinutes.getText().toString());
                    timeMinutes.setText(Integer.toString(toLowerMinutes-1));
                }else if(Integer.parseInt(timeMinutes.getText().toString()) == 0 && Integer.parseInt(temp0.toString()) != 0){
                    timeSeconds.setText((Integer.parseInt(temp0.toString())-1) + "9");

                }else{
                    timeSeconds.setText("00");
                    timeMinutes.setText("00");
                }
            } else {
                    timeSeconds.setText(temp0.toString() + (toLower - 1));
            }
        }else{
            timeSeconds.setText("00");
        }
    }

}
