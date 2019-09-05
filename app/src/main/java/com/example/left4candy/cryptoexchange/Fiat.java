package com.example.left4candy.cryptoexchange;

/**
 * Created by Left4Candy on 2018-02-11.
 */

public class Fiat extends Currency {

    public Fiat(){
        this._value = 0;
        this._name = "";
        this._type = "Fiat";
    }

    public Fiat(String name){
        this._value = 0;
        this._name = name;
        this._type = "Fiat";
    }

    public Fiat(double value, String name){
        this._value = value;
        this._name = name;
        this._type = "Fiat";
    }

    public Fiat(double value, String name, int id){
        this._value = value;
        this._name = name;
        this._type = "Fiat";
        this._id = id;
    }

}
