<h1>Vertical Stepper Android</h1>
<p>This project is a simplified example on how to create vertical stepper in android project, with the ability to costumize steps</p>
</hr>
<h2>demo</h2>
<img src="https://github.com/sojoud98/VerticalStepper/blob/master/demo.gif" alt="demo">

</hr>
<h2>Usage</h2>
<h6>1. Add a refernce to the library in your Module file</h6>

Add the library to your project via Gradle:
<br>
<code>
dependencies {
    implementation 'com.ernestoyaquello.stepperform:vertical-stepper-form:2.2.2'
}
</code>
<br>
<br>
 Note: <code>Your minSdkVersion should be at least 20</code>
 <br>
 <hr>

<h6>2.Add The Form To Your Layout</h6>
<br>
Add the view VerticalStepperFormView to your layout using XML. For design purposes, it is recommended that you don't put anything else than this view in the layout of the screen that will contain the form:
<br>
<pre>
ernestoyaquello.com.verticalstepperform.VerticalStepperFormView
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:id="@+id/stepper_form"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    app:form_circle_background_color="@color/colorPrimary"
    app:form_next_button_background_color="@color/colorPrimary"
    app:form_next_button_pressed_background_color="@color/colorPrimaryDark"/

</pre>

<br>
<hr>
 <h6>3. Define Your Steps</h6>
 <br>each step is defined by a class that extends the step class.In each step you can define customized layout using any components you wish. <br>
Example:
<br>
<code>
package com.example.vertical_stepper_example;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import ernestoyaquello.com.verticalstepperform.Step;
public class NameStep extends Step<String> {

    private EditText userNameView;

    public NameStep(String stepTitle) {
        super(stepTitle);
    }
    @Override
    protected View createStepContentLayout() {
        // Here we generate the view that will be used by the library as the content of the step.
        // In this case we do it programmatically, but we could also do it by inflating an XML layout.
        userNameView = new EditText(getContext());
        userNameView.setSingleLine(true);
        userNameView.setHint("Your Name");
        userNameView.addTextChangedListener(new TextWatcher() {


            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Whenever the user updates the user name text, we update the state of the step.
                // The step will be marked as completed only if its data is valid, which will be
                // checked automatically by the form with a call to isStepDataValid().
                markAsCompletedOrUncompleted(true);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        return userNameView;
    }

    @Override
    protected IsDataValid isStepDataValid(String stepData) {
        // The step's data (i.e., the user name) will be considered valid only if it is longer than
        // three characters. In case it is not, we will display an error message for feedback.
        // In an optional step, you should implement this method to always return a valid value.
        boolean isNameValid = stepData.length() >= 1;
        String errorMessage = !isNameValid ? "1 character minimum" : "";

        return new IsDataValid(isNameValid, errorMessage);
    }

    @Override
    public String getStepData() {
        // We get the step's data from the value that the user has typed in the EditText view.
        Editable userName = userNameView.getText();
        return userName != null ? userName.toString() : "";
    }

    @Override
    public String getStepDataAsHumanReadableString() {
        // Because the step's data is already a human-readable string, we don't need to convert it.
        // However, we return "(Empty)" if the text is empty to avoid not having any text to display.
        // This string will be displayed in the subtitle of the step whenever the step gets closed.
        String userName = getStepData();
        return !userName.isEmpty() ? userName : "(Empty)";
    }

    @Override
    protected void onStepOpened(boolean animated) {
        // This will be called automatically whenever the step gets opened.
    }

    @Override
    protected void onStepClosed(boolean animated) {
        // This will be called automatically whenever the step gets closed.
    }

    @Override
    protected void onStepMarkedAsCompleted(boolean animated) {
        // This will be called automatically whenever the step is marked as completed.
    }

    @Override
    protected void onStepMarkedAsUncompleted(boolean animated) {
        // This will be called automatically whenever the step is marked as uncompleted.
    }

    @Override
    public void restoreStepData(String stepData) {
        // To restore the step after a configuration change, we restore the text of its EditText view.
        userNameView.setText(stepData);
    }
}
</code>
 
<br>
<hr>

<h6>4.Set Up The Form</h6>
<p>First make sure to implement StepperFormListener in the file where you want to use the vertical stepper</p>
<p>Create an instance of each step and give it a title. Then pass those steps in the Stepup methos for the stepper </p>
<code>
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

</code>

<hr>
<h1>Further details</h1>
For more information and details please check out the origin of the project :
<a href="https://github.com/ernestoyaquello/VerticalStepperForm/">original project</a>


