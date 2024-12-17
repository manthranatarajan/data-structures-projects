# Binary Search Tree and AVL Tree Implementation

## Overview
This project focuses on implementing a **Binary Search Tree (BST)** and extending it to create an **AVL Tree**. Both data structures maintain sorted data and allow efficient operations for search, insertion, and deletion.

---

## Project Structure

### 1. Binary Search Tree (BST)
Implements a Binary Search Tree with essential operations.

- **Starter Code**: `BinarySearchTree.java`
- **Implemented Methods**:  
  - `contains()` – Checks whether a specific value exists in the tree.  
  - `add()` – Inserts a new value into the tree using the **single-pass algorithm**.  
  - `remove()` – Deletes a value from the tree using the single-pass algorithm.

**Note**: No `parent` field is defined in the `Entry` class.

---

### 2. AVL Tree
Extends the Binary Search Tree to implement a **self-balancing AVL Tree**.

- **Starter Code**: `AVLTree.java`  
- **Implemented Methods**:  
  - `add()` – Inserts values while maintaining the AVL property using rotations.  
  - `verify()` – Ensures the tree satisfies AVL balance conditions.

---

## How to Run
1. Ensure you have `BinarySearchTree.java` and `AVLTree.java` in your project directory.  
2. Use the provided **driver code** to test the functionality of BST and AVL Tree.

---

## Key Features
- Efficient single-pass insertion and deletion for BST.  
- AVL Tree maintains **balance** after every insertion/deletion using rotations.  
- Code reusability through inheritance: AVL Tree inherits methods like `find()`, `add()`, and `remove()` from the BST.

---

## Extra Notes
- Rotations are performed to ensure AVL Tree height remains O(log n).  
- The AVL Tree implementation strictly adheres to class instructions for clarity and correctness.
