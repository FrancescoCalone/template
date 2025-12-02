package apsd.interfaces.containers.sequences;

import apsd.classes.utilities.Natural;
import apsd.interfaces.containers.base.ReallocableContainer;

public interface Vector<Data> extends ReallocableContainer, MutableSequence<Data> { // Must extend ReallocableContainer and MutableSequence

  // ShiftLeft di una posizione (delegante)
  default void ShiftLeft(Natural pos){
    ShiftLeft(pos, Natural.ONE);
  }

  //ShiftLeft
  default void ShiftLeft(Natural pos, Natural num) {
       long idx = ExcIfOutOfBound(pos);
       long size = Size().ToLong();
       long len = num.ToLong();
       len = (len <= size - idx) ? len : size - idx;
       if (len > 0) {
          long iniwrt = idx;
          long wrt = iniwrt;
          for (long rdr = wrt + len; rdr < size; rdr++, wrt++) {
               Natural natrdr = Natural.Of(rdr);
                SetAt(GetAt(natrdr), Natural.Of(wrt));
                SetAt(null, natrdr);
       }
       for (; wrt - iniwrt < len; wrt++) {
         SetAt(null, Natural.Of(wrt));
       }
        }
   }


  // ShiftFirstLeft
  default void ShiftFirstLeft() {
    ShiftLeft(Natural.ZERO, Natural.ONE);
  }

  // ShiftLastLeft
  default void ShiftLastLeft() {
    ShiftLeft(Natural.Of(Size().ToLong() - 1), Natural.ONE  );
  }
  
  
  

  // ShiftRight
  default void ShiftRight(Natural pos){
    ShiftRight(pos, Natural.ONE);
  }

  default void ShiftRight(Natural pos, Natural num) {
    long idx = ExcIfOutOfBound(pos);
    long size = Size().ToLong();
    long len = num.ToLong();
    len = (len <= size - idx) ? len : size - idx;
    if (len > 0) {
      for (long rdr = size - 1 - len; rdr >= idx; rdr--) {
        Natural natrdr = Natural.Of(rdr);
        SetAt(GetAt(natrdr), Natural.Of(rdr + len));
        SetAt(null, natrdr);
      }
      for (long i = 0; i < len; i++) {
        SetAt(null, Natural.Of(idx + i));
      }
    }
  }


  // ShiftFirstRight
  default void ShiftFirstRight() {
    ShiftRight(Natural.ZERO, Natural.ONE);
  }

  // ShiftLastRight
  default void ShiftLastRight() {
    ShiftRight(Natural.Of(Size().ToLong() - 1), Natural.ONE);
  }

  // SubVector
  default Vector<Data> SubVector(Natural start, Natural end){
  long startIdx = ExcIfOutOfBound(start);
  long endIdx = ExcIfOutOfBound(end);
  long s = startIdx;
  long e = endIdx;
  long n = Size().ToLong();
  if (s > e || e >= n) return null;
  return (Vector<Data>) SubSequence(start, end);
  }

  /* ************************************************************************ */
  /* Override specific member functions from Container                        */
  /* ************************************************************************ */

  @Override
  default Natural Size() {
    return ReallocableContainer.super.Size();
  } 

}
