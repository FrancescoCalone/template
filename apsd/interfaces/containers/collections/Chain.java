package apsd.interfaces.containers.collections;

import apsd.classes.utilities.Natural;
import apsd.interfaces.containers.sequences.RemovableAtSequence;



public interface Chain<Data> extends RemovableAtSequence<Data>,Set<Data> {
  // IsEmpty
  default boolean IsEmpty() { return Size() != null && Size().IsZero(); }

  // InsertIfAbsent
  default boolean InsertIfAbsent(Data item) {
    if (Exists(item)) return false;
    Insert(item);
    return true;
  }

  // RemoveOccurrences
  default void RemoveOccurrences(Data item) {
   Filter(dat -> (item == null ? dat != null : !item.equals(dat)) ); 
  }

  // SubChain
  default Chain<Data> SubChain(Natural start, Natural end) {
    return (Chain<Data>) SubSequence(start, end);
  }
  
  
  /* ************************************************************************ */
  /* Override specific member functions from Sequence                         */
  /* ************************************************************************ */
  
  @Override
  default Natural Search(Data value) {
    if (value == null) return null;
    return RemovableAtSequence.super.Search(value);
  }

  
}