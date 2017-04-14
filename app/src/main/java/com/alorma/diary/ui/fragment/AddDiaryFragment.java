package com.alorma.diary.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.ButterKnife;
import com.alorma.diary.R;
import com.alorma.diary.di.component.ApplicationComponent;
import com.alorma.diary.di.component.DataComponent;
import com.alorma.diary.di.component.FragmentComponent;
import com.alorma.diary.di.qualifiers.PerFragment;
import com.alorma.diary.ui.presenter.AddDiaryPresenter;
import dagger.Component;
import javax.inject.Inject;

public class AddDiaryFragment extends BaseFragment implements AddDiaryPresenter.Screen {

  @Inject AddDiaryPresenter presenter;

  public static AddDiaryFragment newInstance() {
    return new AddDiaryFragment();
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    super.onCreateView(inflater, container, savedInstanceState);
    return inflater.inflate(R.layout.fragment_add_diary, null, false);
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    ButterKnife.bind(this, view);
  }

  @Override
  protected void initInject(ApplicationComponent mainComponent, DataComponent dataComponent) {
    DaggerAddDiaryFragment_Provide
        .builder()
        .applicationComponent(mainComponent)
        .dataComponent(dataComponent)
        .build()
        .inject(this);
  }

  @Override
  public void onStart() {
    super.onStart();
    presenter.setScreen(this);
  }

  @Override
  public void onStop() {
    super.onStop();

  }

  @Override
  public void startLoading() {

  }

  @Override
  public void stopLoading() {

  }

  @Override
  public void closeScreen() {

  }

  @Override
  public void showError() {

  }

  @PerFragment
  @Component(dependencies = { ApplicationComponent.class, DataComponent.class })
  public interface Provide extends FragmentComponent<AddDiaryFragment> {

  }
}
