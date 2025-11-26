package apsd.interfaces.containers.collections;

import apsd.interfaces.containers.base.IterableContainer;


public interface Set<Data> extends Collection<Data>{ // Must extend Collection

  // Union
  default void Union(Set<Data> other){
    if (other == null) return;
    other.TraverseForward(dat -> {
      if (dat != null && !Exists(dat)) {
        Insert(dat);
      }
      return false; 
    });
  }

  // Difference
  default void Difference(Set<Data> other){
    if (other == null) return;
    other.TraverseForward(dat -> {
      if (dat != null && Exists(dat)) {
        Remove(dat);
      }
      return false; 
    });
  }

  // Intersection
  default void Intersection(Set<Data> other){
    if (other == null) return;
    TraverseForward(dat -> {
      if (dat != null && !other.Exists(dat)) {
        Remove(dat);
      }
      return false; 
    });
  }

  /* ************************************************************************ */
  /* Override specific member functions from IterableContainer                */
  /* ************************************************************************ */

  @Override
  default boolean IsEqual(IterableContainer<Data> other){
    if (other == null) return false;
     var itThis = FIterator();
     var itOther = other.FIterator();
    if (itThis == null || itOther == null) return false;
    while (itThis.IsValid() && itOther.IsValid()) {
      Data a = itThis.DataNNext();
      Data b = itOther.DataNNext();
      if (a == null ? b != null : !a.equals(b)) return false;
    }
    return !itThis.IsValid() && !itOther.IsValid();
  }

}

