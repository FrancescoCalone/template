package apsd.classes.containers.sequences.abstractbases;

import apsd.classes.utilities.Natural;

/** Object: Abstract (static) linear vector base implementation. */
abstract public class LinearVectorBase<Data> extends VectorBase<Data> { // Must extend VectorBase

  // LinearVectorBase
  protected LinearVectorBase(Natural size) {
    super(size);
  }

  /* ************************************************************************ */
  /* Override specific member functions from ReallocableContainer             */
  /* ************************************************************************ */

  @Override 
  public void Realloc(Natural newsize) {
    long size = newsize.ToLong();
    if (size < 0) throw new IllegalArgumentException("Size negative");
    ArrayAlloc(newsize);
  }

  /* ************************************************************************ */
  /* Override specific member functions from Sequence                         */
  /* ************************************************************************ */

  @Override
  public Data GetAt(Natural position) {
    long pos = ExcIfOutOfBound(position);
    return arr[(int) pos];
  }

  /* ************************************************************************ */
  /* Override specific member functions from MutableSequence                  */
  /* ************************************************************************ */
  
  @Override
  public void SetAt(Data element, Natural position) {
    long pos = ExcIfOutOfBound(position);
    arr[(int) pos] = element;
  }

}
