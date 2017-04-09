package com.alorma.diary.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class AndroidPreferenceWrapper implements PreferenceWrapper {
  private SharedPreferences preferences;

  public AndroidPreferenceWrapper(Context context) {
    this.preferences = PreferenceManager.getDefaultSharedPreferences(context);
  }

  @Override
  public boolean getBoolean(String key, boolean defValue) {
    return preferences.getBoolean(key, defValue);
  }

  @Override
  public int getInteger(String key, int defValue) {
    return preferences.getInt(key, defValue);
  }

  @Override
  public String getBoolean(String key, String defValue) {
    return preferences.getString(key, defValue);
  }
}
