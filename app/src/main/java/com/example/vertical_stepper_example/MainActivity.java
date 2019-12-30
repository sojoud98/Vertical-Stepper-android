package com.example.vertical_stepper_example;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;

import ernestoyaquello.com.verticalstepperform.VerticalStepperFormView;
import ernestoyaquello.com.verticalstepperform.listener.StepperFormListener;

import static android.widget.Toast.LENGTH_LONG;

public class MainActivity extends AppCompatActivity implements StepperFormListener {
    private NameStep nameStep;
    private StudentNumberStep studentNumberStep;
    private DatePickerStep birthDateStep;

    private VerticalStepperFormView verticalStepperForm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Create the steps.
        nameStep = new NameStep("Student Name");
        studentNumberStep = new StudentNumberStep("Student Number");
        birthDateStep = new DatePickerStep("Student Birth Date");

        // Find the form view, set it up and initialize it.
        verticalStepperForm = findViewById(R.id.stepper_form);
        verticalStepperForm
                .setup(this, nameStep, studentNumberStep, birthDateStep)
                .init();
    }
    @Override
    public void onCompletedForm() {
        String info = "Student Name: "+nameStep.getStepDataAsHumanReadableString()+"\n";
        info+="Student number: "+studentNumberStep.getStepDataAsHumanReadableString()+"\n";
        info+="Student birth date: "+birthDateStep.getDay()+", "+birthDateStep.getMonth()+", "+birthDateStep.getYear();
        int duration = LENGTH_LONG;
        Toast toast = Toast.makeText(getApplicationContext(), info, duration);
        toast.show();
    }

    @Override
    public void onCancelledForm() {

    }
}

