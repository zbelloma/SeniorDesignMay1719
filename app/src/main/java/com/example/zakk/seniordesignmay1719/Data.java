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


    public static String[] output_to_pixels(String input){
        //input = input.replace("\n", "");
        String[] output_Data = input.split(" ");

        String data_Mode = output_Data[2]; //0-WORDS (16-bit pixel values), 1-DWORDS (32-bit pixel values)
        //String scans = output_Data[3]; //Number of scans taken
        //String integration_Time = output_Data[4]; //Time taken to obtain sample data
        String pixels[] = new String[(output_Data.length-9)/2];
        Integer pixel_Index = 0;

        if(data_Mode.equals("0")){
            for(int i = 8; i < output_Data.length-2; i += 2){
                pixels[pixel_Index] = output_Data[i+1] + output_Data[i];
                pixel_Index++;
            }
        } else {
            for(int i = 8; i < output_Data.length; i+=4){
                pixels[pixel_Index] = output_Data[i+3] + output_Data[i+2] + output_Data[i+1] + output_Data[i];
                pixel_Index++;
            }
        }

        return pixels;
    }

    @Override
    public String toString(){
        return this.data;
    }
}
