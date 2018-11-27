package io.oasp.gastronomy.restaurant.offermanagement.dataaccess.api;

import java.time.DayOfWeek;

import javax.persistence.Embeddable;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import io.oasp.gastronomy.restaurant.offermanagement.common.api.WeeklyPeriod;

/**
 * @author DSLICZNY
 *
 */
@Embeddable
public class WeeklyPeriodEmbeddable implements WeeklyPeriod {

  private DayOfWeek startingDay;

  private int startingHour;

  private DayOfWeek endingDay;

  private int endingHour;

  @Override
  public DayOfWeek getStartingDay() {

    return this.startingDay;
  }

  @Override
  public void setStartingDay(DayOfWeek startingDay) {

    this.startingDay = startingDay;
  }

  @Override
  @Max(24)
  @Min(0)
  public int getStartingHour() {

    return this.startingHour;
  }

  @Override
  public void setStartingHour(int startingHour) {

    this.startingHour = startingHour;
  }

  @Override
  public DayOfWeek getEndingDay() {

    return this.endingDay;
  }

  @Override
  public void setEndingDay(DayOfWeek endingDay) {

    this.endingDay = endingDay;
  }

  @Override
  @Max(24)
  @Min(0)
  public int getEndingHour() {

    return this.endingHour;
  }

  @Override
  public void setEndingHour(int endingHour) {

    this.endingHour = endingHour;
  }
}