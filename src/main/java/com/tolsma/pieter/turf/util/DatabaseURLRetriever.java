package com.tolsma.pieter.turf.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
/**
 * Created by pietertolsma on 7/4/17.
 */
public class DatabaseURLRetriever {

    private static final String URL = "http://turfsysteem.herokuapp.com/api/secret/W@t3X9993*3v0)Cv_";

    public static String retrieveURL() {
        try {
            return sendGet();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String sendGet() throws Exception {

        URL obj = new URL(URL);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        // optional default is GET
        con.setRequestMethod("GET");

        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'GET' request to URL : " + URL);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        return response.toString();

    }

}
