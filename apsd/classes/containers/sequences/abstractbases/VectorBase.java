package apsd.classes.containers.sequences.abstractbases;

import apsd.classes.utilities.MutableNatural;
import apsd.classes.utilities.Natural;
import apsd.interfaces.containers.base.TraversableContainer;
import apsd.interfaces.containers.iterators.MutableBackwardIterator;
import apsd.interfaces.containers.iterators.MutableForwardIterator;
import apsd.interfaces.containers.sequences.MutableSequence;
import apsd.interfaces.containers.sequences.Vector;

/** Object: Abstract vector base implementation. */
abstract public class VectorBase<Data> implements Vector<Data> { // Must implement Vector

  protected Data[] arr;

  // VectorBase
  public  VectorBase(Natural size) {
    ArrayAlloc(size);
  }

  public VectorBase() {
    arr = null;
  }

  public VectorBase(Data[] arr) {
    this.arr = arr;
  }

  public VectorBase(TraversableContainer<Data> con) {
    if (con == null) {
      arr = null;
      return;
    }
    ArrayAlloc(con.Size());
    final MutableNatural pos = MutableNatural.ZERO;
    con.TraverseForward(data -> {
      SetAt(data, pos.GetNIncrement());
      return false;
    });
  }

  // NewVector
  protected abstract void  NewVector(Data[] arr1);


  @SuppressWarnings("unchecked")
  protected void ArrayAlloc(Natural newsize) {
     long size = newsize.ToLong();
     if (size >= Integer.MAX_VALUE) { throw new ArithmeticException("Overflow: size cannot exceed Integer.MAX_VALUE!"); }
     arr = (Data[]) new Object[(int) size];
   }

  /* ************************************************************************ */
  /* Override specific member functions from ClearableContainer               */
  /* ************************************************************************ */
  
  @Override
  public void Clear() {
    Realloc(Natural.ZERO);
  }
  
  /* ************************************************************************ */
  /* Override specific member functions from ResizableContainer               */
  /* ************************************************************************ */

  @Override
  public Natural Capacity() {
    return Natural.Of(arr.length);
  }

  /* ************************************************************************ */
  /* Override specific member functions from IterableContainer                */
  /* ************************************************************************ */
  @Override
  public MutableBackwardIterator<Data> BIterator() {
    return new MutableBackwardIterator<Data>() {
  private int index = (arr != null && arr.length > 0 ? arr.length - 1 : 0);
  private final int size = (arr != null ? arr.length : 0);

      @Override
      public boolean IsValid() {
        return arr != null && index >= 0 && index < size;
      }

      @Override
      public void Reset() {
      index = (arr != null && arr.length > 0 ? arr.length - 1 : 0);
      }

      @Override
      public Data GetCurrent() {
        if (!IsValid()) throw new IllegalStateException("Iterator terminated");
        return arr[index];
      }

      @Override
      public void Prev() {
        if (IsValid() && index > 0) {
          index--;
        }
      }

      @Override
      public void SetCurrent(Data data) {
        if (!IsValid()) throw new IllegalStateException("Iterator terminated");
        arr[index] = data;
      }

      @Override
      public Data DataNPrev() {
        if (!IsValid()) throw new IllegalStateException("Iterator terminated");
        Data d = arr[index];
        index--;
        return d;
      }
    };

  }

  @Override
  public MutableForwardIterator<Data> FIterator() {
    return new MutableForwardIterator<Data>() {
      private int index = 0;
      private final int size = (arr != null ? arr.length : 0);

      @Override
      public boolean IsValid() {
        return arr != null && index >= 0 && index < size;
      }

      @Override
      public void Reset() {
        index = 0;
      }

      @Override
      public void Next() {
        if (IsValid() && index < size - 1) {
          index++;
        }
      }

      @Override
      public Data GetCurrent() {
        if (!IsValid()) throw new IllegalStateException("Iterator terminated");
        return arr[index];
      }

      @Override
      public void SetCurrent(Data data) {
        if (!IsValid()) throw new IllegalStateException("Iterator terminated");
        arr[index] = data;
      }

      @Override
      public Data DataNNext() {
        if (!IsValid()) throw new IllegalStateException("Iterator terminated");
        Data d = arr[index];
        index++;
        return d;
      }
    };

  }
   
  
  /* ************************************************************************ */
  /* Override specific member functions from Sequence                         */
  /* ************************************************************************ */
   @Override
   abstract public Data GetAt(Natural n);
   
   @Override
   abstract public void SetAt(Data data, Natural n);

  /* ************************************************************************ */
  /* Override specific member functions from MutableSequence                  */
  /* ************************************************************************ */

  @Override
  public MutableSequence<Data> SubSequence(Natural start, Natural end) {
  long s = (start == null ? -1 : start.ToLong());
  long e = (end == null ? -1 : end.ToLong());
  long n = Size().ToLong();
  if (s < 0 || e < 0 || s > e || e >= n) return null;
    Vector<Data> sub = Vector.super.SubVector(start, end);
    return (MutableSequence<Data>) sub;
  }

}
