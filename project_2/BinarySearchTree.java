/**
 * @author Manthra Natarajan
 * Binary search tree (starter code)
 **/

package project2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Scanner;

public class BinarySearchTree<T extends Comparable<? super T>> implements Iterable<T> {
    static class Entry<T> {
        T element;
        Entry<T> left, right;

        public Entry(T x, Entry<T> left, Entry<T> right) {
            this.element = x;
            this.left = left;
            this.right = right;
        }
    }

    Entry<T> root;
    int size;

    
    /**
     * Constructor to initialize an empty Binary Search Tree.
     */
    public BinarySearchTree() {
        root = null;
        size = 0;
    }


    // implementing the helper methods
    
    // Helper method to find element x in BST
    // Returns node where x is found, or last non-null node on search path
    
    /**
     * Helper method to find the entry containing the specified element in the tree.
     * @param x the element to search for.
     * @return the entry containing x if found; otherwise, the last non-null entry along the search path.
     */
    Entry<T> find(T x) {
        Entry<T> t = root;
        Entry<T> prev = null;
        
        while(t != null) {
        	
            prev = t;
            int cmp = x.compareTo(t.element);
            
            if(cmp == 0) {
                return t;
            }
            
            t = cmp < 0 ? t.left : t.right;
        }
        
        return prev;
    }
    
    // Helper method to find parent of node that contains x
    // Return value: last node seen on search path to x
    
    /**
     * Helper method to find the parent of the node containing the specified element.
     * @param x the element to find the parent for.
     * @return the parent entry of the node containing x, or the last entry along the search path if x is not found.
     */
    Entry<T> findParent(T x) {
    	
        Entry<T> t = root;
        Entry<T> prev = null;
        
        while(t != null && !t.element.equals(x)) {
        	
            prev = t;
            t = x.compareTo(t.element) < 0 ? t.left : t.right;
            
        }
        
        return prev;
    }
    
    
    /**
     * Checks if the tree contains the specified element.
     * @param x the element to search for.
     * @return true if x is found in the tree anf false otherwise.
     */
    public boolean contains(T x) {
        if(root == null) 
        	return false;
        
        Entry<T> node = find(x);
        
        return node != null && x.compareTo(node.element) == 0;
    }

    /**
     * Adds a new element to the tree if it is not already present.
     * @param x the element to be added.
     * @return true if x was successfully added, false if x was already in the tree.
     */
    public boolean add(T x) {
    	
        if(root == null) {
            root = new Entry<>(x, null, null);
            size++;
            return true;
        }
        
        Entry<T> node = find(x);
        int i = x.compareTo(node.element);
        
        if(i == 0) {
            node.element = x;  // replace existing element
            return false;
        }
        
        // create new node and attach it to parent
        Entry<T> newNode = new Entry<>(x, null, null);
        
        if(i < 0) {
            node.left = newNode;
        } 
        
        else {
            node.right = newNode;
        }
        
        size++;
        return true;
    }

    /**
     * Removes the specified element from the tree, if it exists.
     * @param x the element to be removed.
     * @return the removed element if it was found and removed, or null otherwise.
     */
    public T remove(T x) {
        if(root == null) 
        	return null;
        
        // find node to be removed uding helper method
        Entry<T> parent = findParent(x);
        Entry<T> node;
        
        // if root is the one to find, removing root here
        if(parent == null) {
        	
            if(!root.element.equals(x)) return null;
            node = root;
            
        } else {
        	
            // find which child of parent contains x
            if(parent.left != null && parent.left.element.equals(x)) {
                node = parent.left;
            } 
            
            else if(parent.right != null && parent.right.element.equals(x)) {
                node = parent.right;
            } 
            
            else {
                return null;
            }
        }

        T result = node.element;

        // if the node to be removed has no children
        if(node.left == null && node.right == null) {
            if(node == root) {
                root = null;
            } else if(parent.left == node) {
                parent.left = null;
            } else {
                parent.right = null;
            }
        }
        
        // if the node to be removed has one child
        else if(node.left == null) {
            if(node == root) {
                root = node.right;
            } else if(parent.left == node) {
                parent.left = node.right;
            } else {
                parent.right = node.right;
            }
        }
        
        else if(node.right == null) {
            if(node == root) {
                root = node.left;
            } else if(parent.left == node) {
                parent.left = node.left;
            } else {
                parent.right = node.left;
            }
        }
        
        // if the node to be removed has two children
        else {
            // Find minimum element in right subtree (successor)
            Entry<T> minRight = node.right;
            Entry<T> minRightParent = node;
            
            while(minRight.left != null) {
                minRightParent = minRight;
                minRight = minRight.left;
            }
            
            // replace nodes element with successors element
            node.element = minRight.element;
            
            // remove successor, it has at most one child on the right
            if(minRightParent == node) {
                minRightParent.right = minRight.right;
            } 
            
            else {
                minRightParent.left = minRight.right;
            }
        }

        size--;
        return result;
    }

    public static void main(String[] args) throws FileNotFoundException {
        BinarySearchTree<Long> bst = new BinarySearchTree<>();
        Scanner sc;
        if (args.length > 0) {
            File file = new File(args[0]);
            sc = new Scanner(file);
        } else {
            sc = new Scanner(System.in);
        }
        String operation = "";
        long operand = 0;
        int modValue = 999983;
        long result = 0;
        // Initialize the timer
        Timer timer = new Timer();

        while (!((operation = sc.next()).equals("End"))) {
            switch (operation) {
                case "Add": {
                    operand = sc.nextInt();
                    if (bst.add(operand)) {
                        result = (result + 1) % modValue;
                    }
                    break;
                }
                case "Remove": {
                    operand = sc.nextInt();
                    if (bst.remove(operand) != null) {
                        result = (result + 1) % modValue;
                    }
                    break;
                }
                case "Contains": {
                    operand = sc.nextInt();
                    if (bst.contains(operand)) {
                        result = (result + 1) % modValue;
                    }
                    break;
                }
            }
        }

        // End Time
        timer.end();

        System.out.println(result);
        System.out.println(timer);
    }


    public void printTree() {
        System.out.print("[" + size + "]");
        printTree(root);
        System.out.println();
    }

    // Inorder traversal of tree
    void printTree(Entry<T> node) {
        if (node != null) {
            printTree(node.left);
            System.out.print(" " + node.element);
            printTree(node.right);
        }
    }
}
