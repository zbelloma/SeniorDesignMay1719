package com.example.zakk.seniordesignmay1719;

import android.util.Log;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Rupert on 2/6/2017.
 */

public class Data {
    public String data;
    public long time;
    public String id;
    public int numScans;
    public int integrationTime;
    public int baselineMSB;
    public int baselineLSB;
    public List pixels;
    public double[] pixel_Intensity;
    final double saturation_Level = 2608;

    public Data(String data, long time){
        this.data = data;
        this.time = time;
        this.id = "" + this.time;
        this.pixels = output_to_pixels(this.data);
    }

/*    public Data(){
        //this.data = data;
        //this.time = time;
        this.id = "" + this.time;
        this.pixels = output_to_pixels(this.data);
    }*/

    private List output_to_pixels(String input){
        //input = input.replace("\n", "");
        if(input == null || input.equals("")){
            return null;
        }
        int start = input.indexOf("65535");
        input = input.substring(start, input.length() -1);
        Log.i("Data", input);
        String[] output_Data = input.split(" ");

        String data_Mode = output_Data[1]; //0-WORDS (16-bit pixel values), 1-DWORDS (32-bit pixel values)
        this.numScans = Integer.parseInt(output_Data[2]); //Number of scans taken
        this.integrationTime = Integer.parseInt(output_Data[3]); //Time taken to obtain sample data
        //this.baselineMSB = Integer.parseInt(output_Data[4]);
        //this.baselineLSB = Integer.parseInt(output_Data[5]);

        String parse_Pixels[] = new String[(output_Data.length) - 8];
        //this.pixel_Intensity = new Double[(output_Data.length-9)/2];
        Integer pixel_Index = 0;

        if(data_Mode.equals("0")){
            for(int i = 8; i < output_Data.length-2; i++){
                parse_Pixels[i-8] = output_Data[i];
                //pixel_Intensity[pixel_Index] = (65535.0/saturation_Level) * Double.parseDouble(parse_Pixels[pixel_Index]);
                //pixel_Index++;
            }
        } else {
            /* for(int i = 7; i < output_Data.length; i++){
                parse_Pixels[i-7] = output_Data[i+3] + output_Data[i+2] + output_Data[i+1] + output_Data[i];
                //pixel_Intensity[pixel_Index] = (65535.0/saturation_Level) * Double.parseDouble(parse_Pixels[pixel_Index]);
                pixel_Index++;
            }*/
            Log.i("as;df", "Should not be sending back DWORDS");
        }

        return Arrays.asList(parse_Pixels);
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
    public int getBaselineMSB(){return this.baselineMSB;}

    //Returns the baseline value for the LSB
    public int getBaselineLSB(){return this.baselineLSB;}

    //Returns the array of pixels received from the scan
    public List getPixels(){return this.pixels;}

    //Returns the time of the data objects creation
    public long getTime(){return this.time;}
}
