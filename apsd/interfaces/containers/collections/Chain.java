package apsd.interfaces.containers.collections;

import apsd.classes.utilities.Natural;
import apsd.interfaces.containers.sequences.RemovableAtSequence;



public interface Chain<Data> extends RemovableAtSequence<Data>,Set<Data> {

  // InsertIfAbsent
  default boolean InsertIfAbsent(Data item) {
    if (Exists(item)) return false;
    Insert(item);
    return true;
  }

  // RemoveOccurrences
  default void RemoveOccurrences(Data item) {
    TraverseForward(dat -> {
      if (dat != null && dat.equals(item)) {
        Remove(dat);
      }
      return false; 
    });
  }

  // SubChain
  default Chain<Data> SubChain(Natural start, Natural end) {
    if (start == null || end == null) throw new NullPointerException("Indici null");
    long s = start.ToLong();
    long e = end.ToLong();
    if (s < 0 || e < 0 || s > e || e >= Size().ToLong()) throw new IndexOutOfBoundsException("Intervallo non valido: ["+s+","+e+"] size="+Size().ToLong());
    for (long i = Size().ToLong() - 1; i >= 0; i--) {
      if (i < s || i > e) {
        RemoveAt(Natural.Of(i));
      }
    }
    return this;
  }
  
  
  /* ************************************************************************ */
  /* Override specific member functions from Sequence                         */
  /* ************************************************************************ */
  
  @Override
  default Natural Search(Data value) {
    long size = Size().ToLong();
    for (long i = 0; i < size; i++) {
      Data dat = GetAt(Natural.Of(i));
      if ((dat == null && value == null) || (dat != null && dat.equals(value))) {
        return Natural.Of(i);
      }
    }
    return Natural.Of(-1);
  }

  
}