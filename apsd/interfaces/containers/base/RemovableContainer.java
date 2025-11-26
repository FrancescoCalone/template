package apsd.interfaces.containers.base;

/** Interface: Container con supporto alla rimozione di un dato. */
public interface RemovableContainer<Data> extends Container { 

  // Remove
  boolean Remove(Data item);

  // RemoveAll
  default boolean RemoveAll(TraversableContainer<Data> items) {
    if (items == null) return false;
    return items.FoldForward((dat, ok) -> ok && Remove(dat), true);
  }
 
   // RemoveSome
  default boolean RemoveSome(TraversableContainer<Data> items) {
    if (items == null) return false;
    return items.FoldForward((dat, ok) -> ok || Remove(dat), false);
  }
}
