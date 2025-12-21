package apsd.interfaces.containers.base;

import apsd.interfaces.containers.iterators.BackwardIterator;
import apsd.interfaces.containers.iterators.ForwardIterator;
import apsd.interfaces.traits.Predicate;

/** Interface: TraversableContainer con supporto all'iterazione. */
public interface IterableContainer<Data> extends TraversableContainer<Data> {

  // FIterator
  ForwardIterator<Data> FIterator();

  // BIterator
  BackwardIterator<Data> BIterator();

  // IsEqual
  default boolean IsEqual (IterableContainer<Data> other) {
      if (other == null) return false;
      if (other.IsEmpty() && IsEmpty()) return true;
      if (other.IsEmpty() || IsEmpty()) return false;
    final ForwardIterator<Data> itThis = FIterator();
    final ForwardIterator<Data> itOther = other.FIterator();
    if(itOther==null || itThis==null) return false;
    while(itThis.IsValid() && itOther.IsValid()) {
      final Data datThis = itThis.DataNNext();
      final Data datOther = itOther.DataNNext();
      if (datThis == null) {    
        if (datOther != null) return  false;
      } else {
        if (!datThis.equals(datOther)) return false;
      }
    }
    return !itThis.IsValid() && !itOther.IsValid();
  }

  /* ************************************************************************ */
  /* Override specific member functions from TraversableContainer             */
  /* ************************************************************************ */

  @Override
  default boolean TraverseForward(Predicate<Data> fun) {
    final ForwardIterator<Data> it = FIterator();
    if (fun != null && it != null) {
      return it.ForEachForward(fun);
    }
    return false;
  }

  @Override
  default boolean TraverseBackward(Predicate<Data> fun) {
    final BackwardIterator<Data> it = BIterator();
    if (fun != null && it != null) {
      return it.ForEachBackward(fun);
    }
    return false;
  }
}
