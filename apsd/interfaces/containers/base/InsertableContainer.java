package apsd.interfaces.containers.base;

import apsd.classes.utilities.Box;

/** Interface: Container con supporto all'inserimento di un dato. */
public interface InsertableContainer<Data> extends Container { 

  // Insert
  boolean Insert(Data item);

  // InsertAll
  default boolean InsertAll(TraversableContainer<Data> items) {
    if (items==null) return false;
    final Box<Boolean> allOk = new Box<>(true);
    items.TraverseForward(dat -> { if (!Insert(dat)) allOk.Set(false); return false; });
    return allOk.Get();
  }
  
  // InsertSome 
  default boolean InsertSome(TraversableContainer<Data> items) {
    final Box<Boolean> someOk = new Box<>(false);
    if (items == null) return someOk.Get();
    items.TraverseForward(dat -> { if (Insert(dat)) { someOk.Set(true); return true; } return false; });
    return someOk.Get();
  } 

    
}