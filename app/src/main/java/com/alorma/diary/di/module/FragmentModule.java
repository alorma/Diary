package com.alorma.diary.di.module;

import android.support.v4.app.Fragment;
import dagger.Module;

@Module
public class FragmentModule {
  private Fragment fragment;

  public FragmentModule(Fragment fragment) {
    this.fragment = fragment;
  }
}
