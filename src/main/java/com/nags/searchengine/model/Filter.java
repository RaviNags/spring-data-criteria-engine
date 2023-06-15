package com.nags.searchengine.model;

import lombok.Value;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Value
public class Filter {

    String column;
    OperationEnum operator;
    List<Object> values;

    public Filter(String column, OperationEnum operateur, List<Object> operandes) {
        super();
        this.column = column;
        this.operator = operateur;
        this.values = operandes;
    }

    public Filter(String column, OperationEnum operateur, Object operande) {
        super();
        this.column = column;
        this.operator = operateur;
        if (operande instanceof List)
            this.values = (ArrayList<Object>) operande;
        else
            this.values = new ArrayList<>(Arrays.asList(operande));
    }

}
