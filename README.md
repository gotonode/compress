# Compress
*Compare Huffman coding and LZW*

| Travis CI | Codacy |
| :-: | :-: |
|[![Build Status](https://travis-ci.org/gotonode/compress.svg?branch=master)](https://travis-ci.org/gotonode/compress) | [![Codacy Badge](https://api.codacy.com/project/badge/Grade/89a0544739ac4db8a43db10c8668d9ce)](https://www.codacy.com/app/gotonode/compress?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=gotonode/compress&amp;utm_campaign=Badge_Grade) |

In this project, you can compress files using either **Huffman coding** or **LZW (Lempel-Ziv-Welch)**. Decompression, as well as comparisons between the two algorithms, are also possible.

App documentation:
* [Manual](docs/MANUAL.md) (this might interest the general user the most)
* [Definition](docs/DEFINITION.md) (what was planned)
* [Implementation](docs/IMPLEMENTATION.md) (how it turned out)
* [Testing](docs/TESTING.md) (how testing was done)

Weekly reports:
* [Week 1 Report](docs/WEEK1.md)
* [Week 2 Report](docs/WEEK2.md)
* [Week 3 Report](docs/WEEK3.md)

##### The problem

To reduce the size of a file, and to be able to return the original file from the size-reduced file. This is also known as "lossless compression" (compared to "lossy comparison", which results in irrevocable data loss such as with JPEG files).

##### Algorithms

Two different algorithms are compared in this project.

| algorithm | GitHub (source) | Wikipedia (info) | time complexity | space complexity |
| :-------  | :----- | :--- | :-------------- | :--------------- |
| Huffman coding | [source](src/main/java/io/github/gotonode/compress/algorithms/huffman) | [info](https://en.wikipedia.org/wiki/Huffman_coding) | O(n) | O(n) |
| LZW (Lempel-Ziv-Welch)| [source](src/main/java/io/github/gotonode/compress/algorithms/lzw) | [info](https://en.wikipedia.org/wiki/Lempel%E2%80%93Ziv%E2%80%93Welch) | O(n) | O(n) |

##### Sample data

You'll find sample data that you can use in the [data](data) folder. The contents of that folder are in the public domain.

More data (not created by me) can be found online with search query "`data compression corpora`".

| name | type | format | size | contents |
| :- | :- | :- | :-| :-|
| [lorem_ipsum.txt](data/lorem_ipsum.txt) | TXT | text file | 92,6 KB | Lorem Ipsum |
| [lorem_ipsum.docx](data/lorem_ipsum.docx) | DOCX | Word document | 41,2 KB | Lorem Ipsum |
| [lorem_ipsum.pdf](data/lorem_ipsum.pdf) | PDF | PDF document | 199 KB | Lorem Ipsum |
| [nice_picture.jpeg](data/nice_picture.jpeg) | JPEG† | JPEG picture | 1042 KB | an old picture of my keychain |
| [just_an_image.png](data/just_an_image.png) | PNG | PNG image | 44,3 KB | some Java code |
| [random_noise.wav](data/random_noise.wav) | WAV | WAV audio | - KB | random noise |

† the real name of a JPEG file is with the E ("Experts"), not just JPG without it (this was due to the 8.3 limit of older systems)
