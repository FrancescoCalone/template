package apsd.interfaces.containers.collections;

public interface OrderedChain<Data extends Comparable<Data>> extends Chain<Data>, OrderedSet<Data> {}
