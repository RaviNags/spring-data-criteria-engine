package com.nags.searchengine.model;

import lombok.Value;

import java.util.ArrayList;
import java.util.List;
@Value
public class ForeignFilter {

  String tableName;
  String parentTableName;
  List<Filter> filters;

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
}
