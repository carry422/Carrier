package com.car.career;

import android.os.AsyncTask;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.ArrayList;


public class FuelActivity extends AsyncTask<Long, Void,ArrayList<Double> > {
    private final String serverAddress="https://www.tppd.com.tr/tr/akaryakit-fiyatlari?id=";

    @Override
    public ArrayList<Double> doInBackground(Long... plaka) {
        ArrayList<Double> list=new ArrayList<Double>();
        try {
            int counter=0;

            Document Doc= Jsoup.connect(serverAddress+plaka[0]).get();
            Element a=null;
            if (plaka[0]!=34)
            for (Element e: Doc.select("table tr:eq(0)") ) {
                a=e;
            }
            else{
                for (Element e: Doc.select("table tr:eq(2)") ) {
                    a=e;
                }
            }
            Log.d("MyLocation",a.text());
            String commaChanger=a.text().replaceAll(",",".");
            String [] words = commaChanger.split(" ", 9);
            String cityname;

            for (String word : words) {
                if (counter++==0){
                    cityname=word;
                }else{
                list.add(Double.parseDouble(word));
            }
            }
        } catch (IOException e) {

        }
        return list;
    }

}
