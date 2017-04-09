package com.alorma.diary;

import android.os.Bundle;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.alorma.diary.di.component.ActivityComponent;
import com.alorma.diary.di.qualifiers.AppInfo;
import com.alorma.diary.di.qualifiers.ComputationScheduler;
import com.alorma.diary.ui.activity.BaseActivity;
import io.reactivex.Scheduler;
import javax.inject.Inject;

public class MainActivity extends BaseActivity {

  @Inject
  @AppInfo
  String appPackage;

  @Inject
  @ComputationScheduler Scheduler scheduler;

  @BindView(R.id.text) TextView textView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    ButterKnife.bind(this);

    getActivityComponent().inject(this);

    textView.setText("Net: " + scheduler);
  }

  @Override
  protected void injectActivity(ActivityComponent activityComponent) {
    activityComponent.inject(this);
  }
}
