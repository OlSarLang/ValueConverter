package com.example.left4candy.cryptoexchange;

/**
 * Created by Left4Candy on 2018-02-11.
 */

public class Crypto extends Currency {

    public Crypto(){
        this._value = 0;
        this._name = "";
        this._type = "Crypto";
    }

    public Crypto(String name){
        this._value = 0;
        this._name = name;
        this._type = "Crypto";
    }

    public Crypto(double value, String name){
        this._value = value;
        this._name = name;
        this._type = "Crypto";
    }
    public Crypto(double value, String name, int id){
        this._value = value;
        this._name = name;
        this._type = "Crypto";
        this._id = id;
    }
}
