import java.util.AbstractMap;

/**
 * Реализация бинарного дерева поиска
 */

public class BinarySearchTree {
    /**
     * Класс, описывающий узел дерева
     */
    static class Node{
        AbstractMap.SimpleEntry<Integer, Double> keyValuePair;
        Node parent;
        Node left;
        Node right;
        public Node(Integer key, Double value,
                    Node parent, Node left, Node right){
            this.keyValuePair = new AbstractMap.SimpleEntry<Integer, Double>(key, value);
            this.parent = parent;
            this.left = left;
            this.right = right;
        }
    }
    private Node root;
    private int size;
    /**
     * Конструктор по умолчанию
     */
    public BinarySearchTree(){
        this.root = null;
        this.size = 0;
    }
    /**
     * Конструктор копирования
     */
    /*public BinarySearchTree(final BinarySearchTree other){
        this.root = other.root;
        this.size = other.size;
    }
     */

    /**
     * Класс, описывающий итератор по дереву
     */
    static class Iterator {
        private Node node;
        public Iterator(Node node){
            this.node = node;
        }
        public boolean hasNext(){
            return this.node != null;
        }

        /**
         * Метод, возвращающий значение ключа текущего узла
         * @return значение ключа текущего узла
         */
        public Iterator next() {
            if (this.node.right != null) {
                this.node = this.node.right;
                while(this.node.left != null){
                    this.node = this.node.left;
                }
                return this;
            }
            while(this.node.parent != null && this.node.parent.right == this.node){
                this.node = this.node.parent;
            }
            this.node = this.node.parent;
            return this;
        }

        /**
         * Возвращает значение ключа предыдущего узла
         * (узла, значение ключа которого меньше значения ключа текущего узла)
         * Если предыдущего узла нет, возвращает null
         * @return значение ключа предыдущего узла
         */
        public Iterator prev() {
            if (this.node.left != null) {
                this.node = this.node.left;
                while(this.node.right != null){
                    this.node = this.node.right;
                }
                return this;
            }
            while(this.node.parent != null && this.node.parent.left == this.node){
                this.node = this.node.parent;
            }
            this.node = this.node.parent;
            return this;
        }

        /**
         * Метод, сравнивающий два итератора
         * @param obj
         * @return true, если итераторы равны, false - иначе
         */
        @Override
        public boolean equals(Object obj) {
            if (obj instanceof Iterator other) {
                return this.node == other.node;
            }
            return false;
        }
    }

    /**
     * Метод вставки нового узла в дерево
     * @param key ключ нового узла
     * @param value значение нового узла
     *              (по умолчанию null)
     */

    public void insert(final Integer key, final Double value){
        if(this.root == null){
            this.root = new Node(key, value, null, null, null);
            this.size++;
            return;
        }
        Node current = this.root;
        while(current != null){
            if(key < current.keyValuePair.getKey()){
                if(current.left == null){
                    current.left = new Node(key, value, current, null, null);
                    this.size++;
                    return;
                }
                current = current.left;
            }
            else if(key > current.keyValuePair.getKey()){
                if(current.right == null){
                    current.right = new Node(key, value, current, null, null);
                    this.size++;
                    return;
                }
                current = current.right;
            }
            else{
                current.keyValuePair.setValue(value);
                return;
            }
        }
    }

