package apsd.classes.containers.sequences.abstractbases;

import apsd.classes.utilities.Natural;
import apsd.interfaces.containers.base.TraversableContainer;
import apsd.interfaces.containers.sequences.DynVector;

/** Object: Abstract dynamic linear vector base implementation. */
abstract public class DynLinearVectorBase<Data> extends LinearVectorBase<Data> implements  DynVector<Data>{ // Must extend LinearVectorBase and implement DynVector

  protected long size = 0L; // numero di elementi logici

  // DynLinearVectorBase
  public  DynLinearVectorBase(TraversableContainer<Data> con) {
    super(con);
    if (con != null) {
      this.size = con.Size().ToLong();
    }
  }
  public DynLinearVectorBase() {
    super();
  }
  public DynLinearVectorBase(Natural size) {
    super(size);
    this.size = size.ToLong();
  }
  
  public DynLinearVectorBase(Data[] arr) {
    super(arr);
    this.size = arr.length;
  }

  public void ArrayAlloc(Natural newcapacity) {
    long capacity = newcapacity.ToLong();

    super.ArrayAlloc(newcapacity);
  }
  /* ************************************************************************ */
  /* Override specific member functions from Container                        */
  /* ************************************************************************ */

  @Override
  public Natural Size() {
    return Natural.Of(size);
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
    Data[] oldArr = arr;
    if (nsize < 0) throw new IllegalArgumentException("Size negative");
    ArrayAlloc(newsize);
    size = size> nsize ? nsize : size;
    if (oldArr != null) {
      int copyLen = (int) Math.min(size, oldArr.length);
      for (int i = 0; i < copyLen; i++) {
        arr[i] = oldArr[i];
      }
    }
  }

  /* ************************************************************************ */
  /* Override specific member functions from ResizableContainer               */
  /* ************************************************************************ */

  @Override
  public void Expand(Natural num) {
    if (num == null) return;
    long n = num.ToLong();
    long newsize = size + n;
    if (newsize < 0) throw new ArithmeticException("Overflow: size cannot be negative!");
    size = newsize;
    if (Capacity().ToLong() < size) {
     Grow(num);
    }
  }

  @Override
  public void Reduce(Natural num) {
    if (num == null) return;
    long n = num.ToLong();
    if (n < 0) throw new IllegalArgumentException("Size negative");
    if (n > size) throw new IllegalArgumentException("Size to reduce exceeds current size");
    long newsize = size - n;
    size = newsize;
    if (Capacity().ToLong() > size * 4) {
    Shrink();
  }

}
}
