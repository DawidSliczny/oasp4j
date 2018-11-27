package io.oasp.gastronomy.restaurant.offermanagement.common.api;

import java.time.DayOfWeek;

/**
 * @author DSLICZNY
 *
 */
public interface WeeklyPeriod {

  /**
   * @return startingDay
   */
  public DayOfWeek getStartingDay();

  /**
   * @param startingDay new value of {@link #getstartingDay}.
   */
  public void setStartingDay(DayOfWeek startingDay);

  /**
   * @return startingHour
   */
  public int getStartingHour();

  /**
   * @param startingHour new value of {@link #getstartingHour}.
   */
  public void setStartingHour(int startingHour);

  /**
   * @return endingDay
   */
  public DayOfWeek getEndingDay();

  /**
   * @param endingDay new value of {@link #getendingDay}.
   */
  public void setEndingDay(DayOfWeek endingDay);

  /**
   * @return endingHour
   */
  public int getEndingHour();

  /**
   * @param endingHour new value of {@link #getendingHour}.
   */
  public void setEndingHour(int endingHour);
}
