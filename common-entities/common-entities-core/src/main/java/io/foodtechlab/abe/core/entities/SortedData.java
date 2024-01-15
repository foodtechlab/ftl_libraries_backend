package io.foodtechlab.core.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

// Нужно чтобы у T обязательно был переопределён метод equals и hashCode
@Getter
@Setter
@SuppressWarnings({"unused", "UnusedReturnValue"})
public class SortedData<T> implements Iterable<T> {
    //todo убрать велосипед
    protected List<Position<T>> values = new ArrayList<>();
    protected String firstElementId;
    protected String lastElementId;

    public static <T> SortedData<T> empty() {
        return new SortedData<>();
    }

    public static <T> SortedData<T> of(Collection<T> list) {
        SortedData<T> data = new SortedData<>();
        list.forEach(data::add);

        return data;
    }

    @SuppressWarnings("UnusedReturnValue")
    public boolean removeIf(Predicate<? super T> filter) {
        List<T> values = stream().filter(filter).collect(Collectors.toList());
        values.forEach(this::delete);
        return values.size() > 0;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<>() {
            Position<T> currentPosition = null;

            @Override
            public boolean hasNext() {
                if (currentPosition == null && firstElementId != null)
                    return true;
                else if (currentPosition != null)
                    return currentPosition.next != null;
                else
                    return false;
            }

            @Override
            public T next() throws IndexOutOfBoundsException {
                if (!this.hasNext()) throw new IndexOutOfBoundsException("End of list.");
                if (currentPosition == null)
                    currentPosition = getById(firstElementId);
                else
                    currentPosition = getById(currentPosition.getNext());

                return currentPosition.element;
            }
        };
    }

    @Override
    public void forEach(Consumer<? super T> action) {
        Iterable.super.forEach(action);
    }

    @Override
    public Spliterator<T> spliterator() {
        return Iterable.super.spliterator();
    }

    /**
     * Добавляет новый id документа <strong>в конец</strong> списка<br>
     * При передаче пустого значения или значение уже представленного в списке ничего не делает.
     *
     * @param element идентификатор объекта
     * @return коллекция над которой ведётся работа
     * @see SortedData
     */
    public SortedData<T> add(T element) {
        if (isNewInvalid(element))
            return this;

        Position<T> lastElement = getById(lastElementId);
        Position<T> newElement;
        if (lastElement == null) {
            newElement = Position.empty(element);
            firstElementId = newElement.id;
        } else {
            newElement = Position.last(lastElement.id, element);
            lastElement.setNext(newElement.id);
        }
        values.add(newElement);
        lastElementId = newElement.id;

        return this;
    }

    /**
     * Добавляет новый id документа <strong>в начало</strong> списка<br>
     * При передаче пустого значения или значение уже представленного в списке ничего не делает.
     *
     * @param element идентификатор объекта
     * @return коллекция над которой ведётся работа
     * @see SortedData
     */
    public SortedData<T> addStart(T element) {
        if (isNewInvalid(element))
            return this;

        Position<T> position = getById(firstElementId);
        Position<T> newPosition;

        if (position == null) {
            newPosition = Position.empty(element);
            values.add(newPosition);
            lastElementId = newPosition.id;
        } else {
            newPosition = Position.first(position.id, element);
            position.setPrev(newPosition.id);
            values.add(newPosition);
        }
        firstElementId = newPosition.id;
        return this;
    }

    private boolean isNewInvalid(T element) {
        if (element == null)
            return false;

        return exist(element);
    }

    private boolean isExistedInvalid(T element) {
        if (element == null)
            return false;

        return !exist(element);
    }

    public boolean contains(T element) {
        return exist(element);
    }

