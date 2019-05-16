package com.nags.searchengine.model;

import java.util.ArrayList;
import java.util.List;

public class ForeignFilter {

  private String tableName;

  private String parentTableName;

  private List<Filter> filters;

  public ForeignFilter() {
    this.filters = new ArrayList<>();
  }

  public ForeignFilter(String parent, String table) {
    this.parentTableName = parent;
    this.tableName = table;
    this.filters = new ArrayList<>();
  }

  public ForeignFilter(String parent, String table, Filter filter) {
    this.parentTableName = parent;
    this.tableName = table;
    this.filters = new ArrayList<>();
    this.filters.add(filter);
  }

  public String getTableName() {
    return tableName;
  }

  public void setTableName(String tableName) {
    this.tableName = tableName;
  }

  public String getParentTableName() {
    return parentTableName;
  }

  public void setParentTableName(String parentTableName) {
    this.parentTableName = parentTableName;
  }

  public List<Filter> getFilters() {
    return filters;
  }

  public void setFilters(List<Filter> filters) {
    this.filters = filters;
  }

  // @Override
  // public String toString ()
  // {
  // return "{tableName : " + tableName + ", parentTableName : " + parentTableName
  // + ", filters : " + filters + "}";
  // }

}
