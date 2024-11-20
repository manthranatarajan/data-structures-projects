package project2;

import java.util.Comparator;

public class AVLTree<T extends Comparable<? super T>> extends BinarySearchTree<T> {
    static class Entry<T> extends BinarySearchTree.Entry<T> {
        int height;

        Entry(T x, Entry<T> left, Entry<T> right) {
            super(x, left, right);
            height = 1;
        }
    }

    AVLTree() {
        super();
    }

    //override the add method from BST class
    
    /**
     * Adds an element to the AVL tree, ensuring the tree remains balanced.
     * @param x the element to be added.
     * @return true if the element was added successfully; false if it was already present.
     */
    @Override
    public boolean add(T x) {
        int initialSize = size;
        root = add((Entry<T>) root, x);
        return size > initialSize;
    }
    
    
    /**
     * Recursively adds a new element to the tree, updating heights and balancing as needed.
     * @param node the subtree root where x will be added.
     * @param x the element to be added.
     * @return the balanced subtree after insertion.
     */
    public Entry<T> add(Entry<T> node, T x) {
        if (node == null) {
            size++;
            return new Entry<>(x, null, null);
        }

        int cmp = x.compareTo(node.element);
        
        if (cmp < 0) {
            node.left = add((Entry<T>) node.left, x);
        } 
        
        else if (cmp > 0) {
            node.right = add((Entry<T>) node.right, x);
        } 
        
        else {
            // duplicate element found, no insertion
            return node;
        }

        // update the height
        node.height = 1 + Math.max(height((Entry<T>) node.left), height((Entry<T>) node.right));

        // balance the tree with helper method
        return balance(node);
    }

    /* Balances the subtree rooted at the specified node.
    * @param node the subtree root to balance.
    * @return the new root of the balanced subtree.
    */
    public Entry<T> balance(Entry<T> node) {
        int balanceFactor = getBalanceFactor(node);

        if (balanceFactor > 1) {
        	
            if (getBalanceFactor((Entry<T>) node.left) < 0) {
                node.left = rotateLeft((Entry<T>) node.left);
            }
            
            node = rotateRight(node);
        } 
        
        else if (balanceFactor < -1) {
        	
            if (getBalanceFactor((Entry<T>) node.right) > 0) {
                node.right = rotateRight((Entry<T>) node.right);
            }
            node = rotateLeft(node);
        }
        
        return node;
    }

    /**
     * Gets the balance factor of a node to determine if rotation is needed.
     * @param node the node for which to calculate the balance factor.
     * @return the balance factor of the node.
     */
    public int getBalanceFactor(Entry<T> node) {
        return node == null ? 0 : height((Entry<T>) node.left) - height((Entry<T>) node.right);
    }

    /**
     * Performs a right rotation on the specified node.
     * @param y the root of the subtree to rotate right.
     * @return the new root of the rotated subtree.
     */
    public Entry<T> rotateRight(Entry<T> y) {
        Entry<T> x = (Entry<T>) y.left;
        Entry<T> T2 = (Entry<T>) x.right;

        // do rotation
        x.right = y;
        y.left = T2;

        // update heights
        y.height = Math.max(height((Entry<T>) y.left), height((Entry<T>) y.right)) + 1;
        x.height = Math.max(height((Entry<T>) x.left), height((Entry<T>) x.right)) + 1;

        return x;
    }

    /**
     * Performs a left rotation on the specified node.
     * @param x the root of the subtree to rotate left.
     * @return the new root of the rotated subtree.
     */
    public Entry<T> rotateLeft(Entry<T> x) {
    	
        Entry<T> y = (Entry<T>) x.right;
        Entry<T> T2 = (Entry<T>) y.left;

        // do rotation
        y.left = x;
        x.right = T2;

        // update heights
        x.height = Math.max(height((Entry<T>) x.left), height((Entry<T>) x.right)) + 1;
        y.height = Math.max(height((Entry<T>) y.left), height((Entry<T>) y.right)) + 1;

        return y;
    }

    /**
     * Retrieves the height of the specified node.
     * @param node the node for which to retrieve the height.
     * @return the height of the node; 0 if the node is null.
     */
    public int height(Entry<T> node) {
        return node == null ? 0 : node.height;
    }

    /**
     * Verifies if the tree is balanced and satisfies the AVL tree properties.
     * @return true if the tree is balanced; false otherwise.
     */
    public boolean verify() {
        if (size == 0) 
        	return true;
        return verify((Entry<T>) root).flag;
    }

    /**
     * Represents the result of verifying a subtree, including its balance status, height, minimum, and maximum elements.
     */
    public static class VerifyResult<T> {
    	
        boolean flag;
        T min;
        T max;
        int height;

        VerifyResult(boolean flag, T min, T max, int height) {
            this.flag = flag;
            this.min = min;
            this.max = max;
            this.height = height;
        }
    }

    /**
     * Recursively verifies the AVL properties of the subtree rooted at the specified node.
     * @param node the root of the subtree to verify.
     * @return a VerifyResult containing the verification results for the subtree.
     */
    public VerifyResult<T> verify(Entry<T> node) {
        if (node == null) {
            return new VerifyResult<>(true, null, null, 0);
        }

        VerifyResult<T> left = verify((Entry<T>) node.left);
        VerifyResult<T> right = verify((Entry<T>) node.right);

        if (!left.flag || !right.flag || (left.max != null && left.max.compareTo(node.element) >= 0) || 
            (right.min != null && right.min.compareTo(node.element) <= 0)) {
            return new VerifyResult<>(false, null, null, 0);
        }

        int balanceFactor = left.height - right.height;
        
        if (Math.abs(balanceFactor) > 1 || node.height != 1 + Math.max(left.height, right.height)) {
            return new VerifyResult<>(false, null, null, 0);
        }

        T min = left.min != null ? left.min : node.element;
        T max = right.max != null ? right.max : node.element;

        return new VerifyResult<>(true, min, max, node.height);
    }
    
}
