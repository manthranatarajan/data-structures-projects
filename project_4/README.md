# PERT Algorithm Implementation

## Overview
This project implements the **PERT (Program Evaluation and Review Technique)** algorithm to calculate the earliest and latest start times for project tasks, ensuring an efficient project schedule. The implementation includes topological sorting using a **DFS-based algorithm** and adheres to provided starter code.

---

## Project Description
Implement the **PERT algorithm** as discussed in class. Starter code (`PERT.java`) is provided along with the driver code (`P4Driver.java`) and test cases in the **Project 4 folder** (shared box folder).

- **Do not change the name of the class.**  
- **Do not modify the `Graph` class** except for updating the `netid`.  
- **Do not change the signatures of public methods** in the starter code.  
- If additional methods are needed, define them as **nested classes** within the `PERT` class.  

The implementation ensures correctness and efficiency for calculating project timelines, including identifying critical tasks.

---

## Operations and Requirements

1. **Topological Sort**  
   - Implement a **DFS-based algorithm** to determine a **topological order** of tasks in the project.  
   - This ordering is crucial for determining earliest and latest start times.  

2. **Calculate Task Times**  
   - Implement the PERT algorithm to compute:  
     - **Earliest Start Time (EST)**  
     - **Latest Start Time (LST)**  
     - **Slack Time** for each task.  

3. **Critical Path Identification**  
   - Tasks with **zero slack** are identified as critical tasks. These tasks determine the project's critical path.  

---

## Input Specification
- The project input is provided as a **directed acyclic graph (DAG)** where:  
   - Nodes represent tasks.  
   - Directed edges represent task dependencies.  
   - Each node includes a task duration.  

- The input includes multiple test cases provided in the **driver code** (`P4Driver.java`).

---

## Expected Output
The program outputs the following:  
1. **Topological Order** of the tasks.  
2. **Earliest Start Times (EST)** and **Latest Start Times (LST)** for all tasks.  
3. Tasks that lie on the **Critical Path** (tasks with zero slack).  

---

## Implementation Strategy

1. **Graph Representation**  
   - Use the provided `Graph` class to represent the project tasks and dependencies.  

2. **Topological Sorting**  
   - Implement a **DFS-based topological sorting** algorithm to determine the task execution order.  
   - This ordering ensures that dependencies are respected when calculating task times.  

3. **PERT Algorithm**  
   - Compute the **Earliest Start Times (EST)** using a forward pass.  
   - Compute the **Latest Start Times (LST)** using a backward pass.  
   - Calculate **slack time** for each task:  
     \[
     \text{Slack} = \text{LST} - \text{EST}
     \]  

4. **Critical Path**  
   - Identify and output tasks with **zero slack**, as these tasks form the project's critical path.  

---

