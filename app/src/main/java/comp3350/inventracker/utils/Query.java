package comp3350.inventracker.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.ToIntFunction;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * A Fluid Wrapper for the Stream API to simplify complex methods and enable method chaining.<br>
 * <code>
 * new Query(collection)<br>
 * .Where(predicate1)<br>
 * .Select(predicate2)<br>
 * .ToList()<br>
 * </code>
 */
public class Query<T> {
    private final Collection<T> collection;
    
    public Query(final Collection<T> collection) {
        this.collection = collection;
    }
    
    public List<T> ToList() {
        return new ArrayList<>(collection);
    }
    
    public Stream<T> Stream() {
        return collection.stream();
    }
    
    /** Selects all elements in this list that satisfy selector.*/
    public Query<T> Append(T item) {
        collection.add(item);
        return this;
    }
    
    /** Selects a transformation of this list.*/
    public <R> Query<R> Select(Function<T, R> transformer) {
        return new Query<>(Stream().map(transformer)
                                   .collect(Collectors.toList()));
    }
    
    /** Selects all elements in this list that satisfy selector.*/
    public Query<T> Where(Predicate<T> selector) {
        return new Query<>(Stream().filter(selector)
                                   .collect(Collectors.toList()));
    }
    
    /** Selects all elements in this list that satisfy comparator.*/
    public Query<T> OrderBy(Comparator<T> comparator) {
        return new Query<>(Stream().sorted(comparator)
                                   .collect(Collectors.toList()));
    }
    
    /** Selects all elements in this list that satisfy comparator.*/
    public <TComparable extends Comparable<TComparable>> Query<T> OrderBy(Function<T, TComparable> selectComparable) {
        return new Query<>(Stream().sorted(Comparator.comparing(selectComparable))
                                   .collect(Collectors.toList()));
    }
    
    public <TKey, TElement, TResult> Query<TResult> GroupBy(
        Function<T, TKey> keySelector,
        Function<T, TElement> elementSelector,
        BiFunction<TKey, Query<TElement>, TResult> resultSelector
    ) {
        return new Query<>(Stream().collect(Collectors.groupingBy(
                                       keySelector,
                                       Collectors.mapping(
                                           elementSelector,
                                           Collectors.toCollection(ArrayList::new))))
                                   .entrySet()
                                   .stream()
                                   .map(entry -> resultSelector.apply(
                                       entry.getKey(),
                                       new Query<>(entry.getValue())
                                   ))
                                   .collect(Collectors.toList()));
    }
    
    // -----------------------------------------------------------------------------------------
    //
    // Methods to Retrieve Data (and end the current Query method chain, as a Query is not returned;
    //
    // -----------------------------------------------------------------------------------------
    
    /**
     * Returns the first element in list satisfying selector.
     */
    public T First(Predicate<T> selector) {
        return Stream().filter(selector)
                       .findFirst()
                       .orElse(null);
    }
    
    public T First() {
        return Stream().findFirst()
                       .orElse(null);
    }
    
    public int Count() {
        return collection.size();
    }
    
    public int CountIf(Predicate<T> cond) {
        return (int) collection.stream().filter(cond).count();
    }
    
    public int Max(ToIntFunction<T> toInt, int defaultValue) {
        return Stream().map(toInt::applyAsInt)
                       .max(Integer::compare)
                       .orElse(defaultValue);
    }
    
    @SafeVarargs
    public static <T> List<T> ListOf(T... ts) {
        return Stream.of(ts).collect(Collectors.toList());
    }
}
