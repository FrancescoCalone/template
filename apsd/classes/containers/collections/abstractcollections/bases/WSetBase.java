
package apsd.classes.containers.collections.abstractcollections.bases;

import apsd.classes.utilities.Natural;
import apsd.interfaces.containers.base.TraversableContainer;
import apsd.interfaces.containers.collections.Chain;
import apsd.interfaces.containers.collections.Set;
import apsd.interfaces.containers.iterators.BackwardIterator;
import apsd.interfaces.containers.iterators.ForwardIterator;
import apsd.interfaces.traits.Predicate;

/** Object: Abstract wrapper set base implementation via chain. */
abstract public class WSetBase<Data, Chn extends Chain<Data>> implements Set<Data>  { // Must implement Set; Chn must extend Chain

  protected Chn chn;

  public  WSetBase(){
    ChainAlloc();
  }
  
  public  WSetBase(Chain<Data> chn){
    this.chn = (Chn) chn;
  }

  public  WSetBase(TraversableContainer<Data> con){
    ChainAlloc();
    final Natural initialSize = chn.Size();
    con.TraverseForward(data -> {
      chn.InsertIfAbsent(data);
      return false;
    });
  }


  public  WSetBase(Chain<Data> chn, TraversableContainer<Data> con){
    this.chn = (Chn) chn;
    final Natural initialSize = chn.Size();
    con.TraverseForward(data -> {
      chn.InsertIfAbsent(data);
      return false;
    });
  }

  // ChainAlloc
  protected abstract void ChainAlloc();

  /* ************************************************************************ */
  /* Override specific member functions from Container                        */
  /* ************************************************************************ */

  @Override
  public Natural Size() {
    return chn.Size();
  }

  /* ************************************************************************ */
  /* Override specific member functions from ClearableContainer               */
  /* ************************************************************************ */

  @Override
  public void Clear() {
    while (!chn.IsEmpty()) {
      chn.Remove(chn.GetAt(Natural.ZERO));
    }
  }

  /* ************************************************************************ */
  /* Override specific member functions from InsertableContainer              */
  /* ************************************************************************ */

  @Override
  public boolean Insert(Data data) {
    System.out.println("[DEBUG WSetBase.Insert] before Insert data=" + data + " size=" + (chn==null?"null":chn.Size()));
    boolean res = chn.InsertIfAbsent(data);
    System.out.println("[DEBUG WSetBase.Insert] after Insert data=" + data + " result=" + res + " size=" + (chn==null?"null":chn.Size()));
    return res;
  }

  /* ************************************************************************ */
  /* Override specific member functions from RemovableContainer               */
  /* ************************************************************************ */

  @Override
  public boolean Remove(Data data) {
    return chn.Remove(data);
  }

  /* ************************************************************************ */
  /* Override specific member functions from IterableContainer                */
  /* ************************************************************************ */

  @Override
  public ForwardIterator<Data> FIterator() {
    return chn.FIterator();
  }

  @Override
  public BackwardIterator<Data> BIterator() {
    return chn.BIterator();
  }

  /* ************************************************************************ */
  /* Override specific member functions from Collection                       */
  /* ************************************************************************ */

  @Override
  public boolean Filter(Predicate<Data> pred) {
    return chn.Filter(pred);
  }

  /* ************************************************************************ */
  /* Override specific member functions from Set                              */
  /* ************************************************************************ */

  public Set<Data> Intersection(Set<Data> other, Set<Data> result) {
    ForwardIterator<Data> it = other.FIterator();
    while (it.IsValid()) {
      Data val = it.GetCurrent();
      if (this.Exists(val)) {
        result.Insert(val);
      }
      it.Next();
    }
    return result;
  }

}
