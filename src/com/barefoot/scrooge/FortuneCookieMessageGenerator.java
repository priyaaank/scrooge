package com.barefoot.scrooge;

import android.content.Context;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class FortuneCookieMessageGenerator {

  private static final int MIN_NUMBER = 0;
  private static FortuneCookieMessageGenerator singleInstance;
  private final Context context;
  private List<String> fortunes;

  public static FortuneCookieMessageGenerator NewInstance(Context context) {
    if(singleInstance == null) {
      singleInstance = new FortuneCookieMessageGenerator(context);
    }
    return singleInstance;
  }

  private FortuneCookieMessageGenerator(Context context) {
    this.context = context;
    fortunes = Arrays.asList(this.context.getResources().getStringArray(R.array.fortune_list));
  }

  public String getRandomFortune() {
    return fortunes.get(randomNumber());
  }

  private int randomNumber() {
    Random rand = new Random();
    int randomNum = rand.nextInt((fortunes.size() - MIN_NUMBER) + 1) + MIN_NUMBER;
    return randomNum;
  }

}
