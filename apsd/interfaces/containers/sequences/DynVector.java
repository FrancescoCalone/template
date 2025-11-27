package apsd.interfaces.containers.sequences;

import apsd.classes.utilities.Natural;
import apsd.interfaces.containers.base.ResizableContainer;

public interface DynVector<Data> extends ResizableContainer, InsertableAtSequence<Data>, RemovableAtSequence<Data>, Vector<Data> {

  /* ************************************************************************ */
  /* Override specific member functions from InsertableAtSequence             */
  /* ************************************************************************ */

  @Override
  default void InsertAt(Data value, Natural position){
    if (position == null || value == null) return;
    long pos = position.ToLong();
    if (pos < 0 || pos > Size().ToLong()) return;
    InsertAt(value, Natural.Of(pos));
  }

  /* ************************************************************************ */
  /* Override specific member functions from RemovableAtSequence              */
  /* ************************************************************************ */

  @Override
  default Data AtNRemove(Natural position) {
    if (position == null) return null;
    long pos = position.ToLong();
    if (pos < 0 || pos >= Size().ToLong()) return null;
    return AtNRemove(Natural.Of(pos));
  }

  /* ************************************************************************ */
  /* Specific member functions of Vector                                       */
  /* ************************************************************************ */

  @Override
  default void ShiftLeft(Natural pos, Natural num) {
    if (pos == null || num == null) return;
    long p = pos.ToLong();
    long n = num.ToLong();
    if (p < 0 || p >= Size().ToLong() || n < 0) return;
    Vector.super.ShiftLeft(pos, num);
    //reduce(num)
  }

  @Override
  default void ShiftRight(Natural pos, Natural num) {
    if (pos == null || num == null) return;
    long p = pos.ToLong();
    long n = num.ToLong();
    if (p < 0 || p >= Size().ToLong() || n < 0) return;
    Vector.super.ShiftRight(Natural.Of(p), Natural.Of(n));
    //Expand(num)
  }

  @Override
  default Vector<Data> SubVector(Natural start, Natural end){
  return (DynVector<Data>) Vector.super.SubVector(start, end);
  }
  
  /* ************************************************************************ */
  /* Override specific member functions from Container                        */
  /* ************************************************************************ */
  @Override
  abstract Natural Size();
}
