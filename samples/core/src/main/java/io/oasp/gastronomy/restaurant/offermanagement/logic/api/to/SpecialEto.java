package io.oasp.gastronomy.restaurant.offermanagement.logic.api.to;

import io.oasp.gastronomy.restaurant.general.common.api.datatype.Money;
import io.oasp.gastronomy.restaurant.general.common.api.datatype.validation.NotNegativeMoney;
import io.oasp.gastronomy.restaurant.offermanagement.common.api.Special;
import io.oasp.gastronomy.restaurant.offermanagement.dataaccess.api.WeeklyPeriodEmbeddable;
import io.oasp.module.basic.common.api.to.AbstractEto;

/**
 * @author DSLICZNY
 *
 */
public class SpecialEto extends AbstractEto implements Special {

  private String name;

  @NotNegativeMoney
  private Money specialPrice;

  private Long offerId;

  private WeeklyPeriodEmbeddable activePeriod;

  @Override
  public Long getOfferId() {

    return this.offerId;
  }

  @Override
  public void setOfferId(Long offerId) {

    this.offerId = offerId;
  }

  @Override
  public WeeklyPeriodEmbeddable getActivePeriod() {

    return this.activePeriod;
  }

  @Override
  public void setActivePeriod(WeeklyPeriodEmbeddable activePeriod) {

    this.activePeriod = activePeriod;
  }

  @Override
  public Money getSpecialPrice() {

    return this.specialPrice;
  }

  @Override
  public void setSpecialPrice(Money specialPrice) {

    this.specialPrice = specialPrice;
  }

  @Override
  public String getName() {

    return this.name;
  }

  @Override
  public void setName(String name) {

    this.name = name;
  }

}
