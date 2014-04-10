package com.barefoot.scrooge;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;


import java.io.IOException;

public class JuiceRecorder extends AsyncTask<String, Void, Boolean> {
  private static final String TAG = "JuiceRecorder";
  private static final String SERVICE_URL = "http://juice-app.herokuapp.com/employee/juice/comsumption";
  public static final int DEFAULT_JUICE_GLASS_QUANTITY = 1;
  private final Context context;

  public JuiceRecorder(Context context) {
    this.context = context;
  }

  @Override
  protected Boolean doInBackground(String... strings) {
    Boolean persistedToServer = true;
    String employeeId = strings[0];
    HttpClient client = new DefaultHttpClient();

      HttpPost post= new HttpPost(SERVICE_URL);
    try {
        StringEntity ent = new StringEntity("{\n" +
                "\"employee_id\":\""+employeeId+"\",\n" +
                "\"numberOfGlasses\":\""+DEFAULT_JUICE_GLASS_QUANTITY+"\",\n" +
                "\"date_time\":\""+System.currentTimeMillis()+"\"\n" +
                "\n" +
                "}");
      post.setEntity(ent);
      HttpResponse response = client.execute(post);
      StatusLine statusLine = response.getStatusLine();
      int statusCode = statusLine.getStatusCode();

      if (statusCode != 200) {
        persistedToServer = false;
        Log.e(TAG, "Somehow the entry was not recorded on the server!");
      }
    } catch (ClientProtocolException e) {
      e.printStackTrace();
    } catch (IOException e) {
      Log.e(TAG, e.getMessage());
    }
      return persistedToServer;
  }

  @Override
  protected void onPostExecute(Boolean persistedToServer) {
    String message = persistedToServer ? "Saved" : "Error occured, not saved!";
    Toast.makeText(this.context,message,Toast.LENGTH_LONG).show();
  }
}