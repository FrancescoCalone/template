package apsd.classes.containers.collections.concretecollections.bases;

import apsd.classes.utilities.Box;
import apsd.classes.utilities.MutableNatural;
import apsd.classes.utilities.Natural;
import apsd.interfaces.containers.base.TraversableContainer;
import apsd.interfaces.containers.collections.Chain;
import apsd.interfaces.containers.iterators.BackwardIterator;
import apsd.interfaces.containers.iterators.ForwardIterator;
import apsd.interfaces.containers.iterators.MutableBackwardIterator;
import apsd.interfaces.containers.iterators.MutableForwardIterator;
import apsd.interfaces.containers.sequences.Sequence;
import apsd.interfaces.traits.Predicate;

/** Object: Abstract chain base implementation on linked-list. */
abstract public class LLChainBase<Data> implements Chain<Data> { // Must implement Chain

  protected final MutableNatural size = new MutableNatural();
  protected final Box<LLNode<Data>> headref = new Box<>();
  protected final Box<LLNode<Data>> tailref = new Box<>();

  // LLChainBase
  public LLChainBase(TraversableContainer<Data> con) {
     size.Assign(con.Size());
     final Box<Boolean> first = new Box<>(true);
     con.TraverseForward(dat -> {
       LLNode<Data> node = new LLNode<>(dat);
       if (first.Get()) {
         headref.Set(node);
         first.Set(false);
       } else {
         tailref.Get().SetNext(node);
       }
       tailref.Set(node);
       return false;
     });
   }

  public LLChainBase() {
    size.Assign(Natural.ZERO);
    headref.Set(null);
    tailref.Set(null);
  }

 protected LLChainBase(long size, LLNode<Data> head, LLNode<Data> tail) {
    this.size.Assign(size);
    this.headref.Set(head);
    this.tailref.Set(tail);
  }
  // NewChain
  protected abstract LLChainBase<Data> NewChain(long size, LLNode<Data> head1, LLNode<Data> tail1);

  protected MutableForwardIterator<Box<LLNode<Data>>> FRefIterator() {
    return new MutableForwardIterator<Box<LLNode<Data>>>() {
      private Box<LLNode<Data>> current = headref;

      @Override
      public boolean IsValid() {
        return current.Get() != null;
      }

      @Override
      public void Reset() {
        current = headref;
      }

      @Override
      public Box<LLNode<Data>> GetCurrent() {
        if (!IsValid()) throw new IllegalStateException("Iterator terminated");
        return current;
      }

      @Override
      public void Next() {
        if (IsValid()) {
          current = current.Get().GetNext();
        }
      }

    
    @Override
    public Box<LLNode<Data>> DataNNext() {
    if (!IsValid()) throw new IllegalStateException("Iterator terminated");
    Box<LLNode<Data>> value = current;
    Next();
    return value;
    }

    @Override
    public void SetCurrent(Box<LLNode<Data>> node) {
      current = node;
    }
  };
}


  public MutableBackwardIterator<Box<LLNode<Data>>> BrefIterator() {
    return new MutableBackwardIterator<Box<LLNode<Data>>>() {
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
      public Box<LLNode<Data>> GetCurrent() {
        if (!IsValid()) throw new IllegalStateException("Iterator terminated");
        return new Box<>(current);
      }

      @Override
      public void Prev() {
        if (IsValid()) {
          LLNode<Data> node = headref.Get();
          LLNode<Data> prev = null;
          while (node != null && node != current) {
            prev = node;
            node = node.GetNext().Get();
          }
          current = prev;
        }
      }

      @Override
      public Box<LLNode<Data>> DataNPrev() {
        if (!IsValid()) throw new IllegalStateException("Iterator terminated");
        LLNode<Data> value = current;
        Prev();
        return new Box<>(value);
      }

      @Override
      public void SetCurrent(Box<LLNode<Data>> node) {
        current = node.Get();
      }
    };
  }

  /* ************************************************************************ */
  /* Specific member functions from LLChainBase                               */
  /* ************************************************************************ */

  @Override
  public Data GetFirst() {
    if(IsEmpty()){
      throw new IndexOutOfBoundsException("Sequence vuota!");
    }
    LLNode<Data> node = headref.Get();
    return node != null ? node.Get() : null;
  }


  @Override
  public Data GetLast() {
    if(IsEmpty()){
      throw new IndexOutOfBoundsException("Sequence vuota!");
    }
    LLNode<Data> node = tailref.Get();
    return node != null ? node.Get() : null;
  }

  /* ************************************************************************ */
  /* Override specific member functions from Container                        */
  /* ************************************************************************ */

  @Override
  public Natural Size() {
    return size.ToNatural();
  }
  

