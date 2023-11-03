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

    public void setValues(int bound ,int lower, int upper){
        setValue(bound);
        setCoeficient(lower, upper);
        setTotal();
        setRep();
    }
    public void setValues2(int value, int lower, int upper){
        setValue2(value);
        setCoeficient(lower, upper);
        setTotal();
        setRep();
    }

    private void setValue(int bound) {
        this.value = random.nextInt(bound);
    }
    private void setValue2(int value) {
        this.value = value;
    }

    private void setCoeficient(int lower, int upper) {
        this.coeficient = random.nextInt(upper + lower + 1) - lower;
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
