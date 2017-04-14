package com.alorma.diary.data;

import io.reactivex.Completable;

public interface Validator<T> {
  Completable validate(T t);
}