    /**
     * Добавляет новый id документа <strong>после указанного элемента</strong> <br>
     * При добавлении пустого значения или значение уже представленного в списке, ничего не делает.<br>
     * При ссылке на пустое значения или значение не представленного в списке, ничего не делает.
     *
     * @param element идентификатор объекта
     * @param after   идентификатор объекта после которого нужно поставить элемент
     * @return коллекция над которой ведётся работа
     * @see SortedData
     */
    public SortedData<T> addAfter(T element, T after) {
        if (isNewInvalid(element))
            return this;

        if (isExistedInvalid(after))
            return this;

        boolean isAfterLast = getById(lastElementId).element.equals(after);
        if (isAfterLast) {
            add(element);
            return this;
        }

        Position<T> afterElement = get(after);
        Position<T> newElement = Position.middle(afterElement.id, afterElement.next, element);
        values.add(newElement);

        afterElement.next = newElement.id;

        return this;
    }

    //todo Написать тест что если в списке один элемент или создан объект без элементов

    /**
     * Добавляет новый id документа <strong>перед указанным элементом</strong> <br>
     * При добавлении пустого значения или значение уже представленного в списке, ничего не делает.<br>
     * При ссылке на пустое значения или значение не представленного в списке, ничего не делает.
     *
     * @param element идентификатор объекта
     * @param before  идентификатор объекта перед которым нужно поставить элемент
     * @return коллекция над которой ведётся работа
     * @see SortedData
     */
    public SortedData<T> addBefore(T element, T before) {
        if (isNewInvalid(element))
            return this;

        if (isExistedInvalid(before))
            return this;

        boolean isAfterLast = getById(firstElementId).element.equals(before);
        if (isAfterLast) {
            addStart(element);
            return this;
        }

        Position<T> beforeElement = get(before);
        Position<T> newPosition = Position.middle(beforeElement.prev, beforeElement.id, element);
        values.add(newPosition);
        beforeElement.prev = newPosition.id;

        return this;
    }

    /**
     * Удаляет указанный элемент <br>
     * При добавлении пустого значения или значение уже представленного в списке, ничего не делает.<br>
     * При ссылке на пустое значения или значение не представленного в списке, ничего не делает.
     *
     * @param element идентификатор объекта который следует удалить
     * @return коллекция над которой ведётся работа
     * @see SortedData
     */
    public SortedData<T> delete(T element) {
        if (isExistedInvalid(element))
            return this;

        if (values.size() == 0) {
            firstElementId = null;
            lastElementId = null;
        }

        Position<T> position = get(element);

        Position<T> prev = getById(position.prev);
        Position<T> next = getById(position.next);

        if (prev != null)
            prev.next = position.next;
        else if (next != null)
            firstElementId = next.id;
        else
            firstElementId = null;

        if (next != null)
            next.prev = position.prev;
        else if (prev != null)
            lastElementId = prev.id;
        else
            lastElementId = null;

        values.remove(position);

        return this;
    }

    private boolean exist(T element) {
        return get(element) != null;
    }

    private Position<T> get(T element) {
        return values.stream().filter(v -> v.element.equals(element)).findFirst().orElse(null);
    }

    private Position<T> getById(String id) {
        return values.stream().filter(v -> v.id.equals(id)).findFirst().orElse(null);
    }

    public Stream<T> stream() {
        return StreamSupport.stream(spliterator(), false);
    }

    public List<T> asList() {
        List<T> result = new ArrayList<>();
        this.forEach(result::add);
        return result;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor //todo переделать под сериалайзер в запросах и убрать эти лишние конструктора
    public static class Position<T> {
        protected String id;
        protected String prev;
        protected String next;
        protected T element;


        private static String generateId() {
            return UUID.randomUUID().toString();
        }

        private static <T> Position<T> last(String prev, T element) {
            return new Position<>(generateId(), prev, null, element);
        }

        private static <T> Position<T> first(String next, T element) {
            return new Position<>(generateId(), null, next, element);
        }

        private static <T> Position<T> middle(String prev, String next, T element) {
            return new Position<>(generateId(), prev, next, element);
        }

        private static <T> Position<T> empty(T element) {
            return new Position<>(generateId(), null, null, element);
        }
    }
}
