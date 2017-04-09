package com.alorma.diary.di.component;

import android.support.v4.app.Fragment;
import com.alorma.diary.di.qualifiers.PerFragment;

@PerFragment
public interface FragmentComponent<T extends Fragment> {
  void inject(T fragment);
}