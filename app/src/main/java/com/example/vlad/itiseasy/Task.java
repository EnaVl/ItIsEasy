package com.example.vlad.itiseasy;

import java.util.Random;

/**
 * Created by vlad on 29.10.16.
 */

public class Task {
    private int a;
    private int b;
    private int LVL;

    public Task(int LVL){
        this.LVL = LVL;
    }

    Random random = new Random();

    public String generateTask(){
        a = random.nextInt(LVL-1) + 1;
        b = random.nextInt(LVL-1) + 1;

        return a + " + " + b;

    }

    public int getResult(){
        return a+b;
    }

    public boolean checkResult(int smth){
        if(smth == this.getResult()){
            return true;
        } else {
            return false;
        }
    }
}
