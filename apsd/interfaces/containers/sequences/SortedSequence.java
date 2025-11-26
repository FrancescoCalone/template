package apsd.interfaces.containers.sequences;

import apsd.classes.utilities.Natural;
import apsd.interfaces.containers.base.SortedIterableContainer;

/** Interface: Sequence & SortedIterableContainer. */
public interface SortedSequence<Data extends Comparable<Data>> extends SortedIterableContainer<Data>, Sequence<Data> { // Must extend Sequence and SortedIterableContainer

  /* ************************************************************************ */
  /* Override specific member functions from MembershipContainer              */
  /* ************************************************************************ */

  @Override
  default boolean Exists(Data value) {
    if (value == null) throw new IllegalArgumentException("Value nullo");
    return Search(value).ToLong() >= 0;
  }

  /* ************************************************************************ */
  /* Override specific member functions from Sequence                         */
  /* ************************************************************************ */

  @Override
  default Natural Search(Data value) {
    if (value == null) throw new IllegalArgumentException("Value nullo");
    Natural left = Natural.Of(0);
    Natural right = Natural.Of(Size().ToLong() - 1);
    while (left.ToLong() <= right.ToLong()) {
      Natural mid = Natural.Of((left.ToLong() + right.ToLong()) / 2);
      Data midValue = GetAt(mid);
      int cmp = midValue.compareTo(value);
      if (cmp == 0) {
        return mid;
      } else if (cmp < 0) {
        left = Natural.Of(mid.ToLong() + 1);
      } else {
        right = Natural.Of(mid.ToLong() - 1);
      }
    }
    return Natural.Of(-1);
  }
}
