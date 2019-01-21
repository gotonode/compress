# Compress
*Compare Huffman coding and LZW*

In this project, you can compress files using either **Huffman coding** or **LZW**. Decompression, as well as comparisons between the two algorithms, are also possible.

* [App Definition](https://github.com/gotonode/compress/blob/master/DEFINITION.md)
* [Week 1 Report](https://github.com/gotonode/compress/blob/master/WEEK1.md)
* [Week 2 Report](https://github.com/gotonode/compress/blob/master/WEEK2.md)

#### The problem

To reduce the size of a file, and to be able to return the original file from the size-reduced file. This is also known as "lossless compression" (compared to "lossy comparison", which results in irrevocable data loss such as with JPEG files).

#### Algorithms

Two different algorithms are compared in this project.

| algorithm | GitHub (source) | Wikipedia (info) | time complexity | space complexity |
| :-------  | :----- | :--- | :-------------- | :--------------- |
| Huffman coding | [source](https://github.com/gotonode/compress/blob/master/src/main/java/io/github/gotonode/compress/algorithms/Huffman.java) | [info](https://en.wikipedia.org/wiki/Huffman_coding) | O(n) | TBD |
| LZW | [source](https://github.com/gotonode/compress/blob/master/src/main/java/io/github/gotonode/compress/algorithms/LZW.java) | [info](https://en.wikipedia.org/wiki/Lempel%E2%80%93Ziv%E2%80%93Welch) | O(n) | TBD |

#### Sample data

You'll find sample data that you can use in the [data](data) folder. The contents of that folder are in the public domain.

| name | type | format | size | contents |
| :- | :- | :- | :-| :-|
| [lorem_ipsum.txt](data/lorem_ipsum.txt) | TXT | text file | 92,6 KB | Lorem Ipsum |
| [lorem_ipsum.docx](data/lorem_ipsum.docx) | DOCX | Word document | 41,2 KB | Lorem Ipsum |
| [lorem_ipsum.pdf](data/lorem_ipsum.pdf) | PDF | PDF document | 199 KB | Lorem Ipsum |
| [nice_picture.jpeg](data/nice_picture.jpeg) | JPEG† | JPEG picture | - KB | it's a nice picture |
| [just_an_image.png](data/just_an_image.png) | PNG | PNG image | 44,3 KB | some old Java code |
| [random_noise.wav](data/random_noise.wav) | WAV | WAV audio | - KB | random noise |

† the real name of a JPEG file is with the E ("Experts"), not just JPG without it (this was due to the 8.3 limit of older systems)
