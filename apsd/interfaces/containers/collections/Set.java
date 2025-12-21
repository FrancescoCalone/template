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
    if (other == this) { 
      Clear();
      return;
    }
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
    Filter(dat -> other.Exists(dat));
  }

  /* ************************************************************************ */
  /* Override specific member functions from IterableContainer                */
  /* ************************************************************************ */

  @Override
  default boolean IsEqual(IterableContainer<Data> other){
    if (other == null) return false;
    var itThis = FIterator();
    if (itThis == null) return false;
    while (itThis.IsValid()) {
      Data a = itThis.DataNNext();
      if (!other.Exists(a)) return false;
    }
    var itOther = other.FIterator();
    if (itOther == null) return false;
    while (itOther.IsValid()) {
      Data b = itOther.DataNNext();
      if (!Exists(b)) return false;
    }

    return true;
  }

}

