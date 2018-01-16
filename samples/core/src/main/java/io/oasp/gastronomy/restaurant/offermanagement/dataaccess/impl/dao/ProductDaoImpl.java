package io.oasp.gastronomy.restaurant.offermanagement.dataaccess.impl.dao;

import static com.querydsl.core.alias.Alias.$;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Named;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.alias.Alias;
import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQuery;

import io.oasp.gastronomy.restaurant.general.dataaccess.base.dao.ApplicationMasterDataDaoImpl;
import io.oasp.gastronomy.restaurant.offermanagement.common.api.datatype.ProductSortByHitEntry;
import io.oasp.gastronomy.restaurant.offermanagement.dataaccess.api.DrinkEntity;
import io.oasp.gastronomy.restaurant.offermanagement.dataaccess.api.MealEntity;
import io.oasp.gastronomy.restaurant.offermanagement.dataaccess.api.ProductEntity;
import io.oasp.gastronomy.restaurant.offermanagement.dataaccess.api.SideDishEntity;
import io.oasp.gastronomy.restaurant.offermanagement.dataaccess.api.dao.ProductDao;
import io.oasp.gastronomy.restaurant.offermanagement.logic.api.to.ProductFilter;
import io.oasp.gastronomy.restaurant.offermanagement.logic.api.to.ProductSearchCriteriaTo;
import io.oasp.gastronomy.restaurant.offermanagement.logic.api.to.ProductSortBy;
import io.oasp.module.jpa.common.api.to.PaginatedListTo;
import io.oasp.module.jpa.common.api.to.PaginationResultTo;
import io.oasp.module.jpa.common.api.to.PaginationTo;

/**
 * Implementation of {@link ProductDao}.
 *
 */
@Named
public class ProductDaoImpl extends ApplicationMasterDataDaoImpl<ProductEntity> implements ProductDao {

  /**
   * The constructor.
   */
  public ProductDaoImpl() {

    super();
  }

  @Override
  public Class<ProductEntity> getEntityClass() {

    return ProductEntity.class;
  }

  @Override
  @Deprecated
  public List<ProductEntity> findProductsFiltered(ProductFilter productFilterBo, ProductSortBy sortBy) {

    /*
     * Basic error handling
     */
    if (productFilterBo == null) {
      return new ArrayList<>();
    }

    ProductEntity product = Alias.alias(ProductEntity.class);
    JPQLQuery<ProductEntity> query = new JPAQuery<ProductEntity>(getEntityManager()).from($(product));
    BooleanBuilder builder = new BooleanBuilder();

    /*
     * Drinks
     */
    if (productFilterBo.getFetchDrinks()) {
      builder.or($(product).instanceOf(DrinkEntity.class));
    }

    /*
     * Meals
     */
    if (productFilterBo.getFetchMeals()) {
      builder.or($(product).instanceOf(MealEntity.class));
    }

    /*
     * SideDishes
     */
    if (productFilterBo.getFetchSideDishes()) {
      builder.or($(product).instanceOf(SideDishEntity.class));
    }

    query.where(builder);
    if (sortBy.getSortByEntry().equals(ProductSortByHitEntry.DESCRIPTION)) {
      if (sortBy.getOrderBy().isDesc()) {
        query.orderBy($(product.getDescription()).desc());
      } else {
        query.orderBy($(product.getDescription()).asc());
      }
    } else {
      if (sortBy.getOrderBy().isDesc()) {
        query.orderBy($(product.getId()).desc());
      } else {
        query.orderBy($(product.getId()).asc());
      }

    }

    List<ProductEntity> result = query.fetch();
    return result;
  }

  @Override
  public PaginatedListTo<ProductEntity> findProducts(ProductSearchCriteriaTo criteria) {

    ProductEntity product = Alias.alias(ProductEntity.class);
    EntityPathBase<ProductEntity> alias = $(product);
    JPAQuery<ProductEntity> query = new JPAQuery<ProductEntity>(getEntityManager()).from(alias);

    String name = criteria.getName();
    if (name != null) {
      query.where($(product.getName()).eq(name));
    }

    String description = criteria.getDescription();
    if (description != null) {
      query.where($(product.getDescription()).eq(description));
    }

    // include filter for entity type

    if (!(criteria.isFetchDrinks() || criteria.isFetchMeals() || criteria.isFetchSideDishes())) {
      // no product type was selected, return empty result
      PaginationTo pagination = criteria.getPagination();

      PaginationResultTo paginationResult = new PaginationResultTo(pagination, 0L);
      List<ProductEntity> paginatedList = new ArrayList<>();

      return new PaginatedListTo<>(paginatedList, paginationResult);
    }

    BooleanBuilder builder = new BooleanBuilder();
    if (criteria.isFetchSideDishes()) {
      builder.or($(product).instanceOf(SideDishEntity.class));
    }
    if (criteria.isFetchMeals()) {
      builder.or($(product).instanceOf(MealEntity.class));
    }
    if (criteria.isFetchDrinks()) {
      builder.or($(product).instanceOf(DrinkEntity.class));
    }
    query.where(builder);

    return findPaginated(criteria, query);
  }
}
