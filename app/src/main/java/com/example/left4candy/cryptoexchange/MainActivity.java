package com.example.left4candy.cryptoexchange;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Switch switch1;
    public double amount;
    public double result;
    private Spinner dropdownFrom;
    private Spinner dropdownTo;
    private Button conButt;
    DBHandler dbHandler;
    //private static String[] currencies = new String[]{"USD", "SEK", "Bitcoin", "Ethereum"};
    ArrayList<Currency> currencies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        conButt = findViewById(R.id.convertButton);

        dbHandler = new DBHandler(this, null, null, 1);     //SQLite database handler

        dropdownFrom = findViewById(R.id.spinnerFrom);
        dropdownTo = findViewById(R.id.spinnerTo);
        dropdownFrom.setOnItemSelectedListener(this);
        dropdownTo.setOnItemSelectedListener(this);
        updateList();   //Calls the updateList method which updates the list of currencies

    }

    //Function for handling conversion between the two currencies
    public void clickConvert(View view){
        double from = currencies.get(dropdownFrom.getSelectedItemPosition()).getValue();    //Fetches the value from whatever is chosen
        double to = currencies.get(dropdownTo.getSelectedItemPosition()).getValue();
        EditText amountField = (EditText) findViewById(R.id.amountField);
        TextView resultView = (TextView) findViewById(R.id.resultView);

        String amountString = amountField.getText().toString();     //Turns the amountField into a String
        if(!amountString.isEmpty()){
            amount = Integer.parseInt(amountField.getText().toString());    //Turns amountString into an amount Integer
            result = (to/from) * amount;
            DecimalFormat numberFormat = new DecimalFormat("#.0000");
            hideKeyboard(this);
            resultView.setText(numberFormat.format(result)+"");
        }
    }

    //Method for updating the local arraylist with whatever is in the database
    public void updateList(){
        currencies = dbHandler.getCurrencies(); //Fills a ArrayList<Currency> with the currencies from SQLite Database using the getCurrencies()-method inside of DBHAndler.class

        ArrayList<String> curr = new ArrayList<String>();

        for (Currency c : currencies) {
            curr.add(c.getName());
        }

        //Based on https://stackoverflow.com/a/13502567
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, curr);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dropdownFrom.setAdapter(adapter);
        dropdownTo.setAdapter(adapter);
    }

    public void showMenu(View view){
        Intent intent = new Intent(MainActivity.this, OptionsActivity.class);
        startActivity(intent);
    }


    //Based on https://stackoverflow.com/a/13502567
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id){
        switch(position){
            case 0:
                break;
            case 1:
                break;
            case 2:
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    //Copied from StackOverflow https://stackoverflow.com/a/17789187
    public static void hideKeyboard(MainActivity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(MainActivity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
