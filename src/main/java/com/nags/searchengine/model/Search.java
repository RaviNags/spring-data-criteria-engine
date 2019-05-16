package com.nags.searchengine.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Search {
  private Pagination pagination;

  private List<Sort> sorts;

  // private List<Filter> filters;

  private List<GroupedFilter> groupedFilters = new ArrayList<>();

  // private List<ForeignFilter> foreignFilters;

  public Search() {
    super();
    sorts = new ArrayList<>();
    groupedFilters = new ArrayList<>();
    // filters = new ArrayList<>();
    // foreignFilters = new ArrayList<>();
  }

  public Pagination getPagination() {
    return pagination;
  }

  public void setPagination(Pagination pagination) {
    this.pagination = pagination;
  }

  // public List<Filter> getFilters ()
  // {
  // return filters;
  // }
  //
  // public void setFilters ( List<Filter> filters )
  // {
  // this.filters = filters;
  // }
  //
  // public List<ForeignFilter> getForeignFilters ()
  // {
  // return foreignFilters;
  // }
  //
  // public void setForeignFilters ( List<ForeignFilter> foreignFilters )
  // {
  // this.foreignFilters = foreignFilters;
  // }

  public List<Sort> getSorts() {
    return sorts;
  }

  public void setSorts(List<Sort> sorts) {
    this.sorts = sorts;
  }

  public void addSort(Sort sort) {
    if (sorts == null)
      sorts = new ArrayList<>();

    sorts.add(sort);
  }

  // public void addForeignFilters ( String parent, String table, Filter filter )
  // {
  // Optional<ForeignFilter> opt = this.foreignFilters.stream()
  // .filter(c -> c.getParentTableName().equals(parent) &&
  // c.getTableName().equals(table))
  // .findAny();
  // if (opt.isPresent())
  // opt.get().getFilters().add(filter);
  // else
  // this.foreignFilters.add(new ForeignFilter(parent, table, filter));
  // }
  //
  // public void addFilter ( Filter filter )
  // {
  // this.filters.add(filter);
  // }

  public void addGroupedFilter(GroupedFilter groupedFilter) {
    Optional<GroupedFilter> opt = this.groupedFilters.stream()
        .filter(c -> c.getRequestType().equals(groupedFilter.getRequestType())).findAny();
    if (opt.isPresent()) {
      groupedFilter.getFilters().forEach(filter -> opt.get().getFilters().add(filter));
      groupedFilter.getForeignFilters()
          .forEach(filter -> opt.get().getForeignFilters().add(filter));
    } else
      groupedFilters.add(groupedFilter);
  }

  public List<GroupedFilter> getGroupedFilters() {
    return groupedFilters;
  }

  @Override
  public String toString() {
    String result = "{";

    if (pagination != null)
      result = result + "\"pagination\" :" + pagination.toString() + ",";

    result = "groupedFilters : [";

    for (GroupedFilter gfilter : groupedFilters)
      result = result + gfilter.toString() + ",";

    result = result + "]";

    return result + "}";
  }

}
