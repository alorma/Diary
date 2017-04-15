package com.alorma.diary.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.alorma.diary.R;
import com.alorma.diary.data.model.DiaryItemModel;
import com.alorma.diary.data.model.EntryItemModel;
import com.alorma.diary.di.component.ActivityComponent;
import com.alorma.diary.ui.presenter.DiaryDetailPresenter;
import java.util.Random;
import java.util.UUID;
import javax.inject.Inject;

public class DiaryDetailActivity extends BaseActivity implements DiaryDetailPresenter.Screen {

  @Inject DiaryDetailPresenter diaryDetailPresenter;
  @BindView(R.id.text) TextView textView;
  @BindView(R.id.fabAddEntries) FloatingActionButton fab;

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

    fab.setOnClickListener(v -> onAddEntryClick());
  }

  private void onAddEntryClick() {
    EntryItemModel entry = getEntry();
    diaryDetailPresenter.addEntry(entry);
  }

  private EntryItemModel getEntry() {
    Random random = new Random();
    EntryItemModel model = new EntryItemModel();
    model.setSubject("Random entry: " + random.nextGaussian());
    model.setContent("Lorem ipsum generated: " + UUID.randomUUID());
    model.setPostedDate(System.currentTimeMillis());
    return model;
  }

  @Override
  protected void onStart() {
    super.onStart();
    diaryDetailPresenter.setScreen(this);
    loadData();
  }

  private void loadData() {
    int diaryId = getIntent().getExtras().getInt(Extras.DIARY_ID);
    diaryDetailPresenter.load(diaryId);
  }

  @Override
  protected void onStop() {
    diaryDetailPresenter.stop();
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
    textView.setText("");
    textView.append("Diary ::" + itemModel.getId());
    showContact(itemModel);
    showEntries(itemModel);
  }

  @Override
  public void showEntryAdded() {
    loadData();
  }

  private void showContact(DiaryItemModel itemModel) {
    itemModel.getContact().ifSome(contact -> {
      textView.append("\n");
      textView.append("------");
      textView.append("\n");
      textView.append(contact.getName());
      textView.append("\n");
      contact.getComments()
          .filter(strings -> strings != null && !strings.isEmpty())
          .ifSome(comments -> {
            for (String comment : comments) {
              textView.append(comment);
              textView.append("\n");
            }
          }).ifNone(() -> {
        textView.append("\n");
        textView.append("No comments");
        textView.append("\n");
      });
      textView.append("\n");
      textView.append("------");
      textView.append("\n");
    });
  }

  private void showEntries(DiaryItemModel itemModel) {
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

  @Override
  public void showErrorAddEntry() {

  }

  private static class Extras {
    public static final String DIARY_ID = "DIARY_ID";
  }
}
