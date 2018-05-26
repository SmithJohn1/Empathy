package com.example.smithjohn.empathy;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView textView; //the displayed text
    EditText editText; //the text field
    Button button; //the actual button

    String result; //stores either positive, negative, or neutral result

    //generates the result in the background. Takes in String params, returns nothing during execution, returns String at end of execution
    private class GenerateResult extends AsyncTask<String, Void, String> {

        @Override
        //overrides the doInBackground to take 1 string "reviewInput" to analyze
        protected String doInBackground(String... reviewInput){

            System.out.println(editText.getText()); //prints out the value of editText, the text field, to system console for debugging purposes

            //runs on the UI thread (i.e., multitasking), requires Runnable class
            runOnUiThread(new Runnable() {
                //overrides default run() method
                @Override
                public void run(){
                    //do AI processing here
                    //decision logic here as well
                    result = Sentiment.rating(Sentiment.word(editText.getText().toString()));
                }
            });

            System.out.println(result); //for debugging, checks the value of result in console

            return result;
        }

        //complete the intermediate steps, so overrides onPostExecute to deliver the result
        @Override
        protected void onPostExecute(String result){
            //sets the textView, the displayed text, to the results
            textView.setText("This review is " + result);
        }
    }

    //what happens when you first start the app
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //creates an empty activity
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initialize the UI objects, uses redundant casting
        textView = (TextView) findViewById(R.id.textView); //assigns textView, the variable in the .java file, the id found XML
        editText = (EditText) findViewById(R.id.editText);
        button = (Button) findViewById(R.id.button);

        //determine procedure when button is tapped
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v){
                //could include debugging code here

                //create new instance of GenerateResult, and run the execute() from AsyncTask
                GenerateResult generateResult = new GenerateResult();
                generateResult.execute(new String[]{});
            }
        });
    }
}
