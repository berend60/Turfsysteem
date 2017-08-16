package com.tolsma.pieter.turf.database;

import com.tolsma.pieter.turf.items.Transaction;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by pietertolsma on 8/16/17.
 */
public class APIHelper {

    private static APIHelper instance = new APIHelper();

    public APIHelper() {

    }

    public static APIHelper getInstance() {
        return instance;
    }

    public boolean postTransaction(Transaction t) throws Exception{
        String url = "https://turfsysteem.herokuapp.com/api/v1.0/make_transaction";

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

        System.out.println(t.getParticipantsIDString());

        String urlParams = "item_identifier=" + t.getItem().getId() + "&participants=" + t.getParticipantsIDString() + "&total_amount=" + t.getCount();
        con.setDoOutput(true);

        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(urlParams);
        wr.flush();
        wr.close();
        int responseCode = con.getResponseCode();

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        System.out.println(response.toString());


        return true;
    }



}
