package apsd.interfaces.containers.deqs;

import apsd.interfaces.containers.base.ClearableContainer;
import apsd.interfaces.containers.base.InsertableContainer;

public interface Queue<Data> extends ClearableContainer, InsertableContainer<Data>{ // Must extend ClearableContainer and InsertableContainer

  // Head
  Data head();
  
  // Dequeue
  void Dequeue();

  // HeadNDequeue
  default Data HeadNDeque(){
   if(!isEmpty()){
    Dequeue();
    return head();
   }
   else{
    throw new IllegalStateException("Queue vuota");
  }
  }


  // Enqueue
  void Enqueue(Data itemData);

  /* ************************************************************************ */
  /* Override specific member functions from ClearableContainer               */
  /* ************************************************************************ */

  @Override
  default void Clear() {
    while (!isEmpty()) {
      Dequeue();
    }
  }

  /* ************************************************************************ */
  /* Override specific member functions from InsertableContainer              */
  /* ************************************************************************ */

  @Override
  default boolean Insert(Data item){
    Enqueue(item);
    return true;
  }

}
