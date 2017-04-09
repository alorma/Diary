package com.alorma.diary.di.component;

import com.alorma.diary.MainActivity;
import com.alorma.diary.di.module.ActivityModule;
import com.alorma.diary.di.qualifiers.PerActivity;
import dagger.Component;

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {
  void inject(MainActivity mainActivity);
}