package com.example.left4candy.cryptoexchange;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class OptionsActivity extends AppCompatActivity {

    String lang;
    EditText nameText;
    String nameInput;
    double valueInput;
    EditText valueText;
    CheckBox typeCrypto;
    CheckBox typeFiat;
    Crypto c;
    Fiat f;
    ListView currencyList;

    DBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        nameText = (EditText) findViewById(R.id.nameText);
        valueText = (EditText) findViewById(R.id.valueText);
        typeCrypto = findViewById(R.id.cryptoCheck);
        typeFiat = findViewById(R.id.fiatCheck);
        currencyList = findViewById(R.id.currencyList);
        dbHandler = new DBHandler(this, null, null, 1);
        printDatabase();
    }

    public void printDatabase(){
        ArrayList<Currency> currencies = dbHandler.getCurrencies();
        ArrayList<String> curr = new ArrayList<String>();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, curr);

        for(int i = 0; i < currencies.size(); i++){
            curr.add("#" + currencies.get(i).getId() + "\t\t\t" + currencies.get(i).getName() + "\t\t\t\t " + currencies.get(i).getValue() + "\t\t\t " + currencies.get(i).getType());
        }
        currencyList.setAdapter(adapter);
    }

    public void addButtonClicked(View view){

        if(nameText.getText().length() == 0){
            System.out.println("no name");
            return;
        }
        nameInput = nameText.getText().toString();

        if(valueText.getText().length() == 0){
            System.out.println("no value");
            return;
        }
        double valueInput = Double.parseDouble(valueText.getText().toString());

        if(typeFiat.isChecked() && typeCrypto.isChecked()){
            System.out.println("Both types are checked");
            return;
        }else if(typeCrypto.isChecked()){
            Crypto c = new Crypto(valueInput, nameInput);
            dbHandler.addCurrency(c);
        }else if(typeFiat.isChecked()) {
            Fiat f = new Fiat(valueInput, nameInput);
            dbHandler.addCurrency(f);
        }else{
            System.out.println("No types are checked");
            return;
        }
        printDatabase();
    }

    public void deleteButtonClicked(View view){
        if(valueText.length() == 0){
            System.out.println("need delete value");
            return;
        }
        int valueInput = Integer.parseInt(valueText.getText().toString());
        dbHandler.deleteCurrency(valueInput);
        printDatabase();
    }

    public void goMain(View view){
        Intent intent = new Intent(OptionsActivity.this, MainActivity.class);
        startActivity(intent);
    }
}
