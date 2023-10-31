package com.example.learnv1;

public class Result
{
    private int value;
    private String rep;

    public void setValues(int value, String rep){
        setValue(value);
        setRep(rep);
    }
    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getRep() {
        return rep;
    }

    public void setRep(String rep) {
        this.rep = rep;
    }
}
