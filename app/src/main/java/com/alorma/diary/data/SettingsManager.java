package com.alorma.diary.data;

public interface SettingsManager {
    public enum ModeType {
      DAY,
      NIGHT,
      DAY_NIGT
    }

    ModeType getThemeMode();
}
