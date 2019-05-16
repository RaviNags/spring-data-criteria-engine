package com.nags.searchengine.model;

public class Pagination {

  private int currentPage = 0;

  private int nbPage = 0;

  private int count = 0;

  private int elemInPage = 0;

  public Pagination() {
    super();
  }

  public int getCurrentPage() {
    return currentPage;
  }

  public void setCurrentPage(int currentPage) {
    this.currentPage = currentPage;
  }

  public int getNbPage() {
    if (this.count == 0 || this.elemInPage == 0)
      return 0;

    return (this.count / this.elemInPage) + 1;
  }

  public void setNbPage(int nbPage) {
    this.nbPage = nbPage;
  }

  public int getElemInPage() {
    return elemInPage;
  }

  public void setElemInPage(int elemInPage) {
    this.elemInPage = elemInPage;
  }

  public int getCount() {
    return count;
  }

  public void setCount(int count) {
    this.count = count;
  }

}
