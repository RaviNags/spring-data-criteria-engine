package com.nags.searchengine.model;

public enum OperationEnum {
  EQUAL("="), INF("<"), INF_EG("<="), BETWEEN("between"), SUP(">"), SUP_EG(">="), IN("in"), START(
      "start");

  private final String nom;

  public String toString() {
    return this.nom;
  }

  private OperationEnum(String nom) {
    this.nom = nom;
  }
}
