package io.oasp.gastronomy.restaurant.offermanagement.common.api;

import java.time.DayOfWeek;

/**
 * @author DSLICZNY
 *
 */
public class WeeklyPeriod {

  protected DayOfWeek startingDay;

  protected int startingHour;

  protected DayOfWeek endingDay;

  protected int endingHour;

  public DayOfWeek getStartingDay() {

    return this.startingDay;
  }

  public void setStartingDay(DayOfWeek startingDay) {

    this.startingDay = startingDay;
  }

  public int getStartingHour() {

    return this.startingHour;
  }

  public void setStartingHour(int startingHour) {

    this.startingHour = startingHour;
  }

  public DayOfWeek getEndingDay() {

    return this.endingDay;
  }

  public void setEndingDay(DayOfWeek endingDay) {

    this.endingDay = endingDay;
  }

  public int getEndingHour() {

    return this.endingHour;
  }

  public void setEndingHour(int endingHour) {

    this.endingHour = endingHour;
  }
}
