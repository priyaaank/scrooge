package com.barefoot.scrooge;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.Timer;
import java.util.TimerTask;

public class Scanner extends Activity {

  private FortuneCookieMessageGenerator messageGenerator;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main);
    messageGenerator = FortuneCookieMessageGenerator.NewInstance(this.getApplicationContext());

    scanBarcode();
  }

  private void scanBarcode() {
      Handler handler = new Handler();
      handler.postDelayed(new Runnable() {
            public void run() {
                IntentIntegrator integrator = new IntentIntegrator(Scanner.this);
                integrator.initiateScan();
            }
      }, 5000);
  }

  public void onActivityResult(int requestCode, int resultCode, Intent intent) {
    IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent );
    if (resultCode == RESULT_OK && scanResult != null) {
      ((TextView)this.findViewById(R.id.user_name)).setText(scanResult.getContents());
      ((TextView)this.findViewById(R.id.fortune_message)).setText(messageGenerator.getRandomFortune());
        JuiceRecorder recorder =new JuiceRecorder(this);
        recorder.execute(scanResult.getContents());
        scanBarcode();
    } else {
        this.finish();
    }
  }

}
