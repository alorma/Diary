package com.alorma.diary.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.alorma.diary.R;
import com.alorma.diary.data.model.DiaryItemModel;
import com.alorma.diary.data.model.EntryItemModel;
import com.alorma.diary.di.component.ActivityComponent;
import com.alorma.diary.ui.presenter.GetDiaryPresenter;
import javax.inject.Inject;

public class DiaryDetailActivity extends BaseActivity implements GetDiaryPresenter.Screen {

  @Inject GetDiaryPresenter getDiaryPresenter;
  @BindView(R.id.text) TextView textView;

  public static Intent createIntent(Context context, DiaryItemModel itemModel) {
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
  }

  @Override
  protected void onStart() {
    super.onStart();
    getDiaryPresenter.setScreen(this);
    int diaryId = getIntent().getExtras().getInt(Extras.DIARY_ID);
    getDiaryPresenter.load(diaryId);
  }

  @Override
  protected void onStop() {
    getDiaryPresenter.stop();
    super.onStop();
  }

  @Override
  protected void injectActivity(ActivityComponent activityComponent) {
    activityComponent.inject(this);
  }

  @Override
  public void startLoading() {

  }

  @Override
  public void showDiary(DiaryItemModel itemModel) {
    textView.setText("Diary ::" + itemModel.getId());
    itemModel.getEntries().ifSome(entryItemModels -> {
      textView.append("\n");
      textView.append("Entries:");
      textView.append("\n");
      for (EntryItemModel entry : entryItemModels) {
        entry.getSubject().ifSome(s -> {
          textView.append("\n");
          textView.append(s);
        });
        textView.append("\n");
        textView.append(entry.getContent());
        textView.append("\n");
        textView.append("\n");
      }
    }).ifNone(() -> {
      textView.append("\n");
      textView.append("No entries");
    });
  }

  @Override
  public void stopLoading() {

  }

  @Override
  public void showError() {

  }

  private static class Extras {
    public static final String DIARY_ID = "DIARY_ID";
  }
}
