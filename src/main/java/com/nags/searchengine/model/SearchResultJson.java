package com.nags.searchengine.model;

import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonUnwrapped;

public class SearchResultJson<T> {

  private Search search;

  private List<T> data = new ArrayList<>();

  public SearchResultJson() {
    super();
  }

  public SearchResultJson(@JsonUnwrapped Search search, @JsonUnwrapped List<T> data) {
    super();
    this.search = search;
    this.data = data;
  }

  @JsonUnwrapped
  public Search getSearch() {
    return search;
  }

  @JsonUnwrapped
  public void setSearch(Search search) {
    this.search = search;
  }

  @JsonUnwrapped
  public List<T> getData() {
    return data;
  }

  @JsonUnwrapped
  public void setData(List<T> data) {
    this.data = data;
  }

}
