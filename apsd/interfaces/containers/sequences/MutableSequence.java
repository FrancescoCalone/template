package apsd.interfaces.containers.sequences;

import apsd.classes.utilities.Natural;
import apsd.interfaces.containers.base.MutableIterableContainer;
import apsd.interfaces.containers.iterators.MutableForwardIterator;

/** Interface: Sequence & MutableIterableContainer con supporto alla scrittura tramite posizione. */
public interface MutableSequence<Data> extends Sequence<Data>, MutableIterableContainer<Data> { // Must extend Sequence and MutableIterableContainer

  // SetAt
  default void SetAt(Data value, Natural position){
    long index=ExcIfOutOfBound(position);
    if (value == null) return;
    MutableForwardIterator<Data> it = FIterator();
    it.Next(Natural.Of(index));
    it.SetCurrent(value);
  }

  // GetNSetAt
  default Data GetNSetAt(Data value, Natural position){
    long index=ExcIfOutOfBound(position);
    MutableForwardIterator<Data> it = FIterator();
    it.Next(Natural.Of(index));
    Data oldValue = (Data)it.GetCurrent();
    if (value != null) it.SetCurrent(value);
    return oldValue;
  }

  // SetFirst
  default void SetFirst(Data value){
    SetAt(value, Natural.ZERO);
  }

  // GetNSetFirst
  default Data GetNSetFirst(Data value){
    return GetNSetAt(value, Natural.ZERO);
  }

  // SetLast
  default void SetLast(Data value){
    if (Size().IsZero()) throw new IndexOutOfBoundsException("Sequence vuota!");
    SetAt(value, Natural.Of(Size().ToLong()-1));
  }

  // GetNSetLast
  default Data GetNSetLast(Data value){
    if (Size().IsZero()) throw new IndexOutOfBoundsException("Sequence vuota!");
    return GetNSetAt(value, Natural.Of(Size().ToLong() - 1));
  }

  // Swap
  default void Swap(Natural pos1, Natural pos2){
   Data temp = GetAt(pos1);
   SetAt(GetAt(pos2), pos1);
   SetAt(temp, pos2);
  }


  /* ************************************************************************ */
  /* Override specific member functions from Sequence                         */
  /* ************************************************************************ */

  @Override
  MutableSequence<Data> SubSequence(Natural start, Natural end);

}
