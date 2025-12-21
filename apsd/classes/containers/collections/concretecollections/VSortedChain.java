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
    if (val == null) return false;

    // Insert (allow duplicates) at successor of predecessor
    Natural pred = SearchPredecessor(val);
    Natural index = pred != null ? pred.Increment() : Natural.ZERO;
    vec.InsertAt(val, index);
    return true;
  }
  
    

  /* ************************************************************************ */
  /* Override specific member functions from Chain                            */
  /* ************************************************************************ */

  
  @Override
  public boolean InsertIfAbsent(Data data) {
    if (data == null) return false;
    if (vec.Size().ToLong() == 0) {
      vec.Expand(Natural.ONE);
      vec.SetAt(data, Natural.ZERO);
      return true;
    }
    Natural pred = SearchPredecessor(data);
    Natural pos;
    if (pred == null) {
      pos = Natural.ZERO;
    } else {
      Data predData = GetAt(pred);
      int cmp = predData.compareTo(data);
      if (cmp == 0) return false; 
      pos = pred.Increment(); 
    }
    if (pos.ToLong() < vec.Size().ToLong()) {
      Data atPos = vec.GetAt(pos);
      if (atPos.compareTo(data) == 0) return false;
    }
    vec.InsertAt(data, pos);
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


