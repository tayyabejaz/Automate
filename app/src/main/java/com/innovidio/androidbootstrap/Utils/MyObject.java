package com.innovidio.androidbootstrap.Utils;
import java.util.Date;

public class MyObject implements Comparable<MyObject> {

  private Date dateTime;

  public Date getDateTime() {
    return dateTime;
  }

  public void setDateTime(Date datetime) {
    this.dateTime = datetime;
  }

  @Override
  public int compareTo(MyObject o) {
    return getDateTime().compareTo(o.getDateTime());
  }
}