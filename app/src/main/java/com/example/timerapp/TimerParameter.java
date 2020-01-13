package com.example.timerapp;

public class TimerParameter {
    private String workMinutes;
    private String workSeconds;
    private String restMinutes;
    private String restSeconds;
    private String set;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String name;


    public TimerParameter(String workMinutes, String workSeconds, String restMinutes, String restSeconds, String set, String name) {
        this.workMinutes = workMinutes;
        this.workSeconds = workSeconds;
        this.restMinutes = restMinutes;
        this.restSeconds = restSeconds;
        this.set = set;
        this.name = name;
    }

    public String getWorkMinutes() {
        return workMinutes;
    }

    public void setWorkMinutes(String workMinutes) {
        this.workMinutes = workMinutes;
    }

    public String getWorkSeconds() {
        return workSeconds;
    }

    public void setWorkSeconds(String workSeconds) {
        this.workSeconds = workSeconds;
    }

    public String getRestMinutes() {
        return restMinutes;
    }

    public void setRestMinutes(String restMinutes) {
        this.restMinutes = restMinutes;
    }

    public String getRestSeconds() {
        return restSeconds;
    }

    public void setRestSeconds(String restSeconds) {
        this.restSeconds = restSeconds;
    }

    public String getSet() {
        return set;
    }

    public void setSet(String set) {
        this.set = set;
    }

    @Override
    public String toString() {
        return getName();
    }

}
