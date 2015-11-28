package com.example.simen.bmicalculator;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;


public class MainActivity extends ActionBarActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText weightText = (EditText)findViewById(R.id.weightEdit);
        EditText heightText = (EditText)findViewById(R.id.heightEdit);

        final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        weightText.setText(prefs.getString("autoSave", null));
        heightText.setText(prefs.getString("autoSave2", null));

        weightText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                prefs.edit().putString("autoSave", s.toString()).commit();
            }
        });
        heightText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                prefs.edit().putString("autoSave2", s.toString()).commit();
            }
        });
    }

    public void calculateBMI(View view){
        if (view.getId() == R.id.calculateButton){
            EditText weightText = (EditText)findViewById(R.id.weightEdit);
            EditText heightText = (EditText)findViewById(R.id.heightEdit);
            TextView resultText = (TextView)findViewById(R.id.result);

            float weight = Float.parseFloat(weightText.getText().toString());
            float height = Float.parseFloat(heightText.getText().toString());


            float bmiValue = calculateBMI(weight, height);
            String s = String.format("%.1f", bmiValue);


            String bmiInterpretation = interpretBMI(bmiValue);

            resultText.setText(s + " - " + bmiInterpretation);
        }
    }

    private float calculateBMI(float weight, float height){
        return (float) ((weight/(height*height)*10000)); //multipliserer 10k for cm
    }

    private String interpretBMI(float bmiValue){
        String quiteUnderweight = getResources().getString(R.string.quiteUnderweight);
        String underweight = getResources().getString(R.string.underweight);
        String normal = getResources().getString(R.string.normal);
        String overweight = getResources().getString(R.string.overweight);
        String obese = getResources().getString(R.string.obese);

        if (bmiValue < 16){
            return quiteUnderweight;
        } else if (bmiValue < 18.5){
            return underweight;
        } else if (bmiValue < 25){
            return normal;
        } else if (bmiValue < 30){
            return overweight;
        } else {
            return obese;
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
