package com.example.zakk.seniordesignmay1719;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DisplayDBActivity extends AppCompatActivity {

    ListView lv;
    ArrayAdapter<String> arrAdapter;
    List<String> listViewData = new ArrayList<String>();
    List<String> tempViewData = new ArrayList<String>();
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference ref = database.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_db);
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference Dataref = database.getReference("/data");
        lv = (ListView)findViewById(R.id.dbLV);
        arrAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listViewData);
        lv.setAdapter(arrAdapter);
        Dataref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                tempViewData.clear();
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    Data d = postSnapshot.getValue(Data.class);
                    System.out.println(d.toString());
                    if (d.data != null && !listViewData.contains(d.id)) {
                        listViewData.add(d.id);
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

    public void setData(View view){
        long time = System.currentTimeMillis();
        Data d = new Data("testdata1234", time);
        ref.child("data").child(d.id).setValue(d);
    }

    public void goBack(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
