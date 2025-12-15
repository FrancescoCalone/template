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
    final MutableNatural pos = new MutableNatural();
    con.TraverseForward(data -> {
      long idx = pos.GetLongNIncrement();
      arr[(int) idx] = data;
      return false;
    });
  }

  // NewVector
  protected abstract VectorBase<Data>  NewVector(Data[] arr1);


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
    arr = null;
  }
  
  /* ************************************************************************ */
  /* Override specific member functions from ResizableContainer               */
  /* ************************************************************************ */

  @Override
  public Natural Capacity() {
    return arr == null ? Natural.ZERO: Natural.Of(arr.length);
  }

  /* ************************************************************************ */
  /* Override specific member functions from IterableContainer                */
  /* ************************************************************************ */
  @Override
  public MutableBackwardIterator<Data> BIterator() {
    return new MutableBackwardIterator<Data>() {
  private int index = (arr != null && Size().ToLong() > 0 ? (int)Size().ToLong() - 1 : 0);
  private final int size = (arr != null ? (int)Size().ToLong() : 0);

      @Override
      public boolean IsValid() {
        return arr != null && index >= 0 && index < size;
      }

      @Override
      public void Reset() {
      index = (arr != null && Size().ToLong() > 0 ? (int)Size().ToLong() - 1 : 0);
      }

      @Override
      public Data GetCurrent() {
        if (!IsValid()) throw new IllegalStateException("Iterator terminated");
        return GetAt(Natural.Of(index));
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
        SetAt(data, Natural.Of(index));
      }

      @Override
      public Data DataNPrev() {
        if (!IsValid()) throw new IllegalStateException("Iterator terminated");
        Data d = GetAt(apsd.classes.utilities.Natural.Of(index));
        index--;
        return d;
      }
    };

  }

  @Override
  public MutableForwardIterator<Data> FIterator() {
    return new MutableForwardIterator<Data>() {
      private int index = 0;
      private final int size = (arr != null ? (int)Size().ToLong() : 0);

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
        return GetAt(apsd.classes.utilities.Natural.Of(index));
      }

      @Override
      public void SetCurrent(Data data) {
        if (!IsValid()) throw new IllegalStateException("Iterator terminated");
        SetAt(data, apsd.classes.utilities.Natural.Of(index));
      }

      @Override
      public Data DataNNext() {
        if (!IsValid()) throw new IllegalStateException("Iterator terminated");
        Data d = GetAt(apsd.classes.utilities.Natural.Of(index));
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
    long n = Size().ToLong();
    long s = (start == null ? 0L : start.ToLong());
    long e = (end == null ? (n > 0 ? n - 1 : -1) : end.ToLong());

    if (s < 0 || e < 0 || s > e || e >= n) return null;
    int count=0;
    int len = (int) (e - s + 1);
    Data[] slice= (Data[]) new Object[len];
    for (int i = (int) s; i <= (int) e; i++) {
      slice[count] = GetAt(Natural.Of(i));
      count++;
    }

    return NewVector(slice);
  }
}

