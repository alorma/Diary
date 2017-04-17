package com.alorma.diary.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.alorma.diary.R;
import com.alorma.diary.data.diary.dbmodel.EntryType;
import com.alorma.diary.data.model.DiaryItemModel;
import com.alorma.diary.data.model.EntryItemModel;
import com.alorma.diary.data.model.EntryMessageItemModel;
import com.alorma.diary.data.model.EntryPhotoItemModel;
import com.alorma.diary.di.component.ActivityComponent;
import com.alorma.diary.ui.adapter.EntryAdapter;
import com.alorma.diary.ui.presenter.DiaryDetailPresenter;
import java.util.List;
import java.util.UUID;
import javax.inject.Inject;
import polanski.option.Option;

public class DiaryDetailActivity extends BaseActivity implements DiaryDetailPresenter.Screen {

  @Inject DiaryDetailPresenter diaryDetailPresenter;
  @BindView(R.id.text) TextView textView;
  @BindView(R.id.fabAddEntries) FloatingActionButton fab;
  @BindView(R.id.recyclerEntries) RecyclerView recyclerViewEntries;
  private EntryAdapter adapter;

  public static Intent createIntent(Context context, DiaryItemModel itemModel) {
    Intent intent = new Intent(context, DiaryDetailActivity.class);
    intent.putExtra(Extras.DIARY_ID, itemModel.getId());
    return intent;
  }

  public static Intent createIntent(Context context, UUID diaryId) {
    Intent intent = new Intent(context, DiaryDetailActivity.class);
    intent.putExtra(Extras.DIARY_ID, diaryId);
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
    diaryDetailPresenter.requestNewEntry();
  }

  @Override
  public void openNewEntry(UUID id) {
    Intent intent = AddDiaryEntryActivity.createIntent(this, id);
    startActivity(intent);
  }

  @Override
  protected void onStart() {
    super.onStart();
    diaryDetailPresenter.setScreen(this);
    loadData();
  }

  private void loadData() {
    UUID diaryId = (UUID) getIntent().getExtras().getSerializable(Extras.DIARY_ID);
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
    createAdapter();
    recyclerViewEntries.setLayoutManager(new LinearLayoutManager(this));
    recyclerViewEntries.setAdapter(adapter);

    textView.setText("");
    textView.append("Diary ::" + itemModel.getId());
    showContact(itemModel);

    itemModel.getEntries().ifSome(entries -> adapter.addAll(entries));
  }

  private void createAdapter() {
    adapter = new EntryAdapter(LayoutInflater.from(this));
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
        textView.append("\n");
        textView.append(entry.getEntryType().name());
        textView.append("\n");
        printEntry(entry, textView);
        textView.append("\n");
        textView.append("\n");
      }
    }).ifNone(() -> {
      textView.append("\n");
      textView.append("No entries");
    });
  }

  private void printEntry(EntryItemModel entry, TextView textView) {
    if (entry.getEntryType() == EntryType.MESSAGE) {
      printEntryMessage((EntryMessageItemModel) entry, textView);
    } else if (entry.getEntryType() == EntryType.PHOTO) {
      printEntryPhoto((EntryPhotoItemModel) entry, textView);
    }
  }

  private void printEntryMessage(EntryMessageItemModel entry, TextView textView) {
    textView.append(entry.getContent());
  }

  private void printEntryPhoto(EntryPhotoItemModel entry, TextView textView) {
    if (entry.getUri() != null) {
      textView.append(entry.getUri().toString());
    }
    entry.getPhotoName().ifSome(s -> {
      textView.append("\n");
      textView.append(s);
    });
    entry.getPhotoDescription().ifSome(s -> {
      textView.append("\n");
      textView.append(s);
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

  @Override
  protected void onDestroy() {
    diaryDetailPresenter.destroy();
    super.onDestroy();
  }

  private static class Extras {
    public static final String DIARY_ID = "DIARY_ID";
  }
}
