package io.oasp.gastronomy.restaurant.offermanagement.dataaccess.impl.dao;

import static com.querydsl.core.alias.Alias.$;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.List;

import javax.inject.Named;

import com.querydsl.core.alias.Alias;
import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.jpa.impl.JPAQuery;

import io.oasp.gastronomy.restaurant.general.dataaccess.base.dao.ApplicationMasterDataDaoImpl;
import io.oasp.gastronomy.restaurant.offermanagement.dataaccess.api.SpecialEntity;
import io.oasp.gastronomy.restaurant.offermanagement.dataaccess.api.dao.SpecialDao;

/**
 * @author DSLICZNY
 *
 */
@Named
public class SpecialDaoImpl extends ApplicationMasterDataDaoImpl<SpecialEntity> implements SpecialDao {

  /**
   * The constructor.
   */
  public SpecialDaoImpl() {

    super();
  }

  @Override
  protected Class<SpecialEntity> getEntityClass() {

    return SpecialEntity.class;
  }

  public List<SpecialEntity> findActiveSpecials(LocalDateTime dateTimeToCheck) {

    DayOfWeek currentDayOfWeek = dateTimeToCheck.getDayOfWeek();
    int currentHour = dateTimeToCheck.getHour();

    SpecialEntity special = Alias.alias(SpecialEntity.class);
    EntityPathBase<SpecialEntity> alias = $(special);
    JPAQuery<SpecialEntity> query = new JPAQuery<SpecialEntity>(getEntityManager()).from(alias);

    buldQueryForDateInActivePeriod(currentDayOfWeek, currentHour, special, query);

    return query.fetch();
  }

  private void buldQueryForDateInActivePeriod(DayOfWeek currentDayOfWeek, int currentHour, SpecialEntity special,
      JPAQuery<SpecialEntity> query) {

    query.where($(special.getActivePeriod().getStartingDay()).loe(currentDayOfWeek));
    query.where($(special.getActivePeriod().getEndingDay()).goe(currentDayOfWeek));
    query.where($(special.getActivePeriod().getStartingHour()).loe(currentHour));
    query.where($(special.getActivePeriod().getEndingHour()).goe(currentHour));
  }
}
