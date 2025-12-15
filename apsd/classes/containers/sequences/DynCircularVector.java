package apsd.classes.containers.sequences;

import apsd.classes.containers.sequences.abstractbases.DynCircularVectorBase;
import apsd.classes.utilities.Natural;
import apsd.interfaces.containers.base.TraversableContainer;

/** Object: Concrete dynamic circular vector implementation. */
public class DynCircularVector<Data> extends DynCircularVectorBase<Data> {

  // public DynCircularVector()
  public DynCircularVector() {
    super((TraversableContainer<Data>) null);
    ArrayAlloc(Natural.ZERO);
  }
  // public DynCircularVector(Natural inisize)
  public DynCircularVector(Natural inisize) {
    super((TraversableContainer<Data>) null);
    ArrayAlloc(inisize);
  }

  // public DynCircularVector(TraversableContainer<Data> con)
  public DynCircularVector(TraversableContainer<Data> con) {
    super(con);
    this.size=arr.length;
  }

  // protected DynCircularVector(Data[] arr)
  protected DynCircularVector(Data[] arr) {
    super((TraversableContainer<Data>) null);
    this.arr = arr;
  }

  // NewVector
  @Override
  protected DynCircularVector<Data> NewVector(Data[] arr1) {
    return new DynCircularVector<>(arr1);
  }
}
