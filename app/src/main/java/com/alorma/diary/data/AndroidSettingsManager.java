package com.alorma.diary.data;

public class AndroidSettingsManager implements SettingsManager {

  private PreferenceWrapper preferenceWrapper;

  public AndroidSettingsManager(PreferenceWrapper preferenceWrapper) {
    this.preferenceWrapper = preferenceWrapper;
  }

  @Override
  public ModeType getThemeMode() {
    return ModeType.DAY_NIGT;
  }
}
