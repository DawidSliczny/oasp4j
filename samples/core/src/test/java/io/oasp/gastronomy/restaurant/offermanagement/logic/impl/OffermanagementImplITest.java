package io.oasp.gastronomy.restaurant.offermanagement.logic.impl;

import java.time.DayOfWeek;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import net.sf.mmm.util.exception.api.ObjectNotFoundException;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import io.oasp.gastronomy.restaurant.SpringBootApp;
import io.oasp.gastronomy.restaurant.offermanagement.dataaccess.api.OfferEntity;
import io.oasp.gastronomy.restaurant.offermanagement.dataaccess.api.WeeklyPeriodEmbeddable;
import io.oasp.gastronomy.restaurant.offermanagement.dataaccess.api.dao.OfferDao;
import io.oasp.gastronomy.restaurant.offermanagement.logic.api.to.SpecialEto;
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

    // when
    SpecialEto specialEto = this.offerManagementImpl.saveSpecial(special);

    SpecialEto findSpecial = this.offerManagementImpl.findSpecial(specialEto.getId());

    // then
    assertThat(findSpecial).isNotNull();
    assertThat(findSpecial.getId()).isNotNull();
  }

  @Test(expected = ObjectNotFoundException.class)
  public void deleteSpecial() {

    // given
    SpecialEto special = getSpecialEto("nameThree");

    // when
    SpecialEto specialEto = this.offerManagementImpl.saveSpecial(special);

    this.offerManagementImpl.deleteSpecial(specialEto.getId());

    SpecialEto findSpecial = this.offerManagementImpl.findSpecial(specialEto.getId());
  }

  /**
   * @return
   */
  private SpecialEto getSpecialEto(String name) {

    OfferEntity offer = new OfferEntity();
    this.offerDao.save(offer);

    SpecialEto special = new SpecialEto();
    assertThat(special.getId()).isNull();
    special.setName(name);
    WeeklyPeriodEmbeddable activePeriod = new WeeklyPeriodEmbeddable();
    activePeriod.setEndingHour(14);
    activePeriod.setStartingHour(6);
    activePeriod.setStartingDay(DayOfWeek.MONDAY);
    activePeriod.setEndingDay(DayOfWeek.SATURDAY);
    special.setActivePeriod(activePeriod);
    special.setOfferId(offer.getId());
    return special;
  }
}
