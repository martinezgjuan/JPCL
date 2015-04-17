# Java Programming Contest Library

JPCL is a Java library intended for programming contests.

#### Static methods to copy-and-paste in your code
The different classes and methods are implemented with the goal of being able to easily and rapidly copy-and-paste them in the solutions of the contest problems. This can very helpful in programming contests since most of them only allow to upload one file with all the code needed to solve each problem. Unfortunately, this also often results in a lack of object-oriented design and a considerable number of global variables.

#### Template with fast IO
A programming contest template is also included, containing (as a compact inner-class) the essential methods needed for efficient IO operations which are vital in many on-line contests.

#### Tested with Online Judges and Contests
Most classes/methods have been tested with problems from online judges and such problems will often be mentioned in the classes/methods documentations. The Online Judges and contests used include: UVa, ACM Live Archive, SPOJ, CodeChef, Codeforces, TopCoder, Google Code Jam, Facebook Hacker Cup, among others.

#### List of implemented classes/methods (in the "Java" directory):

* Graph
  * Biconnected Components, Articulation Points & Bridges
  * Graph Traversal
  * GraphUniqueEdge: Graph class without edge repetitions (unweighted edges)
  * GraphUnweighted: Simple graph class with unweighted edges
  * GraphWeighted: Graph class with weighted edges
  * Maximum Cardinality Bipartite Matching
  * Maximum Flow (Min Cut)
  * Minimum Spanning Tree
  * Miscellaneous Graph Methods/Algorithms
  * Shortest Paths (Single Source & All Pairs)
  * Strongly Connected Components
  * Topological Sort (including lexicographical)
* IndexDictionary: Creates a bijection between Generic classes and integers
* MedianBag: Allows efficient dynamic query of the median of a set of numbers
* Pair: Pair formed by 2 generic types
* Reader: Efficient input reader
* Segment Tree: Allows efficient updates and queries on ranges of elements
* Template: Programming contest Java template with fast IO
* Triple: Triple formed by 3 generic types
