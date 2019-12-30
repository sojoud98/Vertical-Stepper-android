package com.example.vertical_stepper_example;

import android.view.View;
import android.widget.DatePicker;

import ernestoyaquello.com.verticalstepperform.Step;

public class DatePickerStep extends Step<String> {
    DatePicker datePicker;
    static int day = 1, month = 1, year = 2020;

    public DatePickerStep(String stepTitle) {
        super(stepTitle);
    }

    @Override
    protected View createStepContentLayout() {
        // Here we generate the view that will be used by the library as the content of the step.
        // In this case we do it programmatically, but we could also do it by inflating an XML layout.
        datePicker = new DatePicker(getContext());
        datePicker.setOnDateChangedListener(new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                markAsCompletedOrUncompleted(true);

            }
        });
        return datePicker;

    }

    @Override
    protected IsDataValid isStepDataValid(String stepData) {
        //define your constraint on date here
        return new IsDataValid(true, "");
    }

    @Override
    public String getStepData() {
        day = datePicker.getDayOfMonth();
        month = datePicker.getMonth();
        year = datePicker.getYear();
        return "Date ready";
    }

    @Override
    public String getStepDataAsHumanReadableString() {
        String message="";
        //define human readable message here
        return message;
    }

    @Override
    protected void onStepOpened(boolean animated) {
    }

    @Override
    protected void onStepClosed(boolean animated) {
        day = datePicker.getDayOfMonth();
        month = datePicker.getMonth();
        year = datePicker.getYear();
    }

    @Override
    protected void onStepMarkedAsCompleted(boolean animated) {
    }

    @Override
    protected void onStepMarkedAsUncompleted(boolean animated) {
    }

    @Override
    public void restoreStepData(String stepData) {
//        MobileView.setText(stepData);
    }

    public  int getYear() {
        return datePicker.getYear();
    }

    public  int getMonth() {
        return datePicker.getMonth();
    }

    public  int getDay() {
        return datePicker.getDayOfMonth();
    }

}