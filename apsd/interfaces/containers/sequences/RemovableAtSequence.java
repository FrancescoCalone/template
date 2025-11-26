package apsd.interfaces.containers.sequences;


import apsd.classes.utilities.Natural;


/** Interface: Sequence con supporto alla rimozione di un dato tramite posizione. */
public interface RemovableAtSequence<Data>  extends Sequence<Data> { // Must extend Sequence

  // RemoveAt
  default void RemoveAt(Natural position){
    if(IsInBound(position)) {
      AtNRemove(position);
    }
  }

  // AtNRemove
  Data AtNRemove(Natural position);

  // RemoveFirst
  default void RemoveFirst(){
    RemoveAt(Natural.ZERO);
  }
  // FirstNRemove
  default Data FirstNRemove(){
    return AtNRemove(Natural.ZERO);
  }

  // RemoveLast
  default void RemoveLast(){
    RemoveAt (isEmpty() ? Natural.ZERO : Size().Decrement());
  }

  // LastNRemove
  default Data LastNRemove(){
    return AtNRemove(isEmpty() ? Natural.ZERO : Size().Decrement());
  }

}
