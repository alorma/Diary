package com.alorma.diary.data;

public interface SettingsManager {
  ModeType getThemeMode();

  public enum ModeType {
    DAY,
    NIGHT,
    DAY_NIGT
  }
}
