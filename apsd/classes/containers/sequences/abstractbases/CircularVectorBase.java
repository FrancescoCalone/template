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
    long size = newsize.ToLong();
    if (size < 0) throw new IllegalArgumentException("Size negative");
    ArrayAlloc(newsize);
    start = 0L;
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
  public void ShiftLeft(Natural num,Natural pos) {
    long n = num.ToLong();
    long p = pos.ToLong();
    long size = Size().ToLong();
    if (n <= 0 || p < 0 || p >= size) return;
    start = (start + n) % arr.length;
  }

  @Override
  public void ShiftRight(Natural num,Natural pos) { 
    long n = num.ToLong();
    long p = pos.ToLong();
    long size = Size().ToLong();
    if (n <= 0 || p < 0 || p >= size) return;
    start = (start - n + arr.length) % arr.length;
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
