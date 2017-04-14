package com.alorma.diary.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import butterknife.ButterKnife;
import com.alorma.diary.R;
import com.alorma.diary.di.component.ActivityComponent;
import com.alorma.diary.ui.fragment.AddDiaryFragment;

public class AddDiaryActivity extends BaseActivity {

  public static Intent createIntent(Context context) {
    return new Intent(context, AddDiaryActivity.class);
  }

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_add_diary);
    ButterKnife.bind(this);

    AddDiaryFragment fragment = AddDiaryFragment.newInstance();
    getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
  }

  @Override
  protected void injectActivity(ActivityComponent activityComponent) {
    activityComponent.inject(this);
  }
}
