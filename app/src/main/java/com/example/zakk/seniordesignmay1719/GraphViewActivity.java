package com.example.zakk.seniordesignmay1719;

import android.app.Activity;
import android.graphics.*;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.androidplot.util.PixelUtils;
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.XYSeries;
import com.androidplot.xy.*;

import java.io.Serializable;
import java.text.FieldPosition;
import java.text.Format;
import java.text.ParsePosition;
import java.util.*;

public class GraphViewActivity extends Activity{

    private XYPlot plot;
//    Data dataObj = (Data)getIntent().getSerializableExtra("Data");

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph_view);

        //get extra
        ArrayList<Double> listDouble = (ArrayList<Double>) getIntent().getSerializableExtra("Data");
        if(listDouble == null){
            Log.e("ERROR", "no pixel data");
            return;
        }
        //Log.e("TEST", listDouble.toString());

        final Number[] arr = new Number[listDouble.size()];
        for(int i = 0; i < listDouble.size(); i++){
            arr[i] = listDouble.get(i);
        }

        final Number[] xAxisVals = new Number[arr.length];
        xAxisVals[0] = 350;
        for(int i = 1; i < xAxisVals.length; i++){
            Double temp = xAxisVals[i-1].doubleValue() + 1;
            xAxisVals[i] = temp;
            Log.e("TEST", xAxisVals[i].toString());
        }

        // initialize our XYPlot reference:
        plot = (XYPlot) findViewById(R.id.plot);

        // create a couple arrays of y-values to plot:
        final Number[] domainLabels = {150, 200, 250, 300, 350, 400, 450, 500, 550, 600, 650, 700, 750, 800, 850, 900, 950, 1000, 1050, 1100,30000};
        Number[] series1Numbers = {1, 4, 2, 8, 4, 16, 500, 1000, 2000, 3000};

        // turn the above arrays into XYSeries':
        // (Y_VALS_ONLY means use the element index as the x value)
        XYSeries series1 = new SimpleXYSeries(
                Arrays.asList(xAxisVals), Arrays.asList(arr), "Series1");

        // create formatters to use for drawing a series using LineAndPointRenderer
        // and configure them from xml:
        LineAndPointFormatter series1Format =
                new LineAndPointFormatter(this, R.xml.line_point_formatter_with_labels);

        series1Format.setPointLabelFormatter(null);
        series1Format.setVertexPaint(null);
        //plot.setLinesPerRangeLabel(3);
        
        plot.setLinesPerDomainLabel(10);
        //plot.setRangeBoundaries(0, 1000, BoundaryMode.FIXED);
        //plot.setDomainBoundaries(350, 1000, BoundaryMode.FIXED);
        PanZoom.attach(plot);

        // add a new series' to the xyplot:
        plot.addSeries(series1, series1Format);

        plot.getGraph().getLineLabelStyle(XYGraphWidget.Edge.BOTTOM).setFormat(new Format() {
            @Override
            public StringBuffer format(Object obj, StringBuffer toAppendTo, FieldPosition pos) {
                double i = Math.round(((Number) obj).floatValue());
                int a = (int) i;

                return toAppendTo.append(a);
            }
            @Override
            public Object parseObject(String source, ParsePosition pos) {
                return null;
            }
        });
    }


}
