package com.nags.searchengine.service;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.transaction.annotation.Transactional;
import com.nags.searchengine.model.Pagination;
import com.nags.searchengine.model.Search;
import com.nags.searchengine.repo.AbstractSearchRepository;


public abstract class AbstractSearchService<T> {

  public abstract AbstractSearchRepository<T> getSearchRepository();

  public List<T> getBySearch(Search request) {
    try {
      return this.find(request);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  @Transactional
  public List<T> find(Search search) throws Exception {
    List<T> result = null;
    try {
      result = getSearchRepository().find(search);
      Pagination pagination = search.getPagination();
      if (pagination != null && pagination.getElemInPage() > 0) {
        result = result.stream().skip(pagination.getCurrentPage() * pagination.getElemInPage())
            .limit(pagination.getElemInPage()).collect(Collectors.toList());
      }
      // {
      // typedQuery.setFirstResult(pagination.getCurrentPage() * pagination.getElemInPage());
      // typedQuery.setMaxResults(pagination.getElemInPage());
      // }
    } catch (Exception e) {
      throw new Exception("Search API unknow error : " + e);
    }
    return result;
  }

  public int count(Search search) throws Exception {
    int result = -1;
    try {
      result = getSearchRepository().count(search);
    } catch (Exception e) {
      throw new Exception("Search API unknow error");
    }
    return result;
  }
}
