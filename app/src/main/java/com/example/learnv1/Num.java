package com.example.learnv1;
import java.util.Random;

public class Num
{
    private int value, coeficient, total;
    private String rep;
    final private String var;
    final private Random random = new Random();

    public Num(String var){
        this.var = var;
    }

    public void setValues(){
        setValue();
        setCoeficient();
        setTotal();
        setRep();
    }
    public void setValues(int value){
        setValue(value);
        setCoeficient();
        setTotal();
        setRep();
    }

    private void setValue() {
        this.value = random.nextInt(11);
    }
    private void setValue(int value) { this.value = value; }

    private void setCoeficient() {
        this.coeficient = random.nextInt(21) - 10;
    }

    public int getCoeficient() { return coeficient; }

    public int getValue() { return value; }

    public int getTotal() {
        return total;
    }

    private void setTotal() {
        this.total = coeficient * value;
    }

    public String getRep() {
        return rep;
    }

    private void setRep() {
        if(coeficient < 0){
            this.rep = "- " + Math.abs(coeficient) + var + " ";
        }else{
            this.rep = "+ " + coeficient + var + " ";
        }
    }

}
