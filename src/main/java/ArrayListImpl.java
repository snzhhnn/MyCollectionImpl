import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class ArrayListImpl<T> {

    private static final int DEFAULT_CAPACITY = 10;
    private int size;
    private Object[] elementData;
    private static final Object[] EMPTY_LIST = {};

    public ArrayListImpl() {
        this.elementData = EMPTY_LIST;
    }

    public ArrayListImpl(int yourCapacity) throws IncorrectValueException {
        if (yourCapacity > 0) {
            this.elementData = new Object[DEFAULT_CAPACITY];
        } else if (yourCapacity == 0) {
            this.elementData = EMPTY_LIST;
        } else {
            throw new IncorrectValueException();
        }
    }

    public ArrayListImpl(Collection<? extends T> collection) {
        Object[] mass = collection.toArray();
        size = mass.length;
        if (size != 0) {
            if (collection.getClass() == ArrayList.class) {
                this.elementData = mass;
            } else {
                elementData = Arrays.copyOf(mass, size, Object[].class);
            }
        } else {
            this.elementData = EMPTY_LIST;
        }
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public boolean contains(Object o) {
        return indexOf(o) >= 0;
    }

    public Object[] toArray() {
        return Arrays.copyOf(elementData, size);
    }

    public boolean add(T t) {
        if (size == elementData.length) {
            elementData = grow(size+1);
        }
        elementData[size] = t;
        size = size + 1;
        return true;
    }

    private Object[] grow(int minCapacity) {
        int oldCapacity = elementData.length;
        if (oldCapacity > 0 || elementData != EMPTY_LIST) {
            int newCapacity = (oldCapacity * 3) / 2 + 1;
            return elementData = Arrays.copyOf(elementData, newCapacity);
        } else {
            return elementData = new Object[Math.max(DEFAULT_CAPACITY, minCapacity)];
        }
    }

======= add-decrease-array
    public void trimToSize() {
        if (size < elementData.length) {
            elementData = size == 0 ? EMPTY_LIST :Arrays.copyOf(elementData, size);
        }
    }


======= master
    public void remove(int index) {
        try {
            rangeCheck(index);
        } catch (InvalidIndexException e) {
            System.out.println(e.getMessage());
        }

        int numMoved = size - index - 1;
        System.arraycopy(elementData, index + 1, elementData, index, numMoved);
        size--;
        elementData[size] = null;
        automaticallyDecreaseArray();
    }

    public void remove(Object o) {
        int numMoved = 0;
        int index = 0;
        for(int i = 0;i < size; i++) {
            if (elementData[i] == o) {
                index = i;
                numMoved = size - i - 1;
                break;
            }
        }
        System.arraycopy(elementData, index + 1, elementData, index, numMoved);
        elementData[--size] = null;
        automaticallyDecreaseArray();
    }

    private void automaticallyDecreaseArray() {
        if (size < elementData.length / 1.5 && elementData.length > 10) {
            elementData = Arrays.copyOf(elementData, Math.toIntExact(Math.round(elementData.length / 1.5)));
        }
    }

    public void addAll(Collection<? extends T> c) {
        Object[] collection = c.toArray();
        int sizeCollection = collection.length;
        if (sizeCollection == 0) return;
        grow(sizeCollection+size);
        System.arraycopy(collection, 0, elementData, size, sizeCollection);
        size = size + sizeCollection;
    }


    public void addAll(int index, Collection<? extends T> c) {
        try {
            rangeCheck(index);
        } catch (InvalidIndexException e) {
            System.out.println(e.getMessage());
        }

        try {
            nullCheckForCollection(c);
        } catch (NullValueException e) {
            System.out.println(e.getMessage());
        }

        Object[] collection = c.toArray();
        int sizeCollection = collection.length;
        if (sizeCollection == 0) return;
        grow(sizeCollection+size);

        int numMoved = size - index;
        if (numMoved > 0) {
            System.arraycopy(elementData, index, elementData, index + numMoved, numMoved);
        }
        System.arraycopy(collection, 0, elementData, index, sizeCollection);
        size = size + sizeCollection;
    }

    public void removeAll(Collection<?> c) {
        try {
            nullCheckForCollection(c);
        } catch (NullValueException e) {
            System.out.println(e.getMessage());
        }

        helpMethodForRetainAndRemoveAll(c, false);
    }

    public void retainAll(Collection<?> c) {
        try {
            nullCheckForCollection(c);
        } catch (NullValueException e) {
            System.out.println(e.getMessage());
        }
        helpMethodForRetainAndRemoveAll(c, true);
    }

    private void helpMethodForRetainAndRemoveAll(Collection<?> c, boolean total) {
        int count = 0;
        int length = Math.max(size, c.size());
        Object[] mass = new Object[length];
        for (int i=0;i<=length; i++) {
            if (c.contains(elementData[i]) == total && i != size) {
                mass[count++] = elementData[i];
            }
        }
        System.arraycopy(mass, 0, elementData, 0, count < mass.length ? size = count : mass.length);
    }

    public void clear() {
        for (int i=0; i< size; i++) {
            elementData[i] = null;
        }
        size = 0;
    }

    public T get(int index) {
        try {
            rangeCheck(index);
        } catch (InvalidIndexException e) {
            System.out.println(e.getMessage());
        }

        return (T) elementData[index];
    }

    public T set(int index, T element) {
        try {
            rangeCheck(index);
        } catch (InvalidIndexException e) {
            System.out.println(e.getMessage());
        }

        T oldValue = (T) elementData[index];
        elementData[index] = element;
        return oldValue;
    }

    public void add(int index, T element) {
        try {
            rangeCheck(index);
        } catch (InvalidIndexException e) {
            System.out.println(e.getMessage());
        }

        if (size == elementData.length) {
            elementData = grow(size+1);
        }
        System.arraycopy(elementData, index, elementData, index+1, size-index);
        elementData[index] = element;
        size = size + 1;
    }


    public int indexOf(Object o) {
        if (o == null) {
            for (int i = 0; i < size; i++) {
                if (elementData[i] == null) {
                    return i;
                }
            }
        } else {
            for (int i = 0; i < size; i++) {
                if (o.equals(elementData[i])) {
                    return i;
                }
            }
        }
        return -1;
    }

    public int lastIndexOf(Object o) {
        if (o == null) {
            for (int i = size-1; i >= 0; i--) {
                if (elementData[i] == null) {
                    return i;
                }
            }
        } else {
            for (int i = size-1; i >= 0; i--) {
                if (o.equals(elementData[i])) {
                    return i;
                }
            }
        }
        return -1;
    }

    public Object[] subList(int fromIndex, int toIndex) {
        try {
            rangeCheck(fromIndex);
            rangeCheck(toIndex);
        } catch (InvalidIndexException e) {
            System.out.println(e.getMessage());
        }

        return Arrays.copyOfRange(elementData, fromIndex, toIndex+1);
    }

    private void rangeCheck(int index) throws InvalidIndexException {
        if (index >= size || index < 0)
            throw new InvalidIndexException();
    }

    private void nullCheckForCollection(Collection<?> c) throws NullValueException {
        if (c == null) throw new NullValueException();
    }
}