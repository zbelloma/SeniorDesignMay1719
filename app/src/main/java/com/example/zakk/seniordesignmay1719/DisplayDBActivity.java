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
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DisplayDBActivity extends Activity implements Serializable {

    ListView lv;
    ArrayAdapter<String> arrAdapter;
    List<String> listViewData = new ArrayList<String>();
    List<String> tempViewData = new ArrayList<String>();
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
                final String fetched = parent.getItemAtPosition(position).toString();
                Log.e("TEST", fetched);
                //getAndDisplayData(fetched);
                ///fetched = "1487866379226"; //temp
                Dataref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        DataSnapshot data = dataSnapshot.child(fetched);
                       // Log.e("VALUE", data.getValue().toString());
                        ArrayList<Double> valueToPass = (ArrayList<Double>) data.child("pixels").getValue();
                        //Log.e("TEST", valueToPass.toString());
                        getAndDisplayData(valueToPass);
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
                    //Log.e("ID", data.id);
                    data.pixels = (List<Double>) postSnapshot.child("pixels").getValue();
                    if(data.pixels!=null) {
                        Log.e("DATA", data.pixels.toString());
                        Log.e("ID", data.id);
                    }
  //                  System.out.println(data.id);
                    if (data.pixels != null && !listViewData.contains(data.id)) {
                        //listViewData.add(convertTime(data.time));
                        listViewData.add(data.id);
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


    public void getAndDisplayData(ArrayList<Double> value){
        if(value != null) {
            Intent intent = new Intent(this, GraphViewActivity.class);
            intent.putExtra("Data", value);
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
        Format format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        return format.format(date);
    }
}
