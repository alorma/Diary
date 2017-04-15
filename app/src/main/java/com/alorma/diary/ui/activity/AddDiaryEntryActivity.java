package com.alorma.diary.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import butterknife.ButterKnife;
import com.alorma.diary.R;
import com.alorma.diary.di.component.ActivityComponent;
import com.alorma.diary.ui.fragment.AddDiaryEntryFragment;

public class AddDiaryEntryActivity extends BaseActivity {

  public static Intent createIntent(Context context, int id) {
    Intent intent = new Intent(context, AddDiaryEntryActivity.class);
    intent.putExtra(Extras.DIARY_ID, id);
    return intent;
  }

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_add_diary);
    ButterKnife.bind(this);

    if (getIntent() == null || getIntent().getExtras() == null) {
      throw new RuntimeException("Should never happen: " + DiaryDetailActivity.class.getCanonicalName());
    }

    int diaryId = getIntent().getExtras().getInt(Extras.DIARY_ID);
    AddDiaryEntryFragment fragment = AddDiaryEntryFragment.newInstance(diaryId);
    getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
  }

  @Override
  protected void injectActivity(ActivityComponent activityComponent) {
    activityComponent.inject(this);
  }

  private static class Extras {
    public static final String DIARY_ID = "DIARY_ID";
  }
}