    /**
    * Метод, стирающий узел с заданным ключом
     * @param key ключ узла, который нужно стереть
     */
    public void erase(final Integer key){
        Node current = this.root;
        while(current != null){
            if(key < current.keyValuePair.getKey()){
                current = current.left;
            }
            else if(key > current.keyValuePair.getKey()){
                current = current.right;
            }
            else{
                if(current.left == null && current.right == null){
                    if(current.parent == null){
                        this.root = null;
                    }
                    else if(current.parent.left == current){
                        current.parent.left = null;
                    }
                    else{
                        current.parent.right = null;
                    }
                    this.size--;
                    return;
                }
                else if(current.left == null){
                    if(current.parent == null){
                        this.root = current.right;
                        this.root.parent = null;
                    }
                    else if(current.parent.left == current){
                        current.parent.left = current.right;
                        current.right.parent = current.parent;
                    }
                    else{
                        current.parent.right = current.right;
                        current.right.parent = current.parent;
                    }
                    this.size--;
                    return;
                }
                else if(current.right == null){
                    if(current.parent == null){
                        this.root = current.left;
                        this.root.parent = null;
                    }
                    else if(current.parent.left == current){
                        current.parent.left = current.left;
                        current.left.parent = current.parent;
                    }
                    else{
                        current.parent.right = current.left;
                        current.left.parent = current.parent;
                    }
                    this.size--;
                    return;
                }
                else{
                    Node successor = current.right;
                    while(successor.left != null){
                        successor = successor.left;
                    }
                    current.keyValuePair = successor.keyValuePair;
                    if(successor.parent.left == successor){
                        successor.parent.left = successor.right;
                        if(successor.right != null){
                            successor.right.parent = successor.parent;
                        }
                    }
                    else{
                        successor.parent.right = successor.right;
                        if(successor.right != null){
                            successor.right.parent = successor.parent;
                        }
                    }
                    this.size--;
                    return;
                }
            }
        }
    }

    /**
     * Метод, возвращающий итератор на узел с заданным ключом
     * @param key
     * @return итератор на узел с заданным ключом
     */
    public Iterator find(final Integer key){
        Node current = this.root;
        while(current != null){
            if(key < current.keyValuePair.getKey()){
                current = current.left;
            }
            else if(key > current.keyValuePair.getKey()){
                current = current.right;
            }
            else{
                return new Iterator(current);
            }
        }
        return null;
    }

    /**
     * Метод, возвращающий ноду (узел) с заданным ключом
     * @param key
     * @return нода с заданным ключом
     */
    public Node findKey(final Integer key){
        Node current = this.root;
        while(current != null){
            if(key < current.keyValuePair.getKey()){
                current = current.left;
            }
            else if(key > current.keyValuePair.getKey()){
                current = current.right;
            }
            else{
                return current;
            }
        }
        return null;
    }

    /**
     * Метод, возвращающий итератор на первый узел с ключом, который больше или равен заданному
     * @param key
     * @return итератор на первый узел с ключом, который больше или равен заданному
     */
    public AbstractMap.SimpleEntry<Iterator, Iterator> equalRange(final Integer key){
        Node firstKey = findKey(key);
        Iterator secondKey = new Iterator(firstKey);
        secondKey.next();
        return new AbstractMap.SimpleEntry<>(new Iterator(firstKey), secondKey);
    }

    /**
     * Метод, который ищет минимальный узел в дереве с заданным ключом
     * @return итератор на минимальный узел в дереве с заданным ключом
     */
    public Iterator min(final Integer key){
        Node current = findKey(key);
        while(current.left != null){
            current = current.left;
        }
        return new Iterator(current);
    }

                        /**
     * Метод, который ищет максимальный узел в дереве c заданным ключом
     * @return итератор на максимальный узел в дереве с заданным ключом
     */
    public Iterator max(final Integer key){
        Node current = findKey(key);
        while(current.right != null){
            current = current.right;
        }
        return new Iterator(current);
    }

    /**
     * Метод, который возвращает итератор на первый узел в дереве
     * @return итератор на первый узел в дереве
     */
    public Iterator begin(){
        Node current = this.root;
        while(current.left != null){
            current = current.left;
        }
        return new Iterator(current);
    }

    /**
     * Метод, который возвращает итератор на последний узел в дереве
     * @return итератор на последний узел в дереве
     */
    public Iterator end(){
        Node current = this.root;
        while(current.right != null){
            current = current.right;
        }
        return new Iterator(current);
    }

    /**
     * Метод, который возвращает размер дерева
     * @return размер дерева
     */
    public int size(){
        return this.size;
    }

    /**
     * Основной метод печати дерева
     */
    public void print(){
        print(this.root);
    }

    /**
     * Метод, который печатает дерево
     * @param node
     */
    public static void print(Node node){
        if(node == null){
            return;
        }
        print(node.left);
        System.out.println(node.keyValuePair.getKey() + " " + node.keyValuePair.getValue());
        print(node.right);
    }
}
