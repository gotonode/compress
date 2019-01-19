# Compress
*Compare Huffman coding and LZW*

In this project, you can compress files using either **Huffman coding** or **LZW**. Decompression, as well as comparisons between the two algorithms, are also possible.

* [App Definition](https://github.com/gotonode/compress/blob/master/DEFINITION.md)
* [Week 1 report](https://github.com/gotonode/compress/blob/master/WEEK1.md)

#### The problem

To reduce the size of a file, and to be able to return the original file from the size-reduced file. This is also known as "lossless compression" (compared to "lossy comparison", which results in irrevocable data loss such as with JPEG files).

#### Algorithms

Two different algorithms are compared in this project.

| algorithm | GitHub (source) | Wikipedia (info) | time complexity | space complexity |
| :-------  | :----- | :--- | :-------------- | :--------------- |
| Huffman coding | [source](https://github.com/gotonode/compress/blob/master/src/main/java/io/github/gotonode/compress/algorithms/Huffman.java) | [info](https://en.wikipedia.org/wiki/Huffman_coding) | O(n) | TBD |
| LZW | [source](https://github.com/gotonode/compress/blob/master/src/main/java/io/github/gotonode/compress/algorithms/LZW.java) | [info](https://en.wikipedia.org/wiki/Lempel%E2%80%93Ziv%E2%80%93Welch) | O(n) | TBD |
