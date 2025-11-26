package apsd.interfaces.containers.base;

import apsd.classes.utilities.Box;
import apsd.classes.utilities.Natural;
import apsd.interfaces.traits.Accumulator;
import apsd.interfaces.traits.Predicate;
import java.util.Objects;

/** Interface: MembershipContainer con supporto all'attraversamento. */
public interface TraversableContainer<Data> extends MembershipContainer<Data> { // Must extend MembershipContainer

  // TraverseForward
    boolean TraverseForward(Predicate<Data> fun);
  // TraverseBackward
    boolean TraverseBackward(Predicate<Data> fun);

    default <Acc> Acc FoldForward(Accumulator<Data, Acc> fun, Acc ini) {
       final Box<Acc> acc = new Box<>(ini);
       if (fun != null) TraverseForward(dat -> { acc.Set(fun.Apply(dat, acc.Get())); return false; });
       return acc.Get();
      }

  // FoldBackward
    default <Acc> Acc FoldBackward (Accumulator<Data, Acc> fun, Acc ini) {
       final Box<Acc> acc = new Box<>(ini);
       if (fun != null) TraverseBackward(dat -> { acc.Set(fun.Apply(dat, acc.Get())); return false; });
       return acc.Get();
      }


  /* ************************************************************************ */
  /* Override specific member functions from Container                        */
  /* ************************************************************************ */
  
  @Override
  default Natural Size() { 
    final Box<Long> count = new Box<>(0L);
    TraverseForward(dat -> { count.Set(count.Get() + 1); return false; });
    return Natural.Of(count.Get());
  }
  /* ************************************************************************ */
  /* Override specific member functions from MembershipContainer              */
  /* ************************************************************************ */

  @Override
  default boolean Exists(Data item){
  final Box<Boolean> found = new Box<>(false);
  if(item==null) return false; 
  TraverseForward(dat -> { if(Objects.equals(dat, item)){ found.Set(true); return true;} return false;});
  return found.Get();
  }
 


}
