package apsd.interfaces.containers.collections;


public interface OrderedSet<Data extends Comparable<Data>> extends Set<Data> {

  // Min
  default Data Min(){
  if (isEmpty()) return null;
  return FIterator().GetCurrent();
  }

  // RemoveMin
  default void RemoveMin(){
    if (isEmpty()) return;
    Remove(Min());
  }

  // MinNRemove
  default Data MinNRemove(){
    Data min = Min();
    RemoveMin();
    return min;
  }

  // Max
  default Data  Max(){
  if (isEmpty()) return null;
  return BIterator().GetCurrent();
  }

  // RemoveMax
  default void RemoveMax(){
    if (isEmpty()) return;
    Remove(Max());
  }

  // MaxNRemove
  default Data MaxNRemove(){
    Data max = Max();
    RemoveMax();
    return max;
  }

  // Predecessor
  default Data Predecessor(Data element) {
    if (isEmpty() || element == null) return null;
    return FoldBackward((cur, res) -> {
      if (res != null) return res;
      if (cur == null) return null;
      return cur.compareTo(element) < 0 ? cur : null;
    }, null);
  }

  // RemovePredecessor
  default void RemovePredecessor(Data x) {
    Data pred = Predecessor(x);
    if (pred != null) {
      Remove(pred);
    }
  }

  // PredecessorNRemove
  default Data PredecessorNRemove(Data x) {
    Data pred = Predecessor(x);
    if (pred != null) {
      Remove(pred);
    }
    return pred;
  }

  // Successor
  default Data Successor(Data element) {
    if (isEmpty() || element == null) return null;
    return FoldForward((cur, res) -> {
      if (res != null) return res;
      if (cur == null) return null;
      return cur.compareTo(element) > 0 ? cur : null;
    }, null);
  }


  // RemoveSuccessor
  default void RemoveSuccessor(Data x) {
    Data succ = Successor(x);
    if (succ != null) {
      Remove(succ);
    }
  }

  // SuccessorNRemove
  default Data SuccessorNRemove(Data x) {
    Data succ = Successor(x);
    if (succ != null) {
      Remove(succ);
    }
    return succ;
  }

}
