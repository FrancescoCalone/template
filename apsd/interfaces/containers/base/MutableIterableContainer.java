package apsd.interfaces.containers.base;

import apsd.interfaces.containers.iterators.MutableBackwardIterator;
import apsd.interfaces.containers.iterators.MutableForwardIterator;

/** Interface: IterableContainer con supporto all'iterazione mutabile. */
public interface MutableIterableContainer<Data> extends IterableContainer<Data> {

  // Iteratore forward mutabile
  public MutableForwardIterator<Data> FMIterator();

  // Iteratore backward mutabile
  public MutableBackwardIterator<Data> BMIterator();
}
