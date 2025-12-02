package apsd.classes.containers.deqs;

import apsd.classes.containers.collections.concretecollections.VList;
import apsd.classes.utilities.Natural;
import apsd.interfaces.containers.base.TraversableContainer;
import apsd.interfaces.containers.collections.List;
import apsd.interfaces.containers.deqs.Stack;

/** Object: Wrapper stack implementation. */
public class WStack<Data> implements Stack<Data> { // Must implement Stack

  protected final List<Data> lst;

  public WStack(){
    lst = new VList<Data>();
  }

  public WStack(List<Data> lst){
    this.lst = lst;
  }

  public WStack(TraversableContainer<Data> con){
    lst = new VList<Data>(con);
  }
  public WStack(List<Data> lst, TraversableContainer<Data> con){ 
    this.lst = lst;
    final Natural initialSize = lst.Size();
    con.TraverseForward(data -> {
      lst.InsertAt(data, initialSize.Increment());
      return false;
    });
  }

  /* ************************************************************************ */
  /* Override specific member functions from Container                        */
  /* ************************************************************************ */

  @Override
  public Natural Size() {
    return lst.Size();
  }
  

  /* ************************************************************************ */
  /* Override specific member functions from ClearableContainer               */
  /* ************************************************************************ */

  @Override
  public void Clear() {
    Stack.super.Clear();
  }

  /* ************************************************************************ */
  /* Override specific member functions from Stack                            */
  /* ************************************************************************ */

  @Override
  public Data Top() {
    return lst.GetLast();
  }

  @Override
  public void Pop() {
    lst.RemoveLast();
  }

  @Override
  public Data TopNPop() {
    return Stack.super.TopNPop();
  }

  @Override
  public void Push(Data itemData) {
    lst.InsertAt(itemData, lst.Size());
  }
  
  @Override
  public void SwapTop(Data item){
    Stack.super.SwapTop(item);
  }

  @Override
  public Data TopNSwap(Data item){
    return Stack.super.TopNSwap(item);
  }
  
}
