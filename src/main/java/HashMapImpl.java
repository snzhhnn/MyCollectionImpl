
import java.util.*;

//лучше не спрашивать, что я курила, когда писала это
//зато работает)))))))))))))))))))))
public class HashMapImpl<K, V> {

    private static final int DEFAULT_CAPACITY = 16;
    private static final float DEFAULT_LOAD_FACTOR = 0.75f;
    private static final int USE_TREE = 8;
    private static final int USE_LINKED_LIST = 6;
    private int size;
    private Object[] table;
    private int capacity;


    public HashMapImpl() {
        table = new Element[DEFAULT_CAPACITY];
        capacity = DEFAULT_CAPACITY;
    }

    public HashMapImpl(int yourCapacity) {
        this(yourCapacity, DEFAULT_LOAD_FACTOR);
    }

    public HashMapImpl(int yourCapacity, float loadFactor) {
        if (yourCapacity > 0) {
            table = new Element[yourCapacity];
            capacity = yourCapacity;
        } else if(yourCapacity == 0) {
            table = new Element[DEFAULT_CAPACITY];
            capacity = DEFAULT_CAPACITY;
        } else throw new IllegalArgumentException("give me non-negative value :(");
    }

    private abstract class Element<K, V> {

        V getValue() {
            return null;
        }
        int hash() {
            return 0;
        }
        int getHash() {return 0;}
        K getKey() {return null;}
        V setValue(V newValue) {return null;}

        int size() {return 0;}
    }

    private class Node<K, V> extends Element {
        final int hash;
        final K key;
        V value;


        public Node(int hash, K key, V value) {
            this.hash = hash;
            this.key = key;
            this.value = value;
        }

        public int hash() {
            return key == null ? 0 : key.hashCode();
        }

        public V getValue() {
            return value;
        }

        public int getHash() {
            return hash;
        }

        public K getKey() {
            return key;
        }

        public String toString() {
            return key + ": " + value;
        }

        public V setValue(Object newValue) {
            V oldValue = value;
            value = (V) newValue;
            return oldValue;
        }

    }

    private class LinkedNode extends Element{
        private LinkedList<Node> linkedList = new LinkedList<>();

        public LinkedNode(Node node) {
            linkedList.add(node);
        }

        public LinkedNode(LinkedList linkedList) {
            this.linkedList.addAll(linkedList);
        }

        public void addNode(Node node) {
            linkedList.add(node);
        }

        public void updateNode(Object key, Object value) {
            for (Node node1: linkedList) {
                if (key == node1.getKey()) node1.setValue(value);
                break;
            }
        }

        public void removeNode(Object key) {
            linkedList.removeIf(node -> node.getKey().equals(key));
        }

        public Object get(Object key) {
            for (Node node: linkedList) {
                if (key == node.getKey()) return node.getValue();
            }
            return null;
        }

        public boolean containsKey(Object key) {
            for (Node node: linkedList) {
                if (key == node.getKey()) return true;
            }
            return false;
        }

        public boolean containsValue(Object value) {
            for (Node node: linkedList) {
                if (value.equals(node.getValue())) return true;
                System.out.println(value.equals(node.getValue()) + " " + value);
            }
            return false;
        }

        public int size() {
            return linkedList.size();
        }

        public Collection<? extends K> keySet() {
            Set<K> result = new HashSet<>();
            for (Node node: linkedList) {
                result.add((K) node.getKey());
            }
            return result;
        }

        public Collection<? extends V> values() {
            Set<V> result = new HashSet<>();
            for (Node node: linkedList) {
                result.add((V) node.getValue());
            }
            return result;
        }
    }

    private class TreeNode extends Element {
        private TreeMap<Node, String> treeNode = new TreeMap<>();

        public TreeNode(Node node) {
            treeNode.put(node, "null");
        }

        public TreeNode(TreeMap treeNode) {
            this.treeNode.putAll(treeNode);
        }

        public void addNode(Node node) {
            treeNode.put(node, "null");
        }

        public void updateNode(Object key, Object value) {
            for (Map.Entry<Node, String> entry : treeNode.entrySet()) {
                if (entry.getKey().getKey().equals(key)) {
                    entry.getKey().setValue(value);
                    break;
                }
            }
        }

        public void removeNode(Object key) {
            treeNode.remove(key);
        }

        public Object get(Object key) {
            for (Map.Entry<Node, String> entry : treeNode.entrySet()) {
                if (entry.getKey().getKey().equals(key)) return entry.getKey().getValue();
            }
            return null;
        }

        public boolean containsKey(Object key) {
            for (Map.Entry<Node, String> entry : treeNode.entrySet()) {
                if (entry.getKey().getKey().equals(key)) return true;
            }
            return false;
        }

        public boolean containsValue(Object value) {
            for (Map.Entry<Node, String> entry : treeNode.entrySet()) {
                if (entry.getKey().getValue().equals(value)) return true;
            }
            return false;
        }

        public int size() {
            return treeNode.size();
        }

        public Collection<? extends K> keySet() {
            Set<K> result = new HashSet<>();
            for (Map.Entry<Node, String> entry : treeNode.entrySet()) {
                result.add((K) entry.getKey().getKey());
            }
            return result;
        }

        public Collection<? extends V> values() {
            Set<V> result = new HashSet<>();
            for (Map.Entry<Node, String> entry : treeNode.entrySet()) {
                result.add((V) entry.getKey().getValue());
            }
            return result;
        }
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public boolean containsKey(Object key) {
        int hash = key.hashCode();
        int index = hash % table.length;
        boolean whereKey = false;

        if (table[index] != null) {
            try {
                whereKey = ((LinkedNode) table[index]).containsKey(key);
            } catch (ClassCastException e) {}


            try {
                whereKey = ((TreeNode) table[index]).containsKey(key);
            } catch (ClassCastException e) {}

            whereKey = ((Node)table[index]).getKey().equals(key);
        }

        return whereKey;
    }

