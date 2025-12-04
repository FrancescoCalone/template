package apsd.interfaces.containers.collections;

import apsd.classes.utilities.Natural;
import apsd.interfaces.containers.sequences.InsertableAtSequence;
import apsd.interfaces.containers.sequences.MutableSequence;

public interface List<Data> extends MutableSequence<Data>, InsertableAtSequence<Data>, Chain<Data> {

 // SubList
 default List<Data> SubList(Natural start, Natural end){
   /*  if (start == null || end == null) throw new NullPointerException("Indici null");
    long s = start.ToLong();
    long e = end.ToLong();
    if (s > e || e >= Size().ToLong()) throw new IndexOutOfBoundsException("Intervallo non valido: ["+s+","+e+"] size="+Size().ToLong());
    for (long i = Size().ToLong(); i > 0; --i) {
      long idx = i - 1;
      if (idx < s || idx > e) {
        RemoveAt(Natural.Of(idx));
      }
    }
    return this;
  } */
  return (List<Data>) Chain.super.SubChain(start, end);
 }

  /* ************************************************************************ */
  /* Override specific member functions from ExtensibleContainer              */
  /* ************************************************************************ */

  @Override
  default boolean Insert (Data element) {
    if (element == null) throw new NullPointerException("Elemento nullo");
    InsertFirst(element);
    return true;
  }

}
