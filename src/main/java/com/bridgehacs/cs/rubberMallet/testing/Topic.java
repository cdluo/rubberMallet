package com.bridgehacs.cs.rubberMallet.testing;

public class Topic {

  private Pair<String, Double> w1;
  private Pair<String, Double> w2;
  private Pair<String, Double> w3;
  private Pair<String, Double> w4;
  private Pair<String, Double> w5;
  
  public Topic(Pair<String, Double> W1, Pair<String, Double> W2, Pair<String, Double> W3, Pair<String, Double> W4, Pair<String, Double> W5){
    w1 = W1;
    w2 = W2;
    w3 = W3;
    w4 = W4;
    w5 = W5;
  }
  
  public String toString(){
    return w1 + "|" + w2 + "|" + w3 + "|" + w4 + "|" + w5;
  }
}
