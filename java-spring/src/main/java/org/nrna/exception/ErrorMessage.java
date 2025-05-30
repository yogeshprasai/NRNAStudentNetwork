package org.nrna.exception;

import java.util.Date;

public class ErrorMessage {
  private int statusCode;
  private Date timestamp;
  private String message;

  public ErrorMessage(int statusCode, Date timestamp, String message, String description) {
    this.statusCode = statusCode;
    this.timestamp = timestamp;
    this.message = message;
  }

  public int getStatusCode() {
    return statusCode;
  }

  public Date getTimestamp() {
    return timestamp;
  }

  public String getMessage() {
    return message;
  }
}
