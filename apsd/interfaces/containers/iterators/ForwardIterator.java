package apsd.interfaces.containers.iterators;

import apsd.classes.utilities.Natural;
import apsd.interfaces.traits.Predicate;


/** Interface: Iteratore in avanti. */
public interface ForwardIterator<Data> extends Iterator<Data> {

  default void Next() {
    if (IsValid()) {
      DataNNext();
    } else {
      throw new IndexOutOfBoundsException("Iterator non valido");
    }
  }

  default void Next(Natural n) {
    if (n == null) throw new IllegalArgumentException("Natural nullo");
    if (n.IsZero() || !IsValid()) return;
    long i = n.ToLong();
    while (i > 0 && IsValid()) {
      DataNNext();
      i--;
     }
  }

  default void Next(long n) {
    if (n < 0) throw new IllegalArgumentException("Natural nullo");
    if (n == 0 || !IsValid()) return;
    long i = n;
    while (i > 0 && IsValid()) {
      Next();
      i--;
     }
  }

  // DataNNext
  Data DataNNext();

  default boolean ForEachForward(Predicate<Data> fun) {
    if (fun != null) {
      while (IsValid()) {
        if (fun.Apply(DataNNext())) return true;
      }
    }
    return false;
  }
}



