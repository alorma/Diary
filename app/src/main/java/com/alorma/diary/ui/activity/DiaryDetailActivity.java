package com.alorma.diary.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.alorma.diary.R;
import com.alorma.diary.data.model.DiaryListItemModel;
import com.alorma.diary.di.component.ActivityComponent;

public class DiaryDetailActivity extends BaseActivity {

  @BindView(R.id.text) TextView textView;

  public static Intent createIntent(Context context, DiaryListItemModel itemModel) {
    Intent intent = new Intent(context, DiaryDetailActivity.class);
    intent.putExtra(Extras.DIARY_ID, itemModel.getId());
    return intent;
  }

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_diary_detail);
    ButterKnife.bind(this);

    if (getIntent() == null || getIntent().getExtras() == null) {
      throw new RuntimeException("Should never happen: " + DiaryDetailActivity.class.getCanonicalName());
    }

    int diaryId = getIntent().getExtras().getInt(Extras.DIARY_ID);
    textView.setText("Diary ID: " + diaryId);
  }

  @Override
  protected void injectActivity(ActivityComponent activityComponent) {
    activityComponent.inject(this);
  }

  private static class Extras {
    public static final String DIARY_ID = "DIARY_ID";
  }
}
