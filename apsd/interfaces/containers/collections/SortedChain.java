package apsd.interfaces.containers.collections;

import apsd.classes.utilities.Natural;
import apsd.interfaces.containers.sequences.SortedSequence;


public interface SortedChain<Data extends Comparable<? super Data>> extends OrderedChain<Data>, SortedSequence<Data> { // Must extend OrderedChain and SortedSequence

  // SearchPredecessor
  default Natural SearchPredecessor(Data element) {
    if (element == null) throw new IllegalArgumentException("Value nullo");
    long n = Size().ToLong();
    long left = 0, right = n - 1;
    long ans = -1; 
    while (left <= right) {
      long mid = (left + right)/2;
      Data midVal = GetAt(Natural.Of(mid));
      int cmp = midVal.compareTo(element);
      if (cmp < 0) {
        ans = mid; 
        left = mid + 1;
      } else {
        right = mid - 1;
      }
    }
    if (ans < 0) return null;
    return Natural.Of(ans);
  }

  // SearchSuccessor
  default Natural SearchSuccessor(Data element) {
    if (element == null) throw new IllegalArgumentException("Value nullo");
    long n = Size().ToLong();
    long left = 0, right = n - 1;
    long ans = -1; 
    while (left <= right) {
      long mid = (left + right) / 2;
      Data midVal = GetAt(Natural.Of(mid));
      int cmp = midVal.compareTo(element);
      if (cmp > 0) {
        ans = mid;
        right = mid - 1;
      } else {
        left = mid + 1;
      }
    }
    if (ans < 0) return null;
    return Natural.Of(ans);
  }

  /* ************************************************************************ */
  /* Override specific member functions from Sequence                         */
  /* ************************************************************************ */
  @Override
  default Natural Search(Data value) {
    if (value == null) throw new IllegalArgumentException("Value nullo");
    return SortedSequence.super.Search(value);
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
      if (IsEmpty()) return null;
      return GetAt(Natural.ZERO);
  }
 
  @Override
  default Data Max() {
      if (IsEmpty()) return null;
      return GetAt(Natural.Of(Size().ToLong() - 1));
  }
    
  @Override
  default void RemoveMin() {
      if (!IsEmpty()) {
          RemoveAt(Natural.ZERO);
      }
  }

  @Override
  default void RemoveMax() {
      if (!IsEmpty()) {
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
      if (index== null) return null;
      return GetAt(index);
  }

  @Override
  default Data Successor(Data element) {
      Natural index = SearchSuccessor(element);
      if (index == null) return null;
      return GetAt(index);
  }

  @Override
  default void RemovePredecessor(Data x) {
      Natural index = SearchPredecessor(x);
      if (index != null) {
          RemoveAt(index);
      }
  }

  @Override
  default void RemoveSuccessor(Data x) {
      Natural index = SearchSuccessor(x);
      if (index != null) {
          RemoveAt(index);
      }
  }


  @Override
  default Data PredecessorNRemove(Data x) {
      Natural index = SearchPredecessor(x);
      if (index != null) {
          Data pred = GetAt(index);
          RemoveAt(index);
          return pred;
      }
      return null;
  }

  @Override
  default Data SuccessorNRemove(Data x) {
      Natural index = SearchSuccessor(x);
      if (index != null) {
          Data succ = GetAt(index);
          RemoveAt(index);
          return succ;
      }
      return null;
  }
  

}
