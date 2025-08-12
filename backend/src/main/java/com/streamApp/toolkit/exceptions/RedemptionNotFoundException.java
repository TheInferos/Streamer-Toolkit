package com.streamApp.toolkit.exceptions;

public class RedemptionNotFoundException extends RuntimeException {

  public RedemptionNotFoundException(final String message) {
    super(message);
  }

  public RedemptionNotFoundException(final String message, final Throwable cause) {
    super(message, cause);
  }
}
