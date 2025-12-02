  /** Restituisce il minimo elemento (solo ordered, non sorted) */
 
package apsd.classes.containers.collections.abstractcollections.bases;

import apsd.interfaces.containers.base.IterableContainer;
import apsd.interfaces.containers.base.TraversableContainer;
import apsd.interfaces.containers.collections.OrderedSet;
import apsd.interfaces.containers.collections.SortedChain;

/** Object: Abstract wrapper set base implementation via chain. */
abstract public class WOrderedSetBase<Data extends Comparable<? super Data>, Chn extends SortedChain<Data>> extends WSetBase<Data, Chn> implements OrderedSet<Data> { // Must extend WSetBase and implement OrderedSet; Chn must extend SortedChain

  // WOrderedSetBase
  public WOrderedSetBase() {
    super();
  }

  public WOrderedSetBase(TraversableContainer<Data> con) {
    super(con);
  }

  public WOrderedSetBase(Chn chn) {
    super(chn);
  }

  public WOrderedSetBase(Chn chn, TraversableContainer<Data> con) {
    super(chn, con);
  }

  /* ************************************************************************ */
  /* Override specific member functions from IterableContainer                */
  /* ************************************************************************ */

  @Override
  public boolean IsEqual(IterableContainer<Data> other) {
    return OrderedSet.super.IsEqual(other);
  }


  /* ************************************************************************ */
  /* Override specific member functions from OrderedSet                       */
  /* ************************************************************************ */

  @Override
  public Data Min() {
    return FoldForward((minSoFar, current) -> {
      if (minSoFar == null) return current;
      if (minSoFar.compareTo(current) > 0) {
        return current;
      } else {
        return minSoFar;
      }
    }, null);
  }

  @Override
  public Data Max() {
    return FoldForward((maxSoFar, current) -> {
      if (maxSoFar == null) return current;
      if (maxSoFar.compareTo(current) < 0) {
        return current;
      } else {
        return maxSoFar;
      }
    }, null);
  }

  @Override
  public Data MaxNRemove() {
    Data maxElem = Max();
    chn.Remove(maxElem);
    return maxElem;
  }

  @Override
  public Data MinNRemove() {
    Data minElem = Min();
    chn.Remove(minElem);
    return minElem;
  }

  @Override
  public Data Predecessor(Data element) {
    return chn.Predecessor(element);
  }

  @Override
  public Data Successor(Data element) {
    return chn.Successor(element);
  }

  @Override
  public void RemovePredecessor(Data element) {
      Data pred = Predecessor(element);
      if (pred != null) {
        chn.Remove(pred);
      }
  }

  @Override
  public void RemoveSuccessor(Data element) {
      Data succ = Successor(element);
      if (succ != null) {
        chn.Remove(succ);
      }
  }

  @Override
  public Data PredecessorNRemove(Data element) {
      Data pred = Predecessor(element);
      if (pred != null) {
        chn.Remove(pred);
      }
      return pred;
  }

  @Override
  public Data SuccessorNRemove(Data element) {
      Data succ = Successor(element);
      if (succ != null) {
        chn.Remove(succ);
      }
      return succ;
  }
  }