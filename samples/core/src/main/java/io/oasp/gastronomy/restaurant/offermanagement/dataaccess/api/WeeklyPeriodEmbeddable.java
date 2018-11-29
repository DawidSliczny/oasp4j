package io.oasp.gastronomy.restaurant.offermanagement.dataaccess.api;

import javax.persistence.Embeddable;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import io.oasp.gastronomy.restaurant.offermanagement.common.api.WeeklyPeriod;

/**
 * @author DSLICZNY
 *
 */
@Embeddable
public class WeeklyPeriodEmbeddable extends WeeklyPeriod {

  @Override
  @Max(24)
  @Min(0)
  public int getStartingHour() {

    return this.startingHour;
  }

  @Override
  @Max(24)
  @Min(0)
  public int getEndingHour() {

    return this.endingHour;
  }
}