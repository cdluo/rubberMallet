package com.bridgehacs.cs.rubberMallet.testing;

public class Topic {

  private String w1;
  private String w2;
  private String w3;
  private String w4;
  private String w5;
  
  public Topic(String W1, String W2, String W3, String W4, String W5){
    w1 = W1;
    w2 = W2;
    w3 = W3;
    w4 = W4;
    w5 = W5;
  }
  
  public String getWord1(){
    return w1;
  }
  
  public String getWord2(){
    return w2;
  }
  
  public String getWord3(){
    return w3;
  }
  
  public String getWord4(){
    return w4;
  }
  
  public String getWord5(){
    return w5;
  }
  
  public String toString(){
    return w1 + "|" + w2 + "|" + w3 + "|" + w4 + "|" + w5;
  }
}
