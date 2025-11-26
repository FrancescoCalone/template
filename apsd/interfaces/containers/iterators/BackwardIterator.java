package apsd.interfaces.containers.iterators;

import apsd.classes.utilities.Natural;
import apsd.interfaces.traits.Predicate;

/** Interface: Iteratore all'indietro. */
public interface BackwardIterator<Data> extends Iterator<Data> { // Must extend Iterator parametrizzato

  // Prev
  default void Prev() {
    if (IsValid()) {
      DataNPrev();
    } else {
      throw new IndexOutOfBoundsException("Iterator non valido");
    }
  }

  default void Prev(Natural n) {
    if (n == null) throw new IllegalArgumentException("Natural nullo");
    if (n.IsZero() || !IsValid()) return;
    long i = n.ToLong();
    while (i > 0 && IsValid()) {
      DataNPrev();
      i--;
     }
    }

    default void Prev(Long n) {
    if (n == null) throw new IllegalArgumentException("Natural nullo");
    if (n == 0 || !IsValid()) return;
    long i = n;
    while (i > 0 && IsValid()) {
      DataNPrev();
      i--;
     }
    }


  // DataNPrev
  Data DataNPrev();
   
  // ForEachBackward
  default boolean ForEachBackward(Predicate<Data> fun) {
    if (fun != null) {
      while (IsValid()) {
        if (fun.Apply(DataNPrev())) return true;
      }
    }
    return false;
  }
}



