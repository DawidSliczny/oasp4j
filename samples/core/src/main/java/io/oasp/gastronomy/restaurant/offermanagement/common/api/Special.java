package io.oasp.gastronomy.restaurant.offermanagement.common.api;

import io.oasp.gastronomy.restaurant.general.common.api.ApplicationEntity;
import io.oasp.gastronomy.restaurant.general.common.api.datatype.Money;
import io.oasp.gastronomy.restaurant.offermanagement.dataaccess.api.WeeklyPeriodEmbeddable;

/**
 * @author DSLICZNY
 *
 */
public interface Special extends ApplicationEntity {

  /**
   * @return name
   */
  public String getName();

  /**
   * @param name new value of {@link #getname}.
   */
  public void setName(String name);

  /**
   * @return offer
   */
  public Long getOfferId();

  /**
   * @param offer new value of {@link #getoffer}.
   */
  public void setOfferId(Long offerId);

  /**
   * @return activePeriod
   */
  public WeeklyPeriodEmbeddable getActivePeriod();

  /**
   * @param activePeriod new value of {@link #getactivePeriod}.
   */
  public void setActivePeriod(WeeklyPeriodEmbeddable activePeriod);

  /**
   * @return specialPrice
   */
  public Money getSpecialPrice();

  /**
   * @param specialPrice new value of {@link #getspecialPrice}.
   */
  public void setSpecialPrice(Money specialPrice);
}
