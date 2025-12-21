package apsd.classes.containers.sequences.abstractbases;

import apsd.classes.utilities.Natural;
import apsd.interfaces.containers.base.TraversableContainer;

/** Object: Abstract (static) linear vector base implementation. */
abstract public class LinearVectorBase<Data> extends VectorBase<Data> { // Must extend VectorBase

  // LinearVectorBase
  public LinearVectorBase(TraversableContainer<Data> con) {
    super(con);
  }

  public LinearVectorBase() {
    super();
  }

  public LinearVectorBase(Natural size) {
    super(size);
  }

  public LinearVectorBase(Data[] arr) {
    super(arr);
  }



  /* ************************************************************************ */
  /* Override specific member functions from ReallocableContainer             */
  /* ************************************************************************ */

  @Override 
  public void Realloc(Natural newsize) {
    if (newsize == null) return;
    long nsize = newsize.ToLong();
    if (nsize < 0) throw new IllegalArgumentException("Size negative");
    Data[] oldArr = arr;
    ArrayAlloc(newsize);
    if (oldArr != null) {
      int copyLen = (int) Math.min(oldArr.length, arr.length);
      System.arraycopy(oldArr, 0, arr, 0, copyLen);
    }
    
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
