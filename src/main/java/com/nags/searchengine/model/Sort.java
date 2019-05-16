package com.nags.searchengine.model;

public class Sort {

  private String sortColumn = "";

  private String sortOrder = "ASC";

  public Sort() {
    super();
  }

  public Sort(String sortColumn) {
    super();
    this.sortColumn = sortColumn;
  }

  public Sort(String sortColumn, String sortOrder) {
    super();
    this.sortColumn = sortColumn;
    this.sortOrder = sortOrder;
  }

  public String getSortColumn() {
    return sortColumn;
  }

  public void setSortColumn(String sortColumn) {
    this.sortColumn = sortColumn;
  }

  public String getSortOrder() {
    return sortOrder;
  }

  public void setSortOrder(String sortOrder) {
    this.sortOrder = sortOrder;
  }


}
