package apsd.interfaces.containers.base;

/** Interface: IterableContainer con struttura dei dati ordinata. */
public interface SortedIterableContainer<Data> extends IterableContainer<Data>,Comparable<Data>{} // Must extends IterableContainer; Data must extend Comparable