package apsd.interfaces.containers.base;

import apsd.classes.utilities.Natural;

/** Interface: ReallocableContainer che è espandibile e riducibile. */
public interface ResizableContainer extends ReallocableContainer{

  double THRESHOLD_FACTOR = 2.0; 

  // Expand
  void Expand(Natural extraSize);

  default void Expand(){
    Expand(Natural.ONE);
  }

  // Reduce
  void Reduce(Natural reducedSize);
 
  default void Reduce(){
    Reduce(Natural.ONE);
  }   

  /* ************************************************************************ */
  /* Override specific member functions from Container                        */
  /* ************************************************************************ */

  @Override
  abstract Natural Size();
  
  /* ************************************************************************ */
  /* Override specific member functions from ReallocableContainer             */
  /* ************************************************************************ */

  @Override
  default void Grow(Natural NewSize) {
    if(Capacity().ToLong()>=Long.MAX_VALUE) 
        throw new ArithmeticException();
    else if(Capacity().ToLong()<Long.MAX_VALUE && (Size().ToLong())+NewSize.ToLong() >= Capacity().ToLong()) 
        ReallocableContainer.super.Grow(NewSize);
  }

  @Override
  default void Shrink() {
  if ((long) (THRESHOLD_FACTOR * SHRINK_FACTOR * Size().ToLong()) <= Capacity().ToLong()) 
  ReallocableContainer.super.Shrink();
  }

}
