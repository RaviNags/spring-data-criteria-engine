package com.nags.searchengine.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class GroupedFilter {
  private RequestTypeEnum requestType = RequestTypeEnum.AND;

  private List<Filter> filters = new ArrayList<>();

  private List<ForeignFilter> foreignFilters = new ArrayList<>();

  public GroupedFilter() {
    super();
  }

  public GroupedFilter(RequestTypeEnum requestType) {
    super();
    this.requestType = requestType;
  }

  public void addFilter(Filter filter) {
    this.filters.add(filter);
  }

  public void addForeignFilters(String parent, String table, Filter filter) {
    Optional<ForeignFilter> opt = this.foreignFilters.stream()
        .filter(c -> c.getParentTableName().equals(parent) && c.getTableName().equals(table))
        .findAny();
    if (opt.isPresent())
      opt.get().getFilters().add(filter);
    else
      this.foreignFilters.add(new ForeignFilter(parent, table, filter));
  }

  public RequestTypeEnum getRequestType() {
    return requestType;
  }

  public void setRequestType(RequestTypeEnum requestType) {
    this.requestType = requestType;
  }

  public List<ForeignFilter> getForeignFilters() {
    return foreignFilters;
  }

  public void setForeignFilters(List<ForeignFilter> foreignFilters) {
    this.foreignFilters = foreignFilters;
  }

  public List<Filter> getFilters() {
    return filters;
  }

  public void setFilters(List<Filter> filters) {
    this.filters = filters;
  }

  @Override
  public String toString() {
    String result = "{ requestType : " + this.requestType.toString() + ", ";
    result = result + "filters : [";

    for (Filter filter : this.filters) {
      result = result + filter.toString() + ",";
    }

    result = result + "], ";

    result = result + "foreignFilters : [";

    for (ForeignFilter filter : this.foreignFilters) {
      result = result + filter.toString() + ",";
    }

    result = result + "] }";

    return result;
  }
}
