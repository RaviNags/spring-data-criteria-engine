package com.nags.searchengine.model;

import lombok.Value;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Value
public class Search {
    Pagination pagination;
    List<Sort> sorts;
    List<GroupedFilter> groupedFilters;

    public Search() {
        sorts = new ArrayList<>();
        groupedFilters = new ArrayList<>();
        pagination = new Pagination();
    }

    public void addGroupedFilter(GroupedFilter groupedFilter) {
        groupedFilters.add(groupedFilter);
    }

    @Override
    public String toString() {
        String result = "{";
        result = result + "\"pagination\" :" + pagination.toString() + ",";
        result = result + "groupedFilters : [";
        for (GroupedFilter gfilter : groupedFilters) {
            result = result + gfilter.toString() + ",";
        }
        result = result + "]";
        return result + "}";
    }

}
