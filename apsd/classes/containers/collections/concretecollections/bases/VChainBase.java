package apsd.classes.containers.collections.concretecollections.bases;

import apsd.classes.containers.sequences.DynCircularVector;
import apsd.classes.utilities.Natural;
import apsd.interfaces.containers.base.TraversableContainer;
import apsd.interfaces.containers.collections.Chain;
import apsd.interfaces.containers.iterators.BackwardIterator;
import apsd.interfaces.containers.iterators.ForwardIterator;
import apsd.interfaces.containers.iterators.MutableForwardIterator;
import apsd.interfaces.containers.sequences.DynVector;
import apsd.interfaces.containers.sequences.Sequence;
import apsd.interfaces.traits.Predicate;


/** Object: Abstract list base implementation on (dynamic circular) vector. */
abstract public class VChainBase<Data> implements Chain<Data> { // Must implement Chain

  protected final DynVector<Data> vec;

  // VChainBase
  public VChainBase(Natural inisize) {
    vec = new DynCircularVector<>(inisize);
  }

  public VChainBase(TraversableContainer<Data> con) {
    vec = new DynCircularVector<Data>(con);
  }

  public VChainBase() {
    vec = new DynCircularVector<>();
  }
  

  // NewChain
  protected abstract VChainBase<Data> NewChain(DynVector<Data> vec1);
  /* ************************************************************************ */
  /* Override specific member functions from Container                        */
  /* ************************************************************************ */

  @Override
  public Natural Size() {
    return vec.Size();
  }
  
  /* ************************************************************************ */
  /* Override specific member functions from ClearableContainer               */
  /* ************************************************************************ */

   @Override
  public void Clear(){
    vec.Clear();
  }


  /* ************************************************************************ */
  /* Override specific member functions from RemovableContainer               */
  /* ************************************************************************ */

  @Override
  public boolean Remove(Data data) {
   final ForwardIterator<Data> it = vec.FIterator(); 
    while (it.IsValid()) {
      if (it.GetCurrent().equals(data)) {
          RemoveOccurrences(data);
          return true;
      }
      it.Next();
    }
    return false;
  }
  /* ************************************************************************ */
  /* Override specific member functions from IterableContainer                */
  /* ************************************************************************ */

  @Override
  public ForwardIterator<Data> FIterator() {
    return vec.FIterator();
  }

  @Override
  public BackwardIterator<Data> BIterator() {
    return vec.BIterator();
  }

  /* ************************************************************************ */
  /* Override specific member functions from Sequence                         */
  /* ************************************************************************ */

  @Override
  public Data GetAt(Natural pos) {
    return vec.GetAt(pos);
  }

  /* ************************************************************************ */
  /* Override specific member functions from RemovableAtSequence              */
  /* ************************************************************************ */

  @Override
  public Data AtNRemove(Natural pos) {
    return vec.AtNRemove(pos);
  }

  /* ************************************************************************ */
  /* Override specific member functions from Collection                       */
  /* ************************************************************************ */

  @Override
  public Sequence<Data> SubSequence(Natural start, Natural end) {
    return vec.SubSequence(start, end);
  }

  @Override
  public boolean Filter(Predicate<Data> pred) {
    boolean changed = false;
    MutableForwardIterator<Data> it = vec.FIterator();
    while (it.IsValid()) {
      if (!pred.Apply(it.GetCurrent())) {
        Remove(it.GetCurrent());
        changed = true;
      } else {
        it.Next();
      }
    }
    return changed;
  }



}
