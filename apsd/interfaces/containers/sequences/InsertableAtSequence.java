package apsd.interfaces.containers.sequences;

import apsd.classes.utilities.Natural;

/** Interface: Sequence con supporto all'inserimento di un dato tramite posizione. */
public interface InsertableAtSequence<Data>  extends Sequence<Data> { 

  // InsertAt
  void InsertAt(Data value, Natural position);

  // InsertFirst
  default void InsertFirst(Data value) {
    InsertAt(value, Natural.ZERO);
  }

  // InsertLast
  default void InsertLast(Data value) {
    InsertAt(value, Natural.Of(Size().ToLong()));
  }

}
