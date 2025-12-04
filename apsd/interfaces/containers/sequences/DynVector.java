package apsd.interfaces.containers.sequences;

import apsd.classes.utilities.Natural;
import apsd.interfaces.containers.base.ResizableContainer;

public interface DynVector<Data> extends ResizableContainer, InsertableAtSequence<Data>, RemovableAtSequence<Data>, Vector<Data> {

  /* ************************************************************************ */
  /* Override specific member functions from InsertableAtSequence             */
  /* ************************************************************************ */

  @Override
  default void InsertAt(Data value, Natural position){
  if (value == null || position == null) return;
  long pos = position.ToLong();
  long size = Size().ToLong();
  if (pos < 0 || pos > size) return;
  if (pos == size) {
    // append at end: increase logical size first
    Expand();
    SetAt(value, Natural.Of(pos));
    return;
  }
  if (size + 1 > Capacity().ToLong()) Grow();
  ShiftRight(Natural.Of(pos), Natural.ONE);
  SetAt(value, Natural.Of(pos));
  }

  /* ************************************************************************ */
  /* Override specific member functions from RemovableAtSequence              */
  /* ************************************************************************ */

  @Override
  default Data AtNRemove(Natural position) {
  if (position == null) return null;
  long pos = position.ToLong();
  long size = Size().ToLong();
  if (pos < 0 || pos >= size) return null;
  Data old = GetAt(Natural.Of(pos));
  if (pos < size - 1) {
    // perform the physical shift without affecting logical size (call Vector default)
    Vector.super.ShiftLeft(Natural.Of(pos), Natural.ONE);
  }
  // clear physical last element and reduce logical size
  SetAt(null, Natural.Of(size - 1));
  Reduce(Natural.ONE);
  return old;
  }

  /* ************************************************************************ */
  /* Specific member functions of Vector                                       */
  /* ************************************************************************ */

  @Override
  default void ShiftLeft(Natural pos, Natural num) {
  if (pos == null || num == null) return;
  long p = pos.ToLong();
  long n = num.ToLong();
  long size = Size().ToLong();
  if (p < 0 || p >= size || n <= 0) return;
  // perform the physical shift then update logical size
  Vector.super.ShiftLeft(Natural.Of(p), Natural.Of(n));
  Reduce(Natural.Of(n));
  }



  @Override
  default void ShiftRight(Natural pos, Natural num) {
  if (pos == null || num == null) return;
  long p = pos.ToLong();
  long n = num.ToLong();
  long size = Size().ToLong();
  if (p < 0 || p >= size || n <= 0) return;
  // Always increase logical size first (Expand will grow capacity if needed)
  Expand(Natural.Of(n));
  Vector.super.ShiftRight(Natural.Of(p), Natural.Of(n));
  }
  

  @Override
  default Vector<Data> SubVector(Natural start, Natural end){
  long s = (start == null ? -1 : start.ToLong());
  long e = (end == null ? -1 : end.ToLong());
  long n = Size().ToLong();
  if (s < 0 || e < 0 || s > e || e >= n) return null;
  return Vector.super.SubVector(start, end);
  }
  
  /* ************************************************************************ */
  /* Override specific member functions from Container                        */
  /* ************************************************************************ */
  @Override
  abstract Natural Size();
}
