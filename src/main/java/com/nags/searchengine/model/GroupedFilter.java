package com.nags.searchengine.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Data
public class GroupedFilter {
  private RequestTypeEnum requestType;

  private List<Filter> filters = new ArrayList<>();

  private List<ForeignFilter> foreignFilters = new ArrayList<>();

  public GroupedFilter() {
    this.filters = new ArrayList<>();
    this.foreignFilters = new ArrayList<>();
    this.requestType = RequestTypeEnum.AND;
  }

  public GroupedFilter(RequestTypeEnum requestType) {
    this.filters = new ArrayList<>();
    this.foreignFilters = new ArrayList<>();
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
