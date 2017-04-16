package com.alorma.diary.di.module;

import android.content.Context;
import android.support.v4.app.Fragment;
import com.afollestad.inquiry.Inquiry;
import com.alorma.diary.ResourceLifeCycle;
import com.alorma.diary.di.qualifiers.ActivityContext;
import com.alorma.diary.di.qualifiers.DatabaseName;
import dagger.Module;
import dagger.Provides;

@Module
public class FragmentModule {
  private Fragment fragment;

  public FragmentModule(Fragment fragment) {
    this.fragment = fragment;
  }

  @Provides
  @ActivityContext
  Context provideContext() {
    return fragment.getContext();
  }

  @Provides
  Inquiry getInquiry(@ActivityContext Context context, @DatabaseName String dbName) {
    return Inquiry.newInstance(context, dbName).instanceName(fragment.getClass().getSimpleName()).build();
  }

  @Provides
  ResourceLifeCycle getCleanupResource(Inquiry inquiry) {
    return inquiry::destroyInstance;
  }
}
