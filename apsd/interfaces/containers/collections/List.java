package apsd.interfaces.containers.collections;

import apsd.classes.utilities.Natural;
import apsd.interfaces.containers.sequences.InsertableAtSequence;
import apsd.interfaces.containers.sequences.MutableSequence;

public interface List<Data> extends MutableSequence<Data>, InsertableAtSequence<Data>, Chain<Data> {

 // SubList
 default List<Data> SubList(Natural start, Natural end){
  return (List<Data>) Chain.super.SubChain(start, end);
 }

  /* ************************************************************************ */
  /* Override specific member functions from ExtensibleContainer              */
  /* ************************************************************************ */

  @Override
  default boolean Insert (Data element) {
    if (element == null) return false;
    InsertFirst(element);
    return true;
  }

}
