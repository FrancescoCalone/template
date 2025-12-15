package apsd.classes.containers.sequences.abstractbases;

 import apsd.classes.utilities.Natural;
 import apsd.interfaces.containers.base.TraversableContainer;

/** Object: Abstract (static) circular vector base implementation. */
abstract public class CircularVectorBase<Data> extends VectorBase<Data> {

  protected long start = 0L;

  public CircularVectorBase(TraversableContainer<Data> con) {
    super(con);
  }

  public CircularVectorBase() {
    super();
  }

  /* ************************************************************************ */
  /* Override specific member functions from ReallocableContainer             */
  /* ************************************************************************ */

  @Override 
  public void Realloc(Natural newsize) {
    long nsize = newsize.ToLong();
    Data[] oldArr = arr;
    long oldStart = start;
    int oldLen = (oldArr == null ? 0 : oldArr.length);
    ArrayAlloc(newsize);
    int newLen = arr.length;
    if (oldArr == null || oldLen == 0 || newLen == 0) {
      start = 0L;
      return;
    }
    long copySize = Math.min(oldLen, nsize);
    for (long k = 0; k < copySize; k++) {
      int p = (int)((oldStart + k) % oldLen);
      int q = (int)((oldStart + k) % newLen);
      arr[q] = oldArr[p];
    }
    start = oldStart % newLen;
  }

  /* ************************************************************************ */
  /* Override specific member functions from Sequence                         */
  /* ************************************************************************ */

  @Override
  public Data GetAt(Natural n) {
    long index = n.ToLong();
    if (index < 0 || index >= Size().ToLong()) throw new IndexOutOfBoundsException("Invalid index");
    return arr[(int) ((start + index) % arr.length)];
  }
  
  @Override
  public void SetAt(Data element, Natural n) {
    if(n==null){
        throw new IllegalArgumentException("Index is null.");
    }
    long index = n.ToLong();
    if (index < 0 || index >= Size().ToLong()) throw new IndexOutOfBoundsException("Invalid index");
    arr[(int) ((start + index) % arr.length)] = element;
  }

  /* ************************************************************************ */
  /* Override specific member functions from MutableSequence                  */
  /* ************************************************************************ */

  @Override
  public void ShiftLeft(Natural pos, Natural num) {
    long p = pos.ToLong();
    long n = num.ToLong();
    long size = Size().ToLong();
    if (n <= 0 || p < 0 || p >= size) return;
    long len = Math.min(n, size - p);
    for (long i = p; i < size - len; i++) {
      SetAt(GetAt(Natural.Of(i + len)), Natural.Of(i));
    }
    for (long i = size - len; i < size; i++) {
      SetAt(null, Natural.Of(i));
    }
  }

  @Override
  public void ShiftRight(Natural pos, Natural num) { 
    long p = pos.ToLong();
    long n = num.ToLong();
    long size = Size().ToLong();
    if (n <= 0 || p < 0 || p >= size) return;
    long len = Math.min(n, size - p);
    for (long i = size - 1 - len; i >= p; i--) {
      SetAt(GetAt(Natural.Of(i)), Natural.Of(i + len));
      SetAt(null, Natural.Of(i));
    }
    for (long i = 0; i < len; i++) {
      SetAt(null, Natural.Of(p + i));
    }
  }
  
  
  /* ************************************************************************ */
  /* Specific member functions of Vector                                      */
  /* ************************************************************************ */

  @Override
  protected void ArrayAlloc(Natural newsize) {
    long capacity = newsize.ToLong();
    if (capacity < 0) throw new IllegalArgumentException("Capacity negative");
    super.ArrayAlloc(newsize);
    start = 0L;
  }
}
