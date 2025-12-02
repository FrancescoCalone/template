package apsd.classes.containers.sequences.abstractbases;

import apsd.classes.utilities.Natural;
import apsd.interfaces.containers.base.TraversableContainer;
import apsd.interfaces.containers.sequences.DynVector;

/** Object: Abstract dynamic circular vector base implementation. */
abstract public class DynCircularVectorBase<Data> extends CircularVectorBase<Data> implements DynVector<Data> {

  protected long size = 0L;

  public  DynCircularVectorBase(TraversableContainer<Data> con) {
    super(con);
  }
  /* ************************************************************************ */
  /* Override specific member functions from Container                        */
  /* ************************************************************************ */

 @Override
  public Natural Size() {
    return Natural.Of(size);
  }

  @Override
  public void ShiftLeft(Natural pos, Natural num){
    long idx = ExcIfOutOfBound(pos);   
    long len = num.ToLong();
    long sizeLong = size;
    if (len <= 0) return;
    if (len > sizeLong - idx) {
      len = sizeLong - idx;
    }
    for (long i = idx; i < sizeLong - len; i++) {
      SetAt(GetAt(Natural.Of(i + len)), Natural.Of(i));
    }
    for (long i = sizeLong - len; i < sizeLong; i++) {
      SetAt(null, Natural.Of(i));
    }
    size -= len;
  }

  // ShiftRight
  @Override
  public void ShiftRight(Natural pos, Natural num) {
    if (pos == null || num == null) throw new NullPointerException();
    long len = num.ToLong();
    if (len <= 0) return;
    long sz = size;
    long idx = pos.ToLong();
    if (idx < 0 || idx > sz) throw new IndexOutOfBoundsException("Index out of bounds: " + idx + "; Size: " + sz);
    if (size + len > Capacity().ToLong()) {
      Expand(Natural.Of(len)); 
    } else {
      size += len; 
    }
    long oldSize = sz;
    long cap = Capacity().ToLong();
    for (long i = oldSize - 1; i >= idx; i--) {
      SetAt(GetAt(Natural.Of(i)), Natural.Of(i + len));
    }
    for (long i = idx; i < idx + len; i++) {
      SetAt(null, Natural.Of(i));
    }
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
  Data[] oldArr = arr;
  long oldStart = start;
  long oldSize = size;
  ArrayAlloc(newsize);
  start = 0L;
  long copySize = Math.min(oldSize, nsize);
  for (long i = 0; i < copySize; i++) {
    arr[(int)i] = oldArr[(int)((oldStart + i) % oldArr.length)];
  }
  size = copySize;
    
   
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
      Grow(newcapacity);
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
