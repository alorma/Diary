package com.alorma.diary.data;

public interface PreferenceWrapper {
  boolean getBoolean(String key, boolean defValue);

  int getInteger(String key, int defValue);

  String getBoolean(String key, String defValue);
}
