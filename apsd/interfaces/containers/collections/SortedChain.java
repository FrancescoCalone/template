package apsd.interfaces.containers.collections;

import apsd.classes.utilities.Natural;
import apsd.interfaces.containers.sequences.SortedSequence;


public interface SortedChain<Data extends Comparable<Data>> extends OrderedChain<Data>, SortedSequence<Data> { // Must extend OrderedChain and SortedSequence

  // SearchPredecessor
  default Natural SearchPredecessor(Data element) {
    Data pred=Predecessor(element);
    if (pred==null) return Natural.ZERO.Decrement();
    Natural index=Natural.ZERO;
    while (index.compareTo(Size()) < 0) {
      if (GetAt(index).equals(pred)) return index;
      index=index.Increment();
    }
    return Natural.ZERO.Decrement();
  }

  // SearchSuccessor
  default Natural SearchSuccessor(Data element) {
    Data succ=Successor(element);
    if (succ==null) return Natural.ZERO.Decrement();
    Natural index=Natural.ZERO;
    while (index.compareTo(Size()) < 0) {
      if (GetAt(index).equals(succ)) return index;
      index=index.Increment();
    }
    return Natural.ZERO.Decrement();
  }

  /* ************************************************************************ */
  /* Override specific member functions from Sequence                         */
  /* ************************************************************************ */
  @Override
  default Natural Search(Data value) {
    if (value == null) throw new IllegalArgumentException("Value nullo");
    Natural left = Natural.Of(0);
    Natural right = Natural.Of(Size().ToLong() - 1);
    while (left.ToLong() <= right.ToLong()) {
      Natural mid = Natural.Of((left.ToLong() + right.ToLong()) / 2);
      Data midValue = GetAt(mid);
      int cmp = midValue.compareTo(value);
      if (cmp == 0) {
        return mid;
      } else if (cmp < 0) {
        left = Natural.Of(mid.ToLong() + 1);
      } else {
        right = Natural.Of(mid.ToLong() - 1);
      }
    }
    return Natural.Of(-1);
  }

  /* ************************************************************************ */
  /* Override specific member functions from Set                              */
  /* ************************************************************************ */

  default void Intersection(SortedChain<Data> chn) {
     Natural i = Natural.ZERO, j = Natural.ZERO;
     while (i.compareTo(Size()) < 0 && j.compareTo(chn.Size()) < 0) {
       int cmp = GetAt(i).compareTo(chn.GetAt(j));
       if (cmp < 0) {
         RemoveAt(i);
       } else {
         j = j.Increment();
         if (cmp == 0) { i = i.Increment(); }
       }
     }
     while (i.compareTo(Size()) < 0) {
       RemoveAt(i);
     }
   }

  /* ************************************************************************ */
  /* Override specific member functions from OrderedSet                       */
  /* ************************************************************************ */
  @Override
  default Data Min() {
      if (isEmpty()) return null;
      return GetAt(Natural.ZERO);
  }
 
  @Override
  default Data Max() {
      if (isEmpty()) return null;
      return GetAt(Natural.Of(Size().ToLong() - 1));
  }
    
  @Override
  default void RemoveMin() {
      if (!isEmpty()) {
          RemoveAt(Natural.ZERO);
      }
  }

  @Override
  default void RemoveMax() {
      if (!isEmpty()) {
          RemoveAt(Natural.Of(Size().ToLong() - 1));
      }
  }

  @Override
  default Data MinNRemove() {
      Data min = Min();
      RemoveMin();
      return min;
  }

  @Override
  default Data MaxNRemove() {
      Data max = Max();
      RemoveMax();
      return max;
  }

  @Override
  default Data Predecessor(Data element) {
      Natural index = SearchPredecessor(element);
      if (index.ToLong() == -1) return null;
      return GetAt(index);
  }

  @Override
  default Data Successor(Data element) {
      Natural index = SearchSuccessor(element);
      if (index.ToLong() == -1) return null;
      return GetAt(index);
  }

  @Override
  default void RemovePredecessor(Data x) {
      Natural index = SearchPredecessor(x);
      if (index.ToLong() != -1) {
          RemoveAt(index);
      }
  }

  @Override
  default void RemoveSuccessor(Data x) {
      Natural index = SearchSuccessor(x);
      if (index.ToLong() != -1) {
          RemoveAt(index);
      }
  }


  @Override
  default Data PredecessorNRemove(Data x) {
      Natural index = SearchPredecessor(x);
      if (index.ToLong() != -1) {
          Data pred = GetAt(index);
          RemoveAt(index);
          return pred;
      }
      return null;
  }

  @Override
  default Data SuccessorNRemove(Data x) {
      Natural index = SearchSuccessor(x);
      if (index.ToLong() != -1) {
          Data succ = GetAt(index);
          RemoveAt(index);
          return succ;
      }
      return null;
  }

  

}
