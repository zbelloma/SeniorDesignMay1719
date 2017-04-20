package com.example.zakk.seniordesignmay1719;

import android.util.Log;
import java.io.Serializable;

import java.util.Arrays;
import java.util.List;


/**
 * Created by Rupert on 2/6/2017.
 */

public class Data implements Serializable{
    public String data;
    public long time;
    public String id;
    public String USER_ID;
    public int numScans;
    public int integrationTime;
    //public int baselineMSB;
    //public int baselineLSB;
    public List pixels;
    //public double[] pixel_Intensity;
    //final double saturation_Level = 26080;

    public Data(String scan_data, String user_id, long time){
        this.data = scan_data;
        this.USER_ID = user_id;
        Log.i("USER_ID:", this.USER_ID);
        this.time = time;
        this.id = "" + this.time;
        this.pixels = output_to_pixels(this.data);
    }

    public Data() {
        //this.data = data;
        //this.time = time;
        this.id = "" + this.time;
        this.pixels = output_to_pixels(this.data);
    }

    private List output_to_pixels(String input){
        //input = input.replace("\n", "");
        if(input == null || input.equals("")){
            return null;
        }

        //int startIndex = "65535".indexOf(input);
        //Log.i("Start", " index: " + startIndex);

/*        if (startIndex == -1){
            Log.i("Index", "Scan was not received correctly");
       git pull     return null;
        }*/
        //input = input.substring(startIndex, input.length() - 1);

        //Log.i("Data", input);
        String[] output_Data = input.split(" ");
        Log.i("Data", "Data length: " + output_Data.length + "\nLast word: " + output_Data[output_Data.length -1]);

        String data_Mode = output_Data[1]; //0-WORDS (16-bit pixel values), 1-DWORDS (32-bit pixel values)
        this.numScans = Integer.parseInt(output_Data[2]); //Number of scans taken
        this.integrationTime = Integer.parseInt(output_Data[3]); //Time taken to obtain sample data
        //this.baselineMSB = Integer.parseInt(output_Data[4]);
        //this.baselineLSB = Integer.parseInt(output_Data[5]);


        Double pixel_Intensity[] = new Double[3678];
        Double averagePixels[] = new Double[651];
        int pixel_Index = 0, averageCount = 0;

        if(data_Mode.equals("0")){
            Log.e("Averaging array", "Reached here");
            Log.e("Length", "ArrayLength: " + output_Data.length);

            for(int i = 28; i < 3678; i++){

                pixel_Intensity[i-28] = Double.parseDouble(output_Data[i]);
                averageCount++;
                if(averageCount == 5 || averageCount == 16){
                    averagePixels[pixel_Index] = (pixel_Intensity[i-32]+pixel_Intensity[i-31]+pixel_Intensity[i-30]+pixel_Intensity[i-29]+pixel_Intensity[i-28])/5;
                    pixel_Index++;

                } else if(averageCount == 11 || averageCount == 22 || averageCount == 28){
                    averagePixels[pixel_Index] = (pixel_Intensity[i-33]+pixel_Intensity[i-32]+pixel_Intensity[i-31]+pixel_Intensity[i-30]+pixel_Intensity[i-29]+pixel_Intensity[i-28])/6;
                    pixel_Index++;

                    if(averageCount == 28){
                        averageCount = 0;
                    }
                }

            }
        } else {

            Log.i("WrongFormat", "Should not be sending back DWORDS");
        }

        //this.data = null;
        //Log.i("Data","Data String: " + this.data.toString());
        Log.i("Data", "Averaged Array: " + averagePixels.length);
        return Arrays.asList(averagePixels);
    }

    @Override
    public String toString(){
        return this.data;
    }

    //Returns the number of scans taken in one run
    public int getNumScans(){ return this.numScans; }

    //Returns the integration time of the run (How long the scan took to complete)
    public int getIntegrationTime(){return this.integrationTime;}

    //Returns the baseline value for the MSB
    //public int getBaselineMSB(){return this.baselineMSB;}

    //Returns the baseline value for the LSB
    //public int getBaselineLSB(){return this.baselineLSB;}

    //Returns the array of pixels received from the scan
    public List getPixels(){return this.pixels;}

    //Returns the time of the data objects creation
    public long getTime(){return this.time;}
}
