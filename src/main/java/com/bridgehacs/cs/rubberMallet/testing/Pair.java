package com.bridgehacs.cs.rubberMallet.testing;

public class Pair<L,R> {

	  private final L value;
	  private final R weight;

	  public Pair(L left, R right) {
	    this.value = left;
	    this.weight = right;
	  }

	  public L getLeft() { return value; }
	  public R getRight() { return weight; }

	  @Override
	  public int hashCode() { return value.hashCode() ^ weight.hashCode(); }

	  @Override
	  public boolean equals(Object o) {
	    if (!(o instanceof Pair)) return false;
	    Pair pairo = (Pair) o;
	    return this.value.equals(pairo.getLeft()) &&
	           this.weight.equals(pairo.getRight());
	  }

	}
