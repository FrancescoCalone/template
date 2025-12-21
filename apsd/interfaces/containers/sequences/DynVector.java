package apsd.interfaces.containers.sequences;

import apsd.classes.utilities.Natural;
import apsd.interfaces.containers.base.ResizableContainer;

public interface DynVector<Data> extends ResizableContainer, InsertableAtSequence<Data>, RemovableAtSequence<Data>, Vector<Data> {

  /* ************************************************************************ */
  /* Override specific member functions from InsertableAtSequence             */
  /* ************************************************************************ */

  @Override
  default void InsertAt(Data value, Natural position){
  if (value == null) return;
  if (position == null) throw new NullPointerException();
  long pos = position.ToLong();
  long size = Size().ToLong();
  if (pos < 0 || pos > size) throw new IndexOutOfBoundsException("Index out of bounds.");
  if (pos == size) {
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
  if (position == null) throw new NullPointerException();
  long pos = position.ToLong();
  long size = Size().ToLong();
  if (pos < 0 || pos >= size) throw new IndexOutOfBoundsException("Index out of bounds.");
  Data old = GetAt(Natural.Of(pos));
  if (pos < size - 1) {
    Vector.super.ShiftLeft(Natural.Of(pos), Natural.ONE);
  }
  SetAt(null, Natural.Of(size - 1));
  Reduce(Natural.ONE);
  return old;
  }

  /* ************************************************************************ */
  /* Specific member functions of Vector                                       */
  /* ************************************************************************ */

  @Override
  default void ShiftLeft(Natural pos, Natural num) {
  if (pos == null || num == null) throw new NullPointerException();
  long p = pos.ToLong();
  long n = num.ToLong();
  long size = Size().ToLong();
  if (p < 0 || p >= size || n <= 0) return;
  Vector.super.ShiftLeft(Natural.Of(p), Natural.Of(n));
  Reduce(Natural.Of(n));
  }



  @Override
  default void ShiftRight(Natural pos, Natural num) {
  if (pos == null || num == null) throw new NullPointerException();
  long p = pos.ToLong();
  long n = num.ToLong();
  long size = Size().ToLong();
  if (p < 0 || p >= size || n <= 0) return;
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
