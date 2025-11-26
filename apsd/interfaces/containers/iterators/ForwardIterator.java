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
    /*
    long i=n.toLong()
    while( i>0 && IsValid()){
      DataNNext();
      i--;
     }*/
    Natural originalN = new Natural(n);
    ForEachForward(dat -> { originalN.Decrement(); return originalN.IsZero(); });
  }

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



