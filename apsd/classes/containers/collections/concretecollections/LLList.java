package apsd.classes.containers.collections.concretecollections;

import apsd.classes.containers.collections.concretecollections.bases.LLChainBase;
import apsd.classes.containers.collections.concretecollections.bases.LLNode;
import apsd.classes.utilities.Box;
import apsd.classes.utilities.Natural;
import apsd.interfaces.containers.base.TraversableContainer;
import apsd.interfaces.containers.collections.List;
import apsd.interfaces.containers.iterators.ForwardIterator;
import apsd.interfaces.containers.iterators.MutableBackwardIterator;
import apsd.interfaces.containers.iterators.MutableForwardIterator;
import apsd.interfaces.containers.sequences.MutableSequence;


/** Object: Concrete list implementation on linked-list. */
public class LLList<Data> extends LLChainBase<Data> implements List<Data> {

  // public LLList()
  public LLList() {
    super();
  }

  // public LLList(TraversableContainer<Data> con)
  public LLList(TraversableContainer<Data> con) {
    super(con);
  }

  protected LLList(long size, LLNode<Data> head, LLNode<Data> tail) {
    super(size, head, tail);
  }

  // NewChain
  @Override
  protected LLList<Data> NewChain(LLNode<Data> head1, LLNode<Data> tail1) {
    return new LLList<>(this.size.ToLong(), head1, tail1);
  }
  /* ************************************************************************ */
  /* Override specific member functions from MutableIterableContainer         */
  /* ************************************************************************ */

  @Override
  public MutableForwardIterator<Data> FIterator() {
    return new MutableForwardIterator<Data>() {
       private LLNode<Data> current = headref.Get();

      @Override
      public boolean IsValid() {
        return current != null;
      }

      @Override
      public void Reset() {
        current = headref.Get();
      }

      @Override
      public Data GetCurrent() {
        if (!IsValid()) throw new IllegalStateException("Iterator terminated");
        return current.Get();
      }

      @Override
      public void Next() {
        if (IsValid()) {
          current = current.GetNext().Get();
        }
      }

      @Override
      public Data DataNNext() {
        if (!IsValid()) throw new IllegalStateException("Iterator terminated");
        Data value = current.Get();
        Next();
        return value;
      }

      @Override
      public void SetCurrent(Data data) {
        if (!IsValid()) throw new IllegalStateException("Iterator terminated");
        current.Set(data);
      }
    };

  }

  @Override
  public MutableBackwardIterator<Data> BIterator() {
    return new MutableBackwardIterator<Data>() {
      private LLNode<Data> current = tailref.Get(); 
      @Override
      public boolean IsValid() {
        return current != null;
      }

      @Override
      public void Reset() {
        current = tailref.Get();
      }

      @Override
      public Data GetCurrent() {
        if (!IsValid()) throw new IllegalStateException("Iterator terminated");
        return current.Get(); 
      }

      @Override
      public void Prev() {
        if (IsValid()) {
          LLNode<Data> temp = headref.Get();
          LLNode<Data> prev = null;
          while (temp != null && temp != current) {
            prev = temp;
            temp = temp.GetNext().Get();
          }
          current = prev;
        }
      }
      
      @Override
      public void SetCurrent(Data data) {
        if (!IsValid()) throw new IllegalStateException("Iterator terminated");
        current.Set(data);
      }

      @Override
      public Data DataNPrev() {
        if (!IsValid()) throw new IllegalStateException("Iterator terminated");
        Data value = current.Get();
        Prev();
        return value; 
      }
    };
  } 

  /* ************************************************************************ */
  /* Override specific member functions from MutableSequence                  */
  /* ************************************************************************ */

  @Override
  public MutableSequence<Data> SubSequence(Natural from, Natural to) {
    if (from.ToLong() < 0 || to.ToLong() > Size().ToLong() || from.ToLong() > to.ToLong()) {
      throw new IndexOutOfBoundsException("Invalid subsequence range.");
    }
    LLNode<Data> current = headref.Get();
    for (long i = 0; i < from.ToLong(); i++) {
      current = current.GetNext().Get();
    }
    LLNode<Data> subHead = null;
    LLNode<Data> subTail = null;
    LLNode<Data> prevNode = null;
    for (long i = from.ToLong(); i < to.ToLong(); i++) {
      LLNode<Data> newNode = new LLNode<>(current.Get());
      if (subHead == null) {
        subHead = newNode;
      } else {
        prevNode.SetNext(newNode);
      }
      prevNode = newNode;
      if (i == to.ToLong() - 1) {
        subTail = newNode;
      }
      current = current.GetNext().Get();
    }
    return new LLList<>(to.ToLong() - from.ToLong(), subHead, subTail);
  }

  /* ************************************************************************ */
  /* Override specific member functions from InsertableAtSequence             */
  /* ************************************************************************ */

  @Override
  public void InsertAt( Data val, Natural index) {
    if(index.ToLong()<0 || index.ToLong()>this.Size().ToLong()){
        throw new IndexOutOfBoundsException("Index out of bounds.");
    }
    if (index.ToLong() == 0) {
      LLNode<Data> newNode = new LLNode<>(null);
      newNode.SetNext(headref.Get());
      headref.Set(newNode);
      if (size.ToLong() == 0) {
        tailref.Set(newNode);
      }
    } else {
      LLNode<Data> prev = headref.Get();
      for (long i = 0; i < index.ToLong() - 1; i++) {
        prev = prev.GetNext().Get();
      }
      LLNode<Data> newNode = new LLNode<>(null);
      newNode.SetNext(prev.GetNext().Get());
      prev.SetNext(newNode);
      if (index.ToLong() == size.ToLong()) {
        tailref.Set(newNode);
      }
    }
    SetAt(val, index);
    size.Increment();
  }

}

