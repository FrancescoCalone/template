package apsd.classes.containers.collections.concretecollections;

 import apsd.classes.containers.collections.concretecollections.bases.VChainBase;
import apsd.classes.utilities.Natural;
 import apsd.interfaces.containers.base.TraversableContainer;
 import apsd.interfaces.containers.collections.SortedChain;
 import apsd.interfaces.containers.sequences.DynVector;


/** Object: Concrete set implementation via (dynamic circular) vector. */
public class VSortedChain<Data extends Comparable<? super Data>> extends VChainBase<Data> implements SortedChain<Data> {
  // IsEmpty
  public boolean IsEmpty() { return Size() != null && Size().IsZero(); }

  public VSortedChain() {
    super();
  }

  public VSortedChain(VSortedChain<Data> chn) {
    super(chn);
  }

  public VSortedChain(TraversableContainer<Data> con) {
    super();
    if (con != null) {
      con.TraverseForward(d -> {
        this.InsertIfAbsent(d);
        return false;
      });
    }
  }

  protected VSortedChain(DynVector<Data> vec) {
    super(vec);
  }

  // NewChain
  @Override
  protected VSortedChain<Data> NewChain(DynVector<Data> vec) {
    return new VSortedChain<>(vec);
  }

  /* ************************************************************************ */
  /* Override specific member functions from InsertableContainer              */
  /* ************************************************************************ */
  
  @Override
  public boolean Insert(Data val){
    /*if (val == null) {
        throw new IllegalArgumentException();
    }

    Natural index = SearchPredecessor(val);

    if (vec.Size().equals(vec.Capacity())) { 
        vec.Expand(Natural.ONE);
    }

    if (index == null) {
      vec.ShiftFirstRight();
      vec.InsertFirst(val);
    } else {
    if (index.compareTo(new Natural(vec.Capacity().ToLong()/2)) > 0) {
        vec.ShiftRight(index); 
    } else {
        vec.ShiftLeft(index);      
    }
    vec.InsertAt(val, index); 
    }

    return true;
    */
    return InsertIfAbsent(val);
  }
  
    

  /* ************************************************************************ */
  /* Override specific member functions from Chain                            */
  /* ************************************************************************ */

  
  @Override
  public boolean InsertIfAbsent(Data data) {
    if (data == null) {
        throw new IllegalArgumentException();
    }

    System.out.println("[DEBUG VSortedChain.InsertIfAbsent] before Search/Insert data=" + data + " size=" + this.Size());

    // Avoid calling SortedSequence.Search directly (it may compute Size()-1 and throw for empty)
    Natural pred = SearchPredecessor(data);
    // Check whether the element already exists at successor position
    if (pred != null) {
      Natural pos = pred.Increment();
      if (pos.ToLong() < vec.Size().ToLong()) {
        Data curr = vec.GetAt(pos);
        if (curr != null && curr.compareTo(data) == 0) {
          System.out.println("[DEBUG VSortedChain.InsertIfAbsent] data exists: " + data + " size=" + this.Size());
          return false;
        }
      }
    } else {
      if (vec.Size().ToLong() > 0) {
        Data curr0 = vec.GetAt(Natural.ZERO);
        if (curr0 != null && curr0.compareTo(data) == 0) {
          System.out.println("[DEBUG VSortedChain.InsertIfAbsent] data exists: " + data + " size=" + this.Size());
          return false;
        }
      }
    }

    Natural index = pred != null ? pred.Increment() : Natural.ZERO;

    // Delegate to vec.InsertAt which handles capacity and shifting exactly once
    vec.InsertAt(data, index);

    System.out.println("[DEBUG VSortedChain.InsertIfAbsent] after Insert data=" + data + " size=" + this.Size());

    return true;

  }

  @Override
  public void RemoveOccurrences(Data data) {
    if (data == null) return;
    Natural pred = SearchPredecessor(data);
    Natural pos;
    if (pred == null) {
      pos = Natural.ZERO;
    } else {
      pos = pred.Increment();
    }
    while (pos.ToLong() < vec.Size().ToLong()) {
      Data curr = vec.GetAt(pos);
      if (curr == null) {
        break;
      }
      int cmp = curr.compareTo(data);
      if (cmp == 0) {
        vec.RemoveAt(pos); 
      } else {
        break; 
      }
    }
  }
}


