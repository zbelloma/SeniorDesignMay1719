package com.example.zakk.seniordesignmay1719;

import android.app.Activity;
import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;
import android.provider.SyncStateContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView;
import java.io.Serializable;


import com.google.firebase.*;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static java.lang.Double.valueOf;

public class DisplayDBActivity extends Activity implements Serializable {

    ListView lv;
    ArrayAdapter<String> arrAdapter;
    List<String> listViewData = new ArrayList<String>();
    List<String> tempViewData = new ArrayList<String>();
    ArrayList<Integer> peaks = new ArrayList<>();
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference ref = database.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        Log.e("USER", user.getUid().toString());

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_db);
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference Dataref = database.getReference("/data");
        lv = (ListView)findViewById(R.id.dbLV);
        arrAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listViewData);
        lv.setAdapter(arrAdapter);

        lv.setOnItemClickListener( new OnItemClickListener()
        {

            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position,
                                    long id)
            {
                Log.e("TEST", "Item clicked: " + position);
                String fetched1 = parent.getItemAtPosition(position).toString();
                Log.e("TEST", fetched1);

                long time = stringToTime(fetched1);
                final String test4 = Objects.toString(time, null);
                Log.e("TEST", test4);
                Log.e("PeaksInital", peaks.toString());
                final String fetched = test4;//parent.getItemAtPosition(position).toString();
                //getAndDisplayData(fetched);
                ///fetched = "1487866379226"; //temp
                Dataref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        DataSnapshot data = dataSnapshot.child(fetched);
                       // Log.e("VALUE", data.getValue().toString());
                        ArrayList<Double> valueToPass = (ArrayList<Double>) data.child("pixels").getValue();

                        List<Object> p = (List<Object>) data.child("pixels").getValue();
                        if(p != null) {
                            peaks.clear();
                            peaks = calcPeaks(p);
                            //double t = a.get(50);
                            Log.e("PEAKS", p.toString());

                            //Log.e("DATA", data.pixels.toString());
                            //Log.e("ID", data.id);
                        }

                        //Log.e("TEST", valueToPass.toString());
                        //Log.e("Actual0", peaks.get(0).toString());
                        getAndDisplayData(valueToPass, peaks);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        } );

        Dataref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.e("TEST", "IN on DataChage");
                tempViewData.clear();
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren() ) {
                    Data data = new Data();
                    data.id = (String) postSnapshot.child("id").getValue();
                    data.time = (long) postSnapshot.child("time").getValue();

                    String temp = convertTime(data.time);
                    String s = Objects.toString(temp, null);
                    Log.e("Time", s);

                    data.pixels = (List<Double>) postSnapshot.child("pixels").getValue();
                    if(data.pixels!=null) {

                        peaks = calcPeaks(data.pixels);
                        //double t = a.get(50);
                        Log.e("PEAKS", peaks.toString());

                        //Log.e("DATA", data.pixels.toString());
                        //Log.e("ID", data.id);
                    }
  //                  System.out.println(data.id);
                    if (data.pixels != null && !listViewData.contains(data.id)) {
                        listViewData.add(convertTime(data.time));
                        //listViewData.add(data.id);
                        //listViewData.add(Objects.toString(data.time,null));

                        Log.e("LIST", "added");
                    }
                }
                arrAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("Read failed: " + databaseError.getCode());
            }

        });

    }


    public void getAndDisplayData(ArrayList<Double> value, ArrayList<Integer> peak){
        if(value != null) {
            Intent intent = new Intent(this, GraphViewActivity.class);
            intent.putExtra("Data", value);
            if(peak.size() != 0){
                Log.e("PeakToAdd", peak.get(0).toString());
                intent.putIntegerArrayListExtra("Peaks", peak);
            }

            startActivity(intent);
        }
        else{Log.e("NULL", "Value to pass is null");}
    }

/*    public void setData(View view){
        long time = System.currentTimeMillis();
        Data d = new Data("testdata1234", "USER_ID", time);
        ref.child("data").child(d.id).setValue(d);
    }*/

    public void goBack(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public String convertTime(long time){
        Date date = new Date(time);
        Format format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss.SSS");
        return format.format(date);
    }


    public long stringToTime(String time){
        DateFormat date = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss.SSS");
        Date newDate = null;
        try {
            newDate = date.parse(time);
        }
        catch (ParseException e){
            e.printStackTrace();
        }

        return newDate.getTime();
    }

    /*
    calculates the number of peaks and the wavelength they happen at
    it works by finding the local max for every 50nm, may expand by adding and avg too
     */
    ArrayList<Integer> calcPeaks(List<Object> arr){
        ArrayList<Integer> wavelengths = new ArrayList<>();
        double minIntensity = 1700;
        double localMax = 0;
        int position = 0;
        int range = 50;

        //System.out.println();

        for(int i = 0; i < arr.size()/range; i++){
            for(int j = 0; j < range; j++){
                int pos = (i * range) + j;
                double a =  valueOf((arr.get(pos).toString()));
                if(a > localMax){
                    localMax = a;
                    position = pos + 350;
                }
            }
            if(localMax > minIntensity){
                wavelengths.add(position);
            }
            localMax = 0;
            position = 0;
        }

        return wavelengths;
    }

}
