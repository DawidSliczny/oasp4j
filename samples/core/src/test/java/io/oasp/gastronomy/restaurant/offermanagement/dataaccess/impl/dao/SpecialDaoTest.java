package io.oasp.gastronomy.restaurant.offermanagement.dataaccess.impl.dao;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import io.oasp.gastronomy.restaurant.SpringBootApp;
import io.oasp.gastronomy.restaurant.offermanagement.dataaccess.api.OfferEntity;
import io.oasp.gastronomy.restaurant.offermanagement.dataaccess.api.SpecialEntity;
import io.oasp.gastronomy.restaurant.offermanagement.dataaccess.api.WeeklyPeriodEmbeddable;
import io.oasp.gastronomy.restaurant.offermanagement.dataaccess.api.dao.OfferDao;
import io.oasp.gastronomy.restaurant.offermanagement.dataaccess.api.dao.SpecialDao;
import io.oasp.module.test.common.base.ComponentTest;

/**
 * @author DSLICZNY
 *
 */
@Transactional
@SpringBootTest(classes = { SpringBootApp.class }, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SpecialDaoTest extends ComponentTest {

  @Inject
  private SpecialDao specialDao;

  @Inject
  private OfferDao offerDao;

  @PersistenceContext
  private EntityManager entityManager;

  @Test
  public void testPersistingSpecialOffer() {

    // given
    OfferEntity offer = new OfferEntity();
    this.offerDao.save(offer);

    SpecialEntity special = new SpecialEntity();
    assertThat(special.getId()).isNull();
    special.setName("Max Source");
    WeeklyPeriodEmbeddable activePeriod = new WeeklyPeriodEmbeddable();
    activePeriod.setEndingHour(14);
    activePeriod.setStartingHour(6);
    activePeriod.setStartingDay(DayOfWeek.MONDAY);
    activePeriod.setEndingDay(DayOfWeek.SATURDAY);
    special.setActivePeriod(activePeriod);
    special.setOffer(offer);

    // when
    this.specialDao.save(special);

    // then
    assertThat(special.getId()).isNotNull();

    SpecialEntity findSpecial = this.specialDao.find(special.getId());
    assertThat(findSpecial).isNotNull();
  }

  @Test
  public void testFindSpecialOffer() {

    // given
    OfferEntity offer = new OfferEntity();
    this.offerDao.save(offer);

    SpecialEntity special = new SpecialEntity();
    special.setName("name");
    WeeklyPeriodEmbeddable activePeriod = new WeeklyPeriodEmbeddable();
    activePeriod.setEndingHour(16);
    activePeriod.setStartingHour(6);
    activePeriod.setStartingDay(DayOfWeek.MONDAY);
    activePeriod.setEndingDay(DayOfWeek.SATURDAY);
    special.setActivePeriod(activePeriod);
    special.setOffer(offer);
    this.specialDao.save(special);

    LocalDateTime currentDateTime = LocalDateTime.of(2018, 11, 27, 12, 0);

    // when
    List<SpecialEntity> specials = this.specialDao.findActiveSpecials(currentDateTime);

    // then
    assertThat(specials).isNotEmpty();
    assertThat(specials.size()).isEqualTo(1);
    assertThat(specials).contains(special);
  }

}