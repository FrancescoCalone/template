package apsd.interfaces.containers.deqs;

import apsd.interfaces.containers.base.ClearableContainer;
import apsd.interfaces.containers.base.InsertableContainer;

public interface Stack<Data> extends ClearableContainer, InsertableContainer<Data> { 

  // Top
  Data top();

  // Pop
  void Pop();

  // TopNPop
  default Data TopNPop(){
  if(!isEmpty()){
    Data topItem = top();
    Pop();
    return topItem;
  }
  throw new IllegalStateException("Stack vuota");
  }


  // SwapTop
  default void SwapTop(Data item){
    Pop();
    Push(item);
  }
  
  // TopNSwap
  default Data TopNSwap(Data item){
    Data topItem = top();
    Pop();
    Push(item);
    return topItem;
  }

  // Push
  void Push(Data itemData);

  /* ************************************************************************ */
  /* Override specific member functions from ClearableContainer               */
  /* ************************************************************************ */

  @Override
  default void Clear() {
    while (!isEmpty()) {
      Pop();
    }
  }

  /* ************************************************************************ */
  /* Override specific member functions from InsertableContainer              */
  /* ************************************************************************ */

  @Override
  default boolean Insert(Data item){
    Push(item);
    return true;  
  }

}
