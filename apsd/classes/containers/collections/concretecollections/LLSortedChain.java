package apsd.classes.containers.collections.concretecollections;

import apsd.classes.containers.collections.concretecollections.bases.LLChainBase;
import apsd.classes.containers.collections.concretecollections.bases.LLNode;
import apsd.classes.utilities.Natural;
import apsd.interfaces.containers.base.TraversableContainer;
import apsd.interfaces.containers.collections.SortedChain;


/** Object: Concrete sorted chain implementation on linked-list. */
public class LLSortedChain<Data extends Comparable<? super Data>> extends LLChainBase<Data> implements SortedChain<Data> {
  // IsEmpty
  public boolean IsEmpty() { return Size() != null && Size().IsZero(); }

  public LLSortedChain(){
    super();
  }

  // public LLSortedChain(LLSortedChain<Data> chn)
  public LLSortedChain(LLSortedChain<Data> chn){
    super(chn.size.ToLong(), chn.headref.Get(), chn.tailref.Get());
  }
  // public LLSortedChain(TraversableContainer<Data> con)
  public LLSortedChain(TraversableContainer<Data> con) {
    super(con);
  }

  // protected LLSortedChain(long size, LLNode<Data> head, LLNode<Data> tail)
   protected LLSortedChain(long size, LLNode<Data> head, LLNode<Data> tail) {
    super(size, head, tail);
  }
  // NewChain
  @Override
  protected LLChainBase<Data> NewChain(long size, LLNode<Data> head, LLNode<Data> tail) {
    return new LLSortedChain<>(size, head, tail);
  }
  
  /* ************************************************************************ */
  /* Specific member functions of LLSortedChain                               */
  /* ************************************************************************ */

  protected LLNode<Data> PredFind(Data val) {
    if (val == null || headref.Get() == null) return null;
    long left = 0;
    long right = size.ToLong() - 1;
    LLNode<Data> pred = null;
    while (left <= right) {
      long mid = (left + right) / 2;
      LLNode<Data> midNode = headref.Get();
      for (long i = 0; i < mid; i++) {
        if (midNode == null) break;
        midNode = midNode.GetNext().Get();
      }
      if (midNode == null) break;
      Comparable<? super Data> cmp = (Comparable<? super Data>) midNode.Get();
      int res = cmp.compareTo(val);
      if (res < 0) {
        pred = midNode;
        left = mid + 1;
      } else {
        right = mid - 1;
      }
    }
    return pred;
  }
  
  protected LLNode<Data> PredPredFind(Data val) {
    LLNode<Data> pred = PredFind(val);
    if (pred == null) return null;
    return PredFind(pred.Get());
  }

  protected LLNode<Data> PredSucFind(Data val) {
    LLNode<Data> pred = PredFind(val);
    if (pred == null) return null;
    return pred.GetNext().Get();
  }
  /* ************************************************************************ */
  /* Override specific member functions from InsertableContainer              */
  /* ************************************************************************ */
  @Override
  public boolean Insert(Data val){
      LLNode<Data> pred = PredFind(val);
      if (pred == null) {
        LLNode<Data> newNode = new LLNode<>(val, headref.Get());
        headref.Set(newNode);
        if (size.ToLong() == 0) {
        tailref.Set(newNode);
        }
        size.Increment();
        return true;
      } else {
        LLNode<Data> newNode = new LLNode<>(val, pred.GetNext().Get());
        pred.SetNext(newNode);
        if (pred == tailref.Get()) {
        tailref.Set(newNode);
        }
        size.Increment();
        return true;
      }
  }


  /* ************************************************************************ */
  /* Override specific member functions from RemovableContainer               */
  /* ************************************************************************ */
  
  @Override
  public boolean Remove(Data val){
      LLNode<Data> pred = PredFind(val);
      LLNode<Data> suc = PredSucFind(val);
      if(Search(val)==null) return false;
      if(pred==null){
          headref.Set(suc);
      }else{
          pred.SetNext(suc);
      }
      size.Decrement();
          return true;
  }
  

  /* ************************************************************************ */
  /* Override specific member functions from Sequence                         */
  /* ************************************************************************ */

  

  /* ************************************************************************ */
  /* Override specific member functions from SortedSequence                   */
  /* ************************************************************************ */

