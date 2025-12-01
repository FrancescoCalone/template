package apsd.classes.containers.collections.concretecollections;

 import apsd.classes.containers.collections.concretecollections.bases.VChainBase;
import apsd.classes.utilities.Natural;
 import apsd.interfaces.containers.base.TraversableContainer;
 import apsd.interfaces.containers.collections.SortedChain;
 import apsd.interfaces.containers.sequences.DynVector;


/** Object: Concrete set implementation via (dynamic circular) vector. */
public class VSortedChain<Data extends Comparable<? super Data>> extends VChainBase<Data> implements SortedChain<Data> {

  public VSortedChain() {
    super();
  }

  public VSortedChain(VSortedChain<Data> chn) {
    super(chn);
  }

  public VSortedChain(TraversableContainer<Data> con) {
    super(con);
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
    return InsertIfAbsent(val);
  }
  
    

  /* ************************************************************************ */
  /* Override specific member functions from Chain                            */
  /* ************************************************************************ */

  @Override
  public boolean InsertIfAbsent(Data val) {
    if (Search(val) == null) {
       if(val==null) throw new IllegalArgumentException("Null data cannot be inserted.");
      Natural index=SearchPredecessor(val);
      if(vec.Size().equals(vec.Capacity())) {
              vec.Expand(Natural.ONE);
      }
      if(index==null){
          vec.ShiftFirstRight(Natural.ZERO);
          vec.InsertFirst(val);
      }else if(index.compareTo(Natural.Of((Size().ToLong()/2))) < 0) {
          vec.ShiftRight(index);
          vec.InsertAt(val,index);
      }else{
          vec.ShiftLastRight(index);
          vec.InsertAt(val,index);
      }
      return true;
    } else {
      return false; 
  }

  }

}


