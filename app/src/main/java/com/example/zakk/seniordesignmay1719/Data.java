package com.example.zakk.seniordesignmay1719;

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
    public String[] pixels;
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

    private String[] output_to_pixels(String input){
        //input = input.replace("\n", "");
        if(input == null || input.equals("")){
            return null;
        }
        String[] output_Data = input.split(" ");

        String data_Mode = output_Data[2]; //0-WORDS (16-bit pixel values), 1-DWORDS (32-bit pixel values)
        this.numScans = Integer.parseInt(output_Data[3]); //Number of scans taken
        this.integrationTime = Integer.parseInt(output_Data[4]); //Time taken to obtain sample data
        this.baselineMSB = Integer.parseInt(output_Data[5]);
        this.baselineLSB = Integer.parseInt(output_Data[6]);

        String parse_Pixels[] = new String[(output_Data.length-9)/2];
        //this.pixel_Intensity = new Double[(output_Data.length-9)/2];
        Integer pixel_Index = 0;

        if(data_Mode.equals("0")){
            for(int i = 8; i < output_Data.length-2; i += 2){
                parse_Pixels[pixel_Index] = output_Data[i+1] + output_Data[i];
                //pixel_Intensity[pixel_Index] = (65535.0/saturation_Level) * Double.parseDouble(parse_Pixels[pixel_Index]);
                pixel_Index++;
            }
        } else {
            for(int i = 8; i < output_Data.length; i+=4){
                parse_Pixels[pixel_Index] = output_Data[i+3] + output_Data[i+2] + output_Data[i+1] + output_Data[i];
                //pixel_Intensity[pixel_Index] = (65535.0/saturation_Level) * Double.parseDouble(parse_Pixels[pixel_Index]);
                pixel_Index++;
            }
        }

        return parse_Pixels;
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
    public String[] getPixels(){return this.pixels;}
}
