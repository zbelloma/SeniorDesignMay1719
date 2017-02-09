package com.example.zakk.seniordesignmay1719;

/**
 * Created by Rupert on 2/6/2017.
 */

public class Data {
    public String data;
    public long time;
    public String id;

    public Data(String data, long time){
        this.data = data;
        this.time = time;
        this.id = "" + this.time;
    }

    public Data(){

    }

    @Override
    public String toString(){
        return this.data;
    }
}
