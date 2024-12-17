# Multi-Dimensional Search Implementation

## Overview
This project implements efficient multi-dimensional search operations for a product catalog, similar to platforms like **Amazon**. Each product has an `id`, `price`, and a `description` consisting of zero or more integers. Operations such as insert, delete, search, and price range queries are supported using optimal data structures.

---

## Project Description
Implement the following operations. Starter code is provided.  
- **Do not change the name of the class.**  
- **Do not change the signatures of public methods in the starter code.**  

The input involves tens of thousands of products, each with multiple attributes. To support efficient search based on product attributes, appropriate **data structures** (such as balanced trees) are used. The operations allow insertion, deletion, and queries based on IDs, prices, and descriptions.  

---

## Operations

1. **Insert(id, price, list)**  
   - Inserts a new item with the given `id`, `price`, and `description` (list of integers).  
   - If an entry with the same `id` already exists:
     - Updates its description and price.  
     - If the description list is null or empty, only the price is updated.  
   - **Returns**: `1` if the item is new, `0` otherwise.

2. **Find(id)**  
   - Returns the price of the item with the given `id`.  
   - **Returns**: `0` if no such item exists.

3. **Delete(id)**  
   - Deletes the item with the given `id`.  
   - **Returns**: The sum of integers in the description of the deleted item.  
   - Returns `0` if the item does not exist.

4. **FindMinPrice(n)**  
   - Finds all items whose description contains the number `n`.  
   - **Returns**: The lowest price among those items, or `0` if no such items exist.

5. **FindMaxPrice(n)**  
   - Finds all items whose description contains the number `n`.  
   - **Returns**: The highest price among those items, or `0` if no such items exist.

6. **FindPriceRange(n, low, high)**  
   - Finds the number of items whose description contains the number `n`, and whose price falls within the range `[low, high]`.  
   - **Returns**: The count of such items.

7. **RemoveNames(id, list)**  
   - Removes elements of the `list` from the description of the item with the given `id`.  
   - **Returns**: The sum of the integers actually deleted from the description.  
   - Returns `0` if no such `id` exists.

---

## Input Specification
- Initially, the store is **empty** (no products).  
- The input contains multiple lines, each representing an operation.  
- Lines starting with `#` are **comments** and should be ignored.  
- `Insert` operations include a trailing `0` that is not part of the description.  

---

## Sample Input and Output

### Sample Input:
```text
Insert 22 19 475 1238 9742 0
# New item with id=22, price=19, description=[475, 1238, 9742]

Insert 12 96 44 109 0
# New item with id=12, price=96, description=[44, 109]

Insert 37 47 109 475 694 88 0
# New item with id=37, price=47, description=[109, 475, 694, 88]

FindMaxPrice 475
# Return: 47 (id=37 has the highest price among matches)

Delete 37
# Return: 1366 (=109+475+694+88, sum of description of deleted item)

FindMaxPrice 475
# Return: 19 (id=22 is now the only match)

End

Output : 1435
```

# How to Run
- Ensure the provided starter code and any test cases are in the project folder.
- Compile and run the program using your preferred IDE or terminal.
- Use test input files with a variety of operations to validate functionality.

---

# Key Features
- Efficient multi-dimensional search using appropriate data structures.
- Handles operations such as insertion, deletion, and various search queries efficiently.
- Designed to process large datasets with millions of operations.
