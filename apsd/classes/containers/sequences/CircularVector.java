package apsd.classes.containers.sequences;

import apsd.classes.containers.sequences.abstractbases.CircularVectorBase;
import apsd.classes.containers.sequences.abstractbases.VectorBase;
import apsd.classes.utilities.Natural;
import apsd.interfaces.containers.base.TraversableContainer;

/** Object: Concrete (static) circular vector implementation. */
public class CircularVector<Data> extends CircularVectorBase<Data> {

  // public CircularVector()
  public CircularVector() {
    super((TraversableContainer<Data>) null);
  }

  // public CircularVector(Natural inisize)
   public CircularVector(Natural inisize) {
    super((TraversableContainer<Data>) null);
    ArrayAlloc(inisize);
  }
  // public CircularVector(TraversableContainer<Data> con)
  public CircularVector(TraversableContainer<Data> con) {
    super(con);
  }

  // protected CircularVector(Data[] arr)
  protected CircularVector(Data[] arr) {
    super((TraversableContainer<Data>) null);
    start = 0L;
    this.arr = arr;
  }

  // NewVector
  @Override
  protected VectorBase<Data> NewVector(Data[] arr1) {
    return new CircularVector<>(arr1);
}
  }
