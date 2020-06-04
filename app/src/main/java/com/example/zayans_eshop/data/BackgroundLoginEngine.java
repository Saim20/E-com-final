package com.example.zayans_eshop.data;

import android.app.Activity;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.zayans_eshop.MainActivity;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class BackgroundLoginEngine extends AsyncTask<String, Void, String> {
    private Activity context;
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected String doInBackground(String... strings) {
        String retrievedData = "";
        String registerUrl = "https://zayansshop.000webhostapp.com/login.php";
        URL url;
        try {
            url = new URL(registerUrl);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            OutputStream outputStream = httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, StandardCharsets.UTF_8));

            String data = URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(strings[0], "UTF-8") + "&" +
                    URLEncoder.encode("userpass", "UTF-8") + "=" + URLEncoder.encode(strings[1], "UTF-8");
            bufferedWriter.write(data);
            bufferedWriter.flush();
            bufferedWriter.close();
            outputStream.close();

            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.ISO_8859_1));
            String productDataReader;

            while ((productDataReader = bufferedReader.readLine()) != null) {
                //noinspection StringConcatenationInLoop
                retrievedData += productDataReader;
            }

            bufferedReader.close();
            inputStream.close();
            httpURLConnection.disconnect();
        } catch (IOException e) {
          //  e.printStackTrace();
           return "sorry for the server crash";
        }

        return retrievedData;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if (!s.equals("Failed")) {
            try {
                JSONArray obj = new JSONArray(s);

                MainActivity.userAccount = new UserAccount(
                        obj.getString(0),
                        obj.getString(1),
                        obj.getString(2),
                        obj.getString(3),
                        obj.getString(4));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            // TODO: Update UI on Success
        }
        else if(s.equalsIgnoreCase("sorry for the server crash")){
            Toast.makeText(context,
                    "sorry for the server crash",
                    Toast.LENGTH_LONG).show();
        }
        else {
            Toast.makeText(context,
                    "incorrect username or password",
                    Toast.LENGTH_LONG).show();
        }
    }
}
