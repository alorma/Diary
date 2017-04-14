package com.alorma.diary.data;

import com.alorma.diary.data.exception.ValidationException;

public interface Validator<T> {
  boolean validate(T t) throws ValidationException;
}
