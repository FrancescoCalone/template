package apsd.interfaces.containers.collections;


public interface OrderedSet<Data extends Comparable<? super Data>> extends Set<Data> {

  // Min
  default Data Min() {
    return FoldForward((res, cur) -> {
      if (res == null) return cur;
      if (cur == null) return res;
      return cur.compareTo(res) < 0 ? cur : res;
    }, null);
  }

  // RemoveMin
  default void RemoveMin(){
    if (IsEmpty()) return;
    Remove(Min());
  }

  // MinNRemove
  default Data MinNRemove(){
    Data min = Min();
    RemoveMin();
    return min;
  }

  // Max
  default Data Max() {
    return FoldForward((res, cur) -> {
      if (res == null) return cur;
      if (cur == null) return res;
      return cur.compareTo(res) > 0 ? cur : res;
    }, null);
  }

  // RemoveMax
  default void RemoveMax(){
    if (IsEmpty()) return;
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
    if (IsEmpty() || element == null) return null;
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
    if (IsEmpty() || element == null) return null;
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
