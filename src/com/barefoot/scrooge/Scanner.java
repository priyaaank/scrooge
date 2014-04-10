package com.barefoot.scrooge;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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
    new Timer().schedule(new TimerTask() {
      @Override
      public void run() {
        IntentIntegrator integrator = new IntentIntegrator(Scanner.this);
        integrator.initiateScan();
      }
    }, 5000);
  }

  public void onActivityResult(int requestCode, int resultCode, Intent intent) {
    IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
    if (scanResult != null) {
      ((TextView)this.findViewById(R.id.user_name)).setText(scanResult.getContents());
      ((TextView)this.findViewById(R.id.fortune_message)).setText(messageGenerator.getRandomFortune());

      new JuiceRecorder(this.getApplicationContext()).execute((new String[]{scanResult.getContents()}));
    }

    scanBarcode();
  }

}
