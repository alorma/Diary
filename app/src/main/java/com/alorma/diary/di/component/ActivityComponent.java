package com.alorma.diary.di.component;

import com.alorma.diary.ui.activity.AddDiaryActivity;
import com.alorma.diary.ui.activity.DiaryDetailActivity;
import com.alorma.diary.ui.activity.MainActivity;
import com.alorma.diary.di.module.ActivityModule;
import com.alorma.diary.di.qualifiers.PerActivity;
import dagger.Component;

@PerActivity
@Component(dependencies = {ApplicationComponent.class, DataComponent.class}, modules = ActivityModule.class)
public interface ActivityComponent {
  void inject(MainActivity mainActivity);
  void inject(AddDiaryActivity addDiaryActivity);
  void inject(DiaryDetailActivity diaryDetailActivity);
}