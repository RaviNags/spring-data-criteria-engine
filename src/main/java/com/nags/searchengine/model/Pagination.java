package com.nags.searchengine.model;

import lombok.Data;
import lombok.Value;

@Data
public class Pagination {

  int currentPage = 0;
  int nbPage = 0;
  int count = 0;
  int elemInPage = 0;

  public Pagination() {
    super();
  }

  public int getNbPage() {
    if (this.count == 0 || this.elemInPage == 0) {
      return 0;
    }
    return (this.count / this.elemInPage) + 1;
  }
}
