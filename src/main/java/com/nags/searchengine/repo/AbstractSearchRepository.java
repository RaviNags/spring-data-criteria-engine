package com.nags.searchengine.repo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import com.nags.searchengine.model.Filter;
import com.nags.searchengine.model.ForeignFilter;
import com.nags.searchengine.model.GroupedFilter;
import com.nags.searchengine.model.Pagination;
import com.nags.searchengine.model.PredicateFactory;
import com.nags.searchengine.model.RequestTypeEnum;
import com.nags.searchengine.model.Search;
import com.nags.searchengine.model.Sort;

public abstract class AbstractSearchRepository<T> {

    @PersistenceContext
    protected EntityManager em;
    @Autowired
    protected PredicateFactory predicateFactory;
    abstract protected Class getEntityClass();
    abstract protected Root<T> getRoot(CriteriaQuery<T> query);

    @Transactional
    public List<T> find(Search search) throws Exception {
        Pagination pagination = search.getPagination();

        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<T> query = criteriaBuilder.createQuery(getEntityClass());

        Root<T> from = getRoot(query);

        query = query.select(from);

        Predicate[] predicateArray = getPredicates(search, from, criteriaBuilder);

        query.where(predicateArray);
        TypedQuery<T> typedQuery = sort(criteriaBuilder, query, from, search.getSorts());

        return typedQuery.getResultList();
    }

    public int count(Search search) throws Exception {
        return this.find(search).size();
    }

    private TypedQuery<T> sort(CriteriaBuilder criteriaBuilder, CriteriaQuery<T> select, Root<T> from,
                               List<Sort> sortList) throws Exception {

        if (sortList != null && sortList.size() > 0) {
            List<Order> olist = new ArrayList<>();
            for (Sort sort : sortList) {
                if (sort.getSortColumn() != null) {
                    if (sort.getSortOrder().toUpperCase().equals("ASC")) {
                        // select.orderBy(criteriaBuilder.asc(from.get(sort.getSortColumn())));
                        olist.add(criteriaBuilder.asc(from.get(sort.getSortColumn())));
                    } else if (sort.getSortOrder().toUpperCase().equals("DESC")) {
                        // select.orderBy(criteriaBuilder.desc(from.get(sort.getSortColumn())));
                        olist.add(criteriaBuilder.desc(from.get(sort.getSortColumn())));
                    } else {
                        throw new Exception("Ordre de tri invalide : " + sort.getSortOrder());
                    }
                }
            }
            select.orderBy(olist);
        }

        TypedQuery<T> typedQuery = em.createQuery(select);
        // this.sort(typedQuery, pagination)
        return typedQuery;
    }

    private void sort(TypedQuery<T> typedQuery, Pagination pagination) {
        if (pagination != null && pagination.getElemInPage() > 0) {
            typedQuery.setFirstResult(pagination.getCurrentPage() * pagination.getElemInPage());
            typedQuery.setMaxResults(pagination.getElemInPage());
        }
    }

    protected Predicate[] getPredicates(Search search, Root<T> from,
                                        CriteriaBuilder criteriaBuilder) {

        Predicate[] result = new Predicate[search.getGroupedFilters().size()];
        int index = 0;

        for (GroupedFilter groupedFilter : search.getGroupedFilters()) {
            List<Predicate> predicates = new ArrayList<>();

            for (Filter filter : groupedFilter.getFilters()) {
                Predicate predicate = filterToPredicate(criteriaBuilder, from, filter);
                if (predicate != null) {
                    predicates.add(predicate);
                }
            }

            Map<String, Join> froms = initForeign(groupedFilter.getForeignFilters(), from);
            for (ForeignFilter foreign : groupedFilter.getForeignFilters()) {
                for (Filter filter : foreign.getFilters()) {
                    Predicate predicate =
                            filterToPredicate(criteriaBuilder, froms.get(foreign.getTableName()), filter);
                    if (predicate != null) {
                        predicates.add(predicate);
                    }
                }
            }

            Predicate[] predicateArray = new Predicate[predicates.size()];
            predicates.toArray(predicateArray);
            Predicate predicateWithOpes = groupedFilter.getRequestType().equals(RequestTypeEnum.AND)
                    ? criteriaBuilder.and(predicateArray)
                    : criteriaBuilder.or(predicateArray);
            result[index++] = predicateWithOpes;
        }

        return result;
    }

    protected Predicate filterToPredicate(CriteriaBuilder criteriaBuilder, Root<T> from,
                                          Filter filter) {
        Predicate result = null;
        if (filter != null && filter.getColumn() != null && filter.getColumn().trim().length() > 0
                && filter.getOperator() != null) {
            Path path = from.get(filter.getColumn());
            result = this.predicateFactory.getPredicate(criteriaBuilder, filter, path);
        }
        return result;
    }

    protected Predicate filterToPredicate(CriteriaBuilder criteriaBuilder, Join from, Filter filter) {
        Predicate result = null;
        if (filter != null && filter.getColumn() != null && filter.getColumn().trim().length() > 0
                && filter.getOperator() != null) {
            Path path = from.get(filter.getColumn());
            result = this.predicateFactory.getPredicate(criteriaBuilder, filter, path);
        }
        return result;
    }

    private Map<String, Join> initForeign(List<ForeignFilter> foreignFilters, Root<T> from) {
        Map<String, Join> froms = new HashMap<>();

        for (ForeignFilter foreign : foreignFilters) {
            searchJoin(foreignFilters, foreign.getTableName(), from, froms);
        }

        return froms;
    }

    private void searchJoin(List<ForeignFilter> foreignFilters, String toSearch, Root<T> from,
                            Map<String, Join> froms) {
        if (!froms.containsKey(toSearch)) {
            for (ForeignFilter foreign : foreignFilters) {
                if (foreign.getTableName().equals(toSearch)) {
                    if (foreign.getParentTableName() == null)
                        froms.put(foreign.getTableName(), from.join(foreign.getTableName()));
                    else {
                        searchJoin(foreignFilters, foreign.getParentTableName(), from, froms);
                        froms.put(foreign.getTableName(),
                                froms.get(foreign.getParentTableName()).join(foreign.getTableName()));
                    }
                }
            }
        }
    }
}
