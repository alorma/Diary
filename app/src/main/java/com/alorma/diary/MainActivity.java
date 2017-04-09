package com.alorma.diary;

import android.os.Bundle;
import android.widget.Toast;
import com.alorma.diary.di.component.ActivityComponent;
import com.alorma.diary.di.qualifiers.AppInfo;
import com.alorma.diary.ui.activity.BaseActivity;
import javax.inject.Inject;

public class MainActivity extends BaseActivity {

  @Inject
  @AppInfo
  String appPackage;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    getActivityComponent().inject(this);

    Toast.makeText(this, appPackage, Toast.LENGTH_SHORT).show();
  }

  @Override
  protected void injectActivity(ActivityComponent activityComponent) {
    activityComponent.inject(this);
  }
}
