package com.example.timerapp;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class TimerRunningFragment extends Fragment {
    int setCounter;
    private ImageButton button;
    private TextView workText;
    private TextView setText;
    private TextView activityText;
    private View overlay;

    private long workTimeLeftMillies;
    private long restTimeLeftMillies;
    private final long workTime;
    private final long restTime;
    private long prepairTimeLeftMillies;
    private boolean isPrepairing = true;
    private boolean isWorking = false;
    private boolean isResting = false;
    private boolean timerRunning;
    private boolean overlayOpen = false;
    private CountDownTimer countDownTimer;


    public TimerRunningFragment(int workMinutes,int workSeconds, int restMinutes, int restSeconds, int setCounter){
        //Empty Constructor

        this.workTimeLeftMillies = (workMinutes*60 + workSeconds) * 1000;
        this.restTimeLeftMillies = (restMinutes*60 + restSeconds) * 1000;
        this.workTime = workTimeLeftMillies;
        this.restTime = restTimeLeftMillies;
        this.prepairTimeLeftMillies = 5000;
        this.setCounter = setCounter;

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //Prepare the View
        View v = inflater.inflate(R.layout.fragment_timer_running,container,false);
        button = v.findViewById(R.id.btn_play);
        workText = v.findViewById(R.id.txt_work);
        setText = v.findViewById(R.id.txt_set);
        activityText = v.findViewById(R.id.txt_activity);
        setText.setText(Integer.toString(setCounter));

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startStop();
                if(!overlayOpen){
                    //OPEN OVERLAY DIALOG
                    overlayOpen = true;
                }else{
                    //CLOSE OVERLAY DIALOG
                    overlayOpen = false;
                }
            }
        });
        startStop();
        return v;
    }

    public void startStop(){
        if(timerRunning){
            pauseTimer();
        }else{
            startTimer();
        }
    }

    public void startTimer(){
        if (setCounter != 0){
            if(isPrepairing){
                countDownTimer = new CountDownTimer(prepairTimeLeftMillies, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        prepairTimeLeftMillies = millisUntilFinished;
                        updateTimer(prepairTimeLeftMillies);
                    }

                    @Override
                    public void onFinish() {
                        //Open finished!
                        isPrepairing = false;
                        isWorking = true;
                        timerRunning = false;
                        startStop();
                    }
                }.start();
                timerRunning = true;
            }else if (isWorking){
                countDownTimer = new CountDownTimer(workTimeLeftMillies, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        workTimeLeftMillies = millisUntilFinished;
                        updateTimer(workTimeLeftMillies);
                    }

                    @Override
                    public void onFinish() {
                        //Open finished!
                        isWorking = false;
                        isResting = true;
                        timerRunning = false;
                        workTimeLeftMillies = workTime;
                        startStop();
                    }
                }.start();
                this.getView().setBackgroundColor(getResources().getColor(android.R.color.holo_blue_bright));
                activityText.setText("WORKING");
                timerRunning = true;
            }else if(isResting){
                countDownTimer = new CountDownTimer(restTimeLeftMillies, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        restTimeLeftMillies = millisUntilFinished;
                        updateTimer(restTimeLeftMillies);
                    }

                    @Override
                    public void onFinish() {
                        //Open finished!
                        isResting = false;
                        isWorking = true;
                        timerRunning = false;
                        restTimeLeftMillies = restTime;
                        setCounter -= 1;
                        setText.setText(Integer.toString(setCounter));
                        startStop();
                    }
                }.start();
                this.getView().setBackgroundColor(getResources().getColor(android.R.color.holo_green_light));
                activityText.setText("RESTING");
            }else{

            }
            button.setImageResource(R.drawable.ic_pause);
        }else{
            timerRunning = false;
            isResting = false;
            isWorking = false;
            isPrepairing = false;
            activityText.setText("FINISHED");
            this.getView().setBackgroundColor(getResources().getColor(android.R.color.holo_red_light));
        }
    }

    public void pauseTimer(){
        countDownTimer.cancel();
        timerRunning = false;
        button.setImageResource(R.drawable.ic_play);
    }

    public void updateTimer(long millies){
        int minutes = (int) millies/600000;
        int seconds = (int) millies%600000/1000;

        String timeLeftText;
        if(minutes < 10){
            timeLeftText = "0" + minutes;
        }else{
            timeLeftText = "" + minutes;
        }
        timeLeftText += " : ";
        if(seconds < 10){
            timeLeftText += "0";
        }
        timeLeftText += seconds;

        workText.setText(timeLeftText);



    }
}
