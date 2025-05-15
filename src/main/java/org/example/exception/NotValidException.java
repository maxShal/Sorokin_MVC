package org.example.exception;

public class NotValidException extends IllegalArgumentException
{
  public NotValidException(String s) {
    super(s);
  }
}
