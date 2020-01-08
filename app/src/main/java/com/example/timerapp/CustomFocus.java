package com.example.timerapp;

import android.view.View;
import android.widget.EditText;

public class CustomFocus implements View.OnFocusChangeListener {
    EditText timeSeconds;
    EditText timeMinutes;
    public CustomFocus(EditText timeSeconds, EditText timeMinutes){
        this.timeSeconds = timeSeconds;
        this.timeMinutes = timeMinutes;
    }
    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (!hasFocus) {
            // Convert the Text into minutes
            if(!timeSeconds.getText().toString().equals("")){
                int seconds = Integer.parseInt(timeSeconds.getText().toString());
                int minutesToAdd = 0;
                if(!timeMinutes.getText().equals("00")){
                    minutesToAdd += Integer.parseInt(timeMinutes.getText().toString());
                }
                boolean finished = false;
                if(seconds >= 60){
                    while(seconds % 60 != 0 && !finished){
                        int minutes = seconds/60;
                        if(minutes == 0){
                            if(seconds < 10){
                                timeSeconds.setText('0'+ Integer.toString(seconds));
                            }else{
                                timeSeconds.setText(Integer.toString(seconds));
                            }
                            finished = true;
                        }else{
                            minutesToAdd ++;
                            seconds = seconds % 60;
                        }

                    }
                    if(seconds % 60 == 0 && !finished){
                        minutesToAdd += seconds/60;
                        timeSeconds.setText("00");
                        finished = true;
                    }
                    timeMinutes.setText(Integer.toString(minutesToAdd));
                }
            }
        }
    }
}