  /* ************************************************************************ */
  /* Override specific member functions from ClearableContainer               */
  /* ************************************************************************ */

  @Override
  public void Clear() {
    headref.Set(null);
    tailref.Set(null);
    size.Assign(Natural.ZERO);
  }

  /* ************************************************************************ */
  /* Override specific member functions from RemovableContainer               */
  /* ************************************************************************ */

  @Override
   public boolean Remove(Data dat) {
     if (dat == null) return false;
     final Box<LLNode<Data>> prd = new Box<>();
     return FRefIterator().ForEachForward(cur -> {
       LLNode<Data> node = cur.Get();
       if (node.Get().equals(dat)) {
         cur.Set(node.GetNext().Get());
         if (tailref.Get() == node) { tailref.Set(prd.Get()); }
         size.Decrement();
         return true;
       }
       prd.Set(node);
       return false;
     });
   }

  /* ************************************************************************ */
  /* Override specific member functions from IterableContainer                */
  /* ************************************************************************ */
  
  @Override
  public ForwardIterator<Data> FIterator() {
    
    return new ForwardIterator<Data>(){
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
    };

  }

  @Override
  public BackwardIterator<Data> BIterator() {
    return new BackwardIterator<Data>() {
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
          LLNode<Data> node = headref.Get();
          LLNode<Data> prev = null;
          while (node != null && node != current) {
            prev = node;
            node = node.GetNext().Get();
          }
          current = prev;
        }
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
  /* Override specific member functions from Sequence                         */
  /* ************************************************************************ */

  @Override
  public Sequence<Data> SubSequence(Natural startpos, Natural endpos) {
    long startindex = ExcIfOutOfBound(startpos);
    long endindex = ExcIfOutOfBound(endpos);
    if (startindex > endindex) {
      throw new IllegalArgumentException("Start position cannot be greater than end position!");
    }
    MutableForwardIterator<Box<LLNode<Data>>> it = FRefIterator();
    it.Next(Natural.Of(startindex));
    LLNode<Data> newhead = it.GetCurrent().Get();
    it.Next(Natural.Of(endindex - startindex));
    LLNode<Data> newtail = it.GetCurrent().Get();
    return NewChain(endindex - startindex + 1, newhead, newtail);
  }

  /* ************************************************************************ */
  /* Override specific member functions from RemovableAtSequence              */
  /* ************************************************************************ */

  @Override
  public Data AtNRemove(Natural pos) {
    final Box<Long> index = new Box<>(ExcIfOutOfBound(pos));
    final Box<Data> removedData = new Box<>();
    final Box<LLNode<Data>> prd = new Box<>();
    FRefIterator().ForEachForward(cur -> {
      LLNode<Data> node = cur.Get();
      if (index.Get() == 0) {
        removedData.Set(node.Get());
        cur.Set(node.GetNext().Get());
        if (tailref.Get() == node) { tailref.Set(prd.Get()); }
        size.Decrement();
        return true;
      }
      prd.Set(node);
      index.Set(index.Get() - 1);
      return false;
    });
    return removedData.Get();
  }
  
  @Override
  public void RemoveFirst() {
    if (headref.Get() != null) {
      headref.Set(headref.Get().GetNext().Get());
      size.Decrement();
      if (size.ToLong() == 0) {
        tailref.Set(headref.Get());
      }
    }
  }

  @Override
  public void RemoveLast() {
    if (headref.Get() == null) return;
    if (headref.Get() == tailref.Get()) {
      headref.Set(null);
      tailref.Set(null);
      size.Assign(0);
      return;
    }
    LLNode<Data> prev = headref.Get();
    while (prev.GetNext().Get() != tailref.Get()) {
      prev = prev.GetNext().Get();
    }
    prev.SetNext(null);
    tailref.Set(prev);
    size.Decrement();
  }

  @Override
  public Data FirstNRemove() {
    Data data = GetFirst();
    RemoveFirst();
    return data;
  }

  @Override
  public Data LastNRemove() {
    Data data = GetLast();
    RemoveLast();
    return data;
  }

  /* ************************************************************************ */
  /* Override specific member functions from Collection                       */
  /* ************************************************************************ */

  
  @Override
  public boolean Filter(Predicate<Data> pred) {
    boolean changed = false;
    final MutableForwardIterator<Box<LLNode<Data>>> it = FRefIterator();
    while (it.IsValid()) {
      LLNode<Data> node = it.GetCurrent().Get();
      if (!pred.Apply(node.Get())) {
        it.GetCurrent().Set(node.GetNext().Get());
        if (tailref.Get() == node) { tailref.Set(null); }
        size.Decrement();
        changed = true;
      } else {
        it.Next();
      }
    }
    return changed;
  }
}
