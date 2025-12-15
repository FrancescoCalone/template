package apsd.interfaces.containers.sequences;

import apsd.classes.utilities.Natural;
import apsd.interfaces.containers.base.SortedIterableContainer;

/** Interface: Sequence & SortedIterableContainer. */
public interface SortedSequence<Data extends Comparable<? super Data>> extends SortedIterableContainer<Data>, Sequence<Data> { // Must extend Sequence and SortedIterableContainer

  /* ************************************************************************ */
  /* Override specific member functions from MembershipContainer              */
  /* ************************************************************************ */

  @Override
  default boolean Exists(Data value) {
    if (value == null) throw new IllegalArgumentException("Value nullo");
    return Search(value) != null;
  }

  /* ************************************************************************ */
  /* Override specific member functions from Sequence                         */
  /* ************************************************************************ */

  @Override
  default Natural Search(Data value) {
    if (value == null) throw new IllegalArgumentException("Value nullo");
    if (Size().IsZero()) return null;
    long left = 0L;
    long right = Size().ToLong() - 1L;
    while (left <= right) {
      long mid = (left + right) / 2L;
      Natural midNat = Natural.Of(mid);
      Data midValue = GetAt(midNat);
      int cmp = midValue.compareTo(value);
      if (cmp == 0) {
        return midNat;
      } else if (cmp < 0) {
        left = mid + 1L;
      } else {
        right = mid - 1L;
      }
    }
    return null;
  }
}
