package apsd.classes.containers.sequences.abstractbases;

import apsd.classes.utilities.Natural;
import apsd.interfaces.containers.sequences.DynVector;

/** Object: Abstract dynamic circular vector base implementation. */
abstract public class DynCircularVectorBase<Data> extends CircularVectorBase<Data> implements DynVector<Data> {

   protected long size = 0L;

  DynCircularVectorBase(Natural capacity) {
    super(capacity);
  }
  /* ************************************************************************ */
  /* Override specific member functions from Container                        */
  /* ************************************************************************ */

 @Override
  public Natural Size() {
    return Natural.Of(size);
  }

  @Override
  public void ShiftLeft(Natural num,Natural pos) {
    long n = num.ToLong();
    long p = pos.ToLong();
    long sz = Size().ToLong();
    if (n <= 0 || p < 0 || p >= sz) return;
    start = (start + n) % arr.length;
  }

  @Override
  public void ShiftRight(Natural num,Natural pos) { 
    long n = num.ToLong();
    long p = pos.ToLong();
    long sz = Size().ToLong();
    if (n <= 0 || p < 0 || p >= sz) return;
    start = (start - n + arr.length) % arr.length;
  }


  /* ************************************************************************ */
  /* Override specific member functions from ClearableContainer               */
  /* ************************************************************************ */

   @Override
  public void Clear() {
    super.Clear();
    size = 0L;
  }

  /* ************************************************************************ */
  /* Override specific member functions from ReallocableContainer             */
  /* ************************************************************************ */

  @Override 
  public void Realloc(Natural newsize) {
    long nsize = newsize.ToLong();
    if (nsize < 0) throw new IllegalArgumentException("Size negative");
    ArrayAlloc(newsize);
    if (size > nsize) 
    size = nsize;
  }

  /* ************************************************************************ */
  /* Override specific member functions from ResizableContainer               */
  /* ************************************************************************ */

  @Override
  public void Expand(Natural num) {
    if (num == null) return;
    long n = num.ToLong();
    long newsize = size + n;
    if (newsize > Capacity().ToLong()) {
      Natural newcapacity = Natural.Of(Math.max(newsize, Capacity().ToLong() * 2));
      ArrayAlloc(newcapacity);
    }
    size = newsize;
    }

    @Override
    public void Reduce(Natural num) {
        if (num == null) return;
        long n = num.ToLong();
        long newsize = size - n;
        if (newsize < 0) throw new IllegalArgumentException("Size negative");
        size = newsize;
    }

  /* ************************************************************************ */
  /* Specific member functions of Vector                                      */
  /* ************************************************************************ */

  @Override
  public void ArrayAlloc(Natural newcapacity) {
    long capacity = newcapacity.ToLong();
    if (capacity < 0) throw new IllegalArgumentException("Capacity negative");
    super.ArrayAlloc(newcapacity);
    start = 0L;
    size = Math.min(size, newcapacity.ToLong());
  }

}
