package com.example.left4candy.cryptoexchange;

/**
 * Created by Left4Candy on 2018-02-11.
 */

public class Currency {

    int _id;
    String _name;
    double _value;
    String _type;

    public Currency(){
        this(0, "" );
    }

    public Currency(String name){
        this._name = _name;
    }

    public Currency(double value, String name){
        this._value = value;
        this._name = name;
    }

    public Currency(double value, String name, int id){
        this._value = value;
        this._name = name;
        this._id = id;
    }

    /*____________________________Getters and Setters for value and name_________________________________*/

    public double getValue() {
        return _value;
    }

    public void setValue(double value) {
        this._value = value;
    }

    public String getName() {
        return _name;
    }

    public void setName(String name) {
        this._name = name;
    }

    public String getType() {
        return _type;
    }

    public void setType(String type) {
        this._type = type;
    }

    public int getId(){
        return _id;
    }

    public void setId(int id){
        this._id = id;
    }

}