  @Override
  public Natural Search(Data val) {
    LLNode<Data> pred = PredFind(val);
    LLNode<Data> suc = PredSucFind(val);                
    if (pred != null && pred.Get().equals(val)) {
      long index = 0;
      LLNode<Data> current = headref.Get();
      while (current != null && !current.Get().equals(val)) {
        current = current.GetNext().Get();
        index++;
      }
      return Natural.Of(index);
    } else if (suc != null && suc.Get().equals(val)) {
      long index = 0;
      LLNode<Data> current = headref.Get();
      while (current != null && !current.Get().equals(val)) {
        current = current.GetNext().Get();
        index++;
      }
      return Natural.Of(index);
    } else {
      return null;
    }
  }

  @Override
  public Natural SearchPredecessor(Data val) {
    if (val == null || headref.Get() == null) return null;
    long left = 0;
    long right = size.ToLong() - 1;
    Long predIndex = null;
    while (left <= right) {
        long mid = (left + right) / 2;
        LLNode<Data> midNode = headref.Get();
        for (long i = 0; i < mid; i++) {
            if (midNode == null) break;
            midNode = midNode.GetNext().Get();
        }
        if (midNode == null) break;
        Comparable<? super Data> cmp = (Comparable<? super Data>) midNode.Get();
        int res = cmp.compareTo(val);
        if (res < 0) {
            predIndex = mid;
            left = mid + 1;
        } else {
            right = mid - 1;
        }
    }
    if (predIndex == null) return null;
    return Natural.Of(predIndex.longValue());
  }

    @Override
    public Natural SearchSuccessor(Data val) {
    if (val == null || headref.Get() == null) return null;
    long left = 0;
    long right = size.ToLong() - 1;
    Long sucIndex = null;
    while (left <= right) {
        long mid = (left + right) / 2;
        LLNode<Data> midNode = headref.Get();
        for (long i = 0; i < mid; i++) {
            if (midNode == null) break;
            midNode = midNode.GetNext().Get();
        }
        if (midNode == null) break;
        Comparable<? super Data> cmp = (Comparable<? super Data>) midNode.Get();
        int res = cmp.compareTo(val);
        if (res > 0) {
            sucIndex = mid; 
            right = mid - 1;
        } else {
            left = mid + 1;
        }
    }
    if (sucIndex == null) return null;
    return Natural.Of(sucIndex.longValue());
    }
  

  /* ************************************************************************ */
  /* Override specific member functions from OrderedSet                       */
  /* ************************************************************************ */

  @Override
  public boolean InsertIfAbsent(Data val) {
    if (Search(val) != null) return false;
    return Insert(val);
  }

  /* ************************************************************************ */
  /* Override specific member functions from Chain                            */
  /* ************************************************************************ */

  @Override
  public void RemoveOccurrences(Data val) {
    if (val == null || headref.Get() == null) return;
    LLNode<Data> dummy = new LLNode<>(null, headref.Get());
    LLNode<Data> prev = dummy;
    LLNode<Data> curr = headref.Get();
    while (curr != null && curr.Get().compareTo(val) < 0) {
      prev = curr;
      curr = curr.GetNext().Get();
    }
    while (curr != null && curr.Get().compareTo(val) == 0) {
      prev.SetNext(curr.GetNext().Get());
      size.Decrement();
      if (curr == tailref.Get()) {
        tailref.Set(prev == dummy ? null : prev);
      }
      curr = curr.GetNext().Get();
    }
    headref.Set(dummy.GetNext().Get());
    if (size.ToLong() == 0) {
      tailref.Set(null);
    }
  }
  
    @Override
  public Data Predecessor(Data element) {
    Natural index = SearchPredecessor(element);
    if (index == null || index.ToLong() == -1) return null;
    return GetAt(index);
  }

  @Override
  public Data Successor(Data element) {
    Natural index = SearchSuccessor(element);
    if (index == null || index.ToLong() == -1) return null;
    return GetAt(index);
  }

  @Override
  public void RemovePredecessor(Data x) {
      Data pred = Predecessor(x);
      if (pred != null) {
        Remove(pred);
      }
  }

  @Override
  public void RemoveSuccessor(Data x) {
      Data succ = Successor(x);
      if (succ != null) {
        Remove(succ);
      }
  }

  @Override
  public Data PredecessorNRemove(Data x) {
      Data pred = Predecessor(x);
      if (pred != null) {
        Remove(pred);
        return pred;
      }
      return null;
  }

  @Override
  public Data SuccessorNRemove(Data x) {
      Data succ = Successor(x);
      if (succ != null) {
        Remove(succ);
        return succ;
      }
      return null;
  }
 

}