    public boolean containsValue(Object value) {
        boolean whereValue = false;
        for (int i=0;i<table.length;i++) {
            if (table[i] != null) {
                try {
                    whereValue = ((LinkedNode) table[i]).containsValue(value);
                    break;
                } catch (ClassCastException e) {}
                try {
                    whereValue = ((TreeNode) table[i]).containsValue(value);
                    break;
                } catch (ClassCastException e) {}
                try {
                    whereValue = ((Node) table[i]).getValue().equals(value);
                    break;
                } catch (ClassCastException e) {}
            }
        }
        return whereValue;
    }

    public Object get(Object key) {
        int hash = key.hashCode();
        int index = hash % table.length;
        Object value = null;

        if (table[index] != null) {
            try {
                value = ((LinkedNode) table[index]).get(key);
            } catch (ClassCastException e) {}

            try {
                value = ((TreeNode) table[index]).get(key);
            } catch (ClassCastException e) {}

            value = ((Node)table[index]).getValue();
        }

        return value;
    }

    public Object put(Object key, Object value) {
        int hash = hash(key);
        int index = hash % table.length;
        Node<K, V> node;
        LinkedNode linkedNode;
        TreeNode treeNode;
        Node<K, V> newNode = (Node<K, V>) new Node<>(hash, key, value);
        if (table == null) {
            table = new Element[DEFAULT_CAPACITY];
        }
        if (size == capacity / DEFAULT_LOAD_FACTOR) {
            resize();
        }
        if (size > 0) {
            for(int i=0;i < size;i++) {
                node = (Node<K, V>) table[i];
                if (node != null) {
                    try {
                        if (node.hash() == hash && node.getKey() == key || key.equals(node.getKey())) {
                            ((Node)table[i]).setValue(value);
                        }
                    } catch (ClassCastException e) {}

                    try {
                        linkedNode = (LinkedNode) table[i];
                        if (linkedNode.containsKey(key)) {
                            linkedNode.updateNode(key, value);
                        } else {
                            linkedNode.addNode(newNode);
                        }
                    } catch (ClassCastException e) {}

                    try {
                        treeNode = (TreeNode) table[i];
                        if (treeNode.containsKey(key)) {
                            treeNode.updateNode(key, value);
                        } else {
                            treeNode.addNode(newNode);
                        }
                    } catch (ClassCastException e) {}
                }

            }
        }

        if (!addLinkedNode(index, newNode)) {
            if (!addTreeNode(index, newNode)) {
                table[index] = newNode;
                size++;
            }
        }
        return key;
    }

    private boolean addLinkedNode(int index, Node newNode) {

        try {
            if ((table[index]) != null && ((LinkedNode) table[index]).size() <= USE_TREE) {
                ((LinkedNode) table[index]).addNode(newNode);
                size++;
                return true;
            }
        } catch (ClassCastException e) {}

        try {
            if ((table[index]) != null && ((TreeNode) table[index]).size() >= USE_TREE) {
                table[index] = new TreeNode(newNode);
                size++;
                return true;
            }
        } catch (ClassCastException e) {}

        return false;
    }

    private boolean addTreeNode(int index, Node newNode) {
        try {
            if ((table[index]) != null && ((LinkedNode) table[index]).size() >= USE_LINKED_LIST) {
                ((LinkedNode) table[index]).addNode(newNode);
                size++;
                return true;
            }
        } catch (ClassCastException e) {}

        try {
            if ((table[index]) != null && ((TreeNode) table[index]).size() <= USE_LINKED_LIST) {
                table[index] = new TreeNode(newNode);
                size++;
                return true;
            }
        } catch (ClassCastException e) {}

        return false;
    }

    private void resize() {
        int buckets = table.length;
        Element[] data = (Element[]) table;
        capacity = buckets * 2;
        table = new Element[capacity];

        Element<K, V> node;
        for (int i=0;i< data.length;i++) {
            node = (Node<K, V>) data[i];
            if (!addLinkedNode(i, (Node<K, V>) node)) {
                if (!addTreeNode(i, (Node<K, V>) node)) {
                    table[i] = node;
                    size++;
                }
            }
        }
    }

    private int hash(Object o) {
        return o == null ? 0 : o.hashCode();
    }

    public void remove(Object key) {
        int hash = hash(key);
        int index = hash % table.length;

        try {
            ((LinkedNode) table[index]).removeNode(key);
        } catch (ClassCastException e) {}

        try {
            ((TreeNode) table[index]).removeNode(key);
        } catch (ClassCastException e) {}

        table[index] = null;
    }

    public void clear() {
        Arrays.fill(table, null);
        size = 0;
    }

    public Set<K> keySet() {
        Set<K> result = new HashSet<>();
        for(int i=0;i< table.length;i++) {
            if (table[i] != null) {
                try {
                    result.addAll(((LinkedNode) table[i]).keySet());
                } catch (ClassCastException e) {}


                try {
                    result.addAll(((TreeNode) table[i]).keySet());
                } catch (ClassCastException e) {}


                try {
                    result.add((K) ((Node) table[i]).getKey());
                } catch (ClassCastException e) {}
            }
        }
        return result;
    }

    public Collection<V> values() {
        List<V> result = new LinkedList<>();
        for(int i=0;i< table.length;i++) {

            try {
                result.addAll(((LinkedNode) table[i]).values());
            } catch (ClassCastException e) {}

            try {
                result.addAll(((TreeNode) table[i]).values());
            } catch (ClassCastException e) {}

            try {
                result.add((V) ((Node) table[i]).getValue());
            } catch (ClassCastException e) {}
        }
        return result;
    }
}