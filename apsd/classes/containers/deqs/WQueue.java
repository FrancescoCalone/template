
package apsd.classes.containers.deqs;

import apsd.classes.containers.collections.concretecollections.VList;
import apsd.classes.utilities.Natural;
import apsd.interfaces.containers.base.TraversableContainer;
import apsd.interfaces.containers.collections.List;
import apsd.interfaces.containers.deqs.Queue;


/** Object: Wrapper queue implementation. */
public class WQueue<Data> implements Queue<Data> { // Must implement Queue

  protected final List<Data> lst;

  public WQueue(){
    lst = new VList<Data>();
  }

  public WQueue(List<Data> lst) {
    this.lst = lst;
  }


  public WQueue(TraversableContainer<Data> con){
    lst = new VList<Data>(con);
  }

  public WQueue(List<Data> lst, TraversableContainer<Data> con) { 
    this.lst = lst;
    con.TraverseForward(data -> {
      lst.InsertAt(data, lst.Size());
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
    Queue.super.Clear();
  }

  /* ************************************************************************ */
  /* Override specific member functions from Queue                            */
  /* ************************************************************************ */
  
  @Override
  public Data Head() {
    return lst.GetFirst();
  }

  @Override
  public void Dequeue() {
    lst.RemoveFirst();
  }
  
  @Override
  public Data HeadNDequeue() {
    return  Queue.super.HeadNDequeue();

  }
  
  @Override
  public void Enqueue(Data itemData) {
    lst.InsertAt(itemData, lst.Size());
}
}
