package io.oasp.gastronomy.restaurant.offermanagement.logic.impl;

import java.math.BigDecimal;
import java.time.DayOfWeek;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import net.sf.mmm.util.exception.api.ObjectNotFoundException;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import io.oasp.gastronomy.restaurant.SpringBootApp;
import io.oasp.gastronomy.restaurant.general.common.TestUtil;
import io.oasp.gastronomy.restaurant.general.common.api.constants.PermissionConstants;
import io.oasp.gastronomy.restaurant.general.common.api.datatype.Money;
import io.oasp.gastronomy.restaurant.offermanagement.dataaccess.api.OfferEntity;
import io.oasp.gastronomy.restaurant.offermanagement.dataaccess.api.WeeklyPeriodEmbeddable;
import io.oasp.gastronomy.restaurant.offermanagement.dataaccess.api.dao.OfferDao;
import io.oasp.gastronomy.restaurant.offermanagement.logic.api.to.OfferEto;
import io.oasp.gastronomy.restaurant.offermanagement.logic.api.to.OfferSearchCriteriaTo;
import io.oasp.gastronomy.restaurant.offermanagement.logic.api.to.SpecialEto;
import io.oasp.module.jpa.common.api.to.PaginatedListTo;
import io.oasp.module.test.common.base.ComponentTest;

/**
 * @author DSLICZNY
 *
 */
@Transactional
@SpringBootTest(classes = { SpringBootApp.class }, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class OffermanagementImplITest extends ComponentTest {

  @Inject
  private OffermanagementImpl offerManagementImpl;

  @Inject
  private OfferDao offerDao;

  @PersistenceContext
  private EntityManager entityManager;

  @Test
  public void saveSpecial() {

    // given
    SpecialEto special = getSpecialEto("name");

    // when
    SpecialEto specialEto = this.offerManagementImpl.saveSpecial(special);

    // then
    assertThat(specialEto).isNotNull();
    assertThat(specialEto.getId()).isNotNull();
  }

  @Test
  public void findSpecial() {

    // given
    SpecialEto special = getSpecialEto("nameTwo");
    SpecialEto specialEto = this.offerManagementImpl.saveSpecial(special);

    // when
    SpecialEto findSpecial = this.offerManagementImpl.findSpecial(specialEto.getId());

    // then
    assertThat(findSpecial).isNotNull();
    assertThat(findSpecial.getId()).isNotNull();
  }

  @Test(expected = ObjectNotFoundException.class)
  public void deleteSpecial() {

    // given
    SpecialEto special = getSpecialEto("nameThree");
    SpecialEto specialEto = this.offerManagementImpl.saveSpecial(special);

    // when
    this.offerManagementImpl.deleteSpecial(specialEto.getId());

    // then
    SpecialEto findSpecial = this.offerManagementImpl.findSpecial(specialEto.getId());
  }

  @Test
  public void shouldReturnSpecialPriceForoffer() {

    // login
    TestUtil.login("chief", PermissionConstants.FIND_OFFER);

    // given
    OfferEntity offerEntity = new OfferEntity();
    offerEntity.setPrice(new Money(new BigDecimal(80)));
    this.offerDao.save(offerEntity);
    SpecialEto special = getSpecialEtoForOffer("nameFour", offerEntity);
    this.offerManagementImpl.saveSpecial(special);

    OfferSearchCriteriaTo criteria = new OfferSearchCriteriaTo();

    // when
    PaginatedListTo<OfferEto> offers = this.offerManagementImpl.findOfferEtos(criteria);

    // then
    assertThat(offers.getResult()).isNotEmpty();
    OfferEto offerEto = offers.getResult().stream().filter(offer -> offer.getId().equals(offerEntity.getId()))
        .findFirst().orElse(null);
    assertThat(offerEto).isNotNull();
    assertThat(offerEto.getPrice()).isEqualTo(new Money(new BigDecimal(50)));

    // logout
    TestUtil.logout();

  }

  /**
   * @return
   */
  private SpecialEto getSpecialEto(String name) {

    OfferEntity offer = new OfferEntity();
    this.offerDao.save(offer);

    return getSpecialEtoForOffer(name, offer);
  }

  private SpecialEto getSpecialEtoForOffer(String name, OfferEntity offer) {

    SpecialEto special = new SpecialEto();
    assertThat(special.getId()).isNull();
    special.setName(name);
    special.setSpecialPrice(new Money(new BigDecimal(50)));
    WeeklyPeriodEmbeddable activePeriod = new WeeklyPeriodEmbeddable();
    activePeriod.setEndingHour(23);
    activePeriod.setStartingHour(0);
    activePeriod.setStartingDay(DayOfWeek.MONDAY);
    activePeriod.setEndingDay(DayOfWeek.SUNDAY);
    special.setActivePeriod(activePeriod);
    special.setOfferId(offer.getId());
    return special;
  }
}
