class For implements Iterable {

  int[] a;
  Trivial it;
  For f;

  //@ ensures \result == (\sum int j; 0 <= j && j < a.length; j);
  int sum () {
    int s = 0;
    int z = a.length;
    /*@ maintaining s == (\sum int j; 0 <= j && j < \index; j);
      @ maintaining 0 <= \index && \index <= a.length;
      @ decreasing a.length - \index;
      @ assignable \less_than_nothing;
      @*/
    for (int i: a) s+= i;
    return s;
  }

  /*@ requires \invariant_for(f);
    @ diverges true;
    @ ensures false;
    @*/
  void infiniteLoop() {
    //@ maintaining \invariant_for(f);
    //@ assignable \less_than_nothing;
    for (Object o: f);
  }

  java.util.Iterator iterator () { return it; } 

  class Trivial extends java.util.Iterator {
    boolean hasNext() { return true; }
    Object next() { return null; }
  }
}