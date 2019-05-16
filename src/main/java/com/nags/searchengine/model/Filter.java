package com.nags.searchengine.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Filter {

  private String column;

  private OperationEnum operator;

  private List<Object> values;

  public Filter() {
    super();
  }

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
    this.values = new ArrayList<>(Arrays.asList(operande));
  }

  public String getColumn() {
    return column;
  }

  public void setColumn(String column) {
    this.column = column;
  }

  public OperationEnum getOperator() {
    return operator;
  }

  public void setOperator(OperationEnum operateur) {
    this.operator = operateur;
  }

  public List<Object> getValues() {
    return values;
  }

  public void setValues(List<Object> operandes) {
    this.values = operandes;
  }

  // @Override
  // public String toString ()
  // {
  // return "{column :" + column + ", operator :" + operator + ", values :" +
  // values + "]";
  // }

}
