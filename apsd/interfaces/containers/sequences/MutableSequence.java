package apsd.interfaces.containers.sequences;

import apsd.classes.utilities.Natural;
import apsd.interfaces.containers.base.MutableIterableContainer;
import apsd.interfaces.containers.iterators.MutableForwardIterator;

/** Interface: Sequence & MutableIterableContainer con supporto alla scrittura tramite posizione. */
public interface MutableSequence<Data> extends Sequence<Data>, MutableIterableContainer<Data> { // Must extend Sequence and MutableIterableContainer

  // SetAt
  default void SetAt(Data value, Natural position){
    long index=ExcIfOutOfBound(position);
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
    it.SetCurrent(value);
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
    SetAt(value, Natural.Of(Size().ToLong()-1));
  }

  // GetNSetLast
  default Data GetNSetLast(Data value){
    return GetNSetAt(value, Natural.Of(Size().ToLong() - 1));
  }

  // Swap
  default void Swap(Natural pos1, Natural pos2){
    long index1=ExcIfOutOfBound(pos1);
    long index2=ExcIfOutOfBound(pos2);
    if(index1==index2) return;
    MutableForwardIterator<Data> it = FIterator();
    it.Next(Natural.Of(Math.min(index1, index2)));
    Data val1 = (Data) it.GetCurrent();
    it.Next(Natural.Of(Math.abs(index2 - index1)));
    Data val2 = (Data)it.GetCurrent();
    it.SetCurrent(val1);
    it = FIterator();
    it.Next(Natural.Of(Math.min(index1, index2)));
    it.SetCurrent(val2);
  }


  /* ************************************************************************ */
  /* Override specific member functions from Sequence                         */
  /* ************************************************************************ */

  @Override
  MutableSequence<Data> SubSequence(Natural start, Natural end);

}
