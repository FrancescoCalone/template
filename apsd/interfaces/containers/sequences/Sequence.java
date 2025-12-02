package apsd.interfaces.containers.sequences;

import apsd.classes.utilities.Natural;
import apsd.interfaces.containers.base.IterableContainer;
import apsd.interfaces.containers.iterators.ForwardIterator;

/** Interface: IterableContainer con supporto alla lettura e ricerca tramite posizione. */
public interface Sequence<Data> extends IterableContainer<Data> { 
  
  // GetAt
  default Data GetAt(Natural num){
    if(IsEmpty()) throw new IndexOutOfBoundsException("Sequence vuota!");
    long idx = ExcIfOutOfBound(num);
    ForwardIterator<Data> it = FIterator();
    it.Next(Natural.Of(idx));
    return it.GetCurrent();
  }

  // GetFirst
  default Data GetFirst() {
    if (IsEmpty()) throw new IndexOutOfBoundsException("Sequence vuota!");
    return GetAt(Natural.ZERO);
  }
  
  // GetLast
  default Data GetLast() {
    if (IsEmpty()) throw new IndexOutOfBoundsException("Sequence vuota!");
    return GetAt(Natural.Of(Size().ToLong() - 1));
  }

  // Search
  default Natural Search(Data Value){
  final ForwardIterator<Data> it = FIterator();
  Natural idx = Natural.ZERO;
  while(it.IsValid()){
    final Data dat = it.DataNNext();
    if( (dat==null && Value==null) || (dat!=null && dat.equals(Value)) ){
      return idx;
    }
    idx = idx.Increment();
  }
  return null;
  }

  // IsInBound
  default boolean IsInBound(Natural num) {
  if (num == null) return false;
  long idx = num.ToLong();
  long size = Size().ToLong();
  return idx >= 0 && idx < size;
  }


  default long ExcIfOutOfBound(Natural num) {
    if (num == null) throw new NullPointerException("Natural number cannot be null!");
    long idx = num.ToLong();
    long size = Size().ToLong();
    if (idx >= size) throw new IndexOutOfBoundsException("Index out of bounds: " + idx + "; Size: " + size + "!");
    return idx;
  }

  // SubSequence
  Sequence<Data> SubSequence(Natural start, Natural end);

}
