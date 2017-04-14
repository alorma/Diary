package com.alorma.diary.ui.activity;

import android.os.Bundle;
import butterknife.ButterKnife;
import com.alorma.diary.R;
import com.alorma.diary.di.component.ActivityComponent;
import com.alorma.diary.ui.fragment.DiaryListFragment;

public class MainActivity extends BaseActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    ButterKnife.bind(this);

    DiaryListFragment fragment = DiaryListFragment.newInstance();
    getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
  }

  @Override
  protected void injectActivity(ActivityComponent activityComponent) {
    activityComponent.inject(this);
  }
}
