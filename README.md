# Compress
*Compress, decompress and benchmark using Huffman coding and LZW*

| Travis CI | Codacy | Codecov |
| :-: | :-: | :-: |
|[![Build Status](https://travis-ci.org/gotonode/compress.svg?branch=master)](https://travis-ci.org/gotonode/compress) | [![Codacy Badge](https://api.codacy.com/project/badge/Grade/89a0544739ac4db8a43db10c8668d9ce)](https://www.codacy.com/app/gotonode/compress?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=gotonode/compress&amp;utm_campaign=Badge_Grade) | [![codecov](https://codecov.io/gh/gotonode/compress/branch/master/graph/badge.svg)](https://codecov.io/gh/gotonode/compress) |

![App](https://github.com/gotonode/compress/blob/master/docs/images/app02.png)

> Please notice! The current LZW implementation works, but is very slow when using larger files (over 256 kB). The app will become unresponsive for the longest time if you try to compress a large file with LZW or use a large file with the benchmarking functionality. Thus, consider using smaller files (for now). Update! I have identified the issue. The loop in question can be found [here](https://github.com/gotonode/compress/blob/517a857048fa58e3ab633f0eb4c0ec63c45b3b33/src/main/java/io/github/gotonode/compress/algorithms/lzw/LZW.java#L126). And the part that's causing 86 % of the compression time is [here](https://github.com/gotonode/compress/blob/517a857048fa58e3ab633f0eb4c0ec63c45b3b33/src/main/java/io/github/gotonode/compress/algorithms/lzw/LZW.java#L187). Both links are permalinks to past commits on GitHub, and those commits do not reflect current code.

In this project, you can compress files using either **Huffman coding** or **LZW (Lempel-Ziv-Welch)**. Decompression, as well as comparisons between the two algorithms, are also possible.

You can download a pre-built `JAR`-file from [here](https://github.com/gotonode/compress/releases).

#### App documentation
General interest:
* [Manual](docs/MANUAL.md) (how to use)

Advanced interest:
* [Definition](docs/DEFINITION.md) (what was planned)
* [Implementation](docs/IMPLEMENTATION.md) (how it turned out)
* [Testing](docs/TESTING.md) (how testing was done)
* [Performance](docs/PERFORMANCE.md) (how the algorithms stack up)

Geek interest:
* [Javadoc](https://gotonode.github.io/compress) (code documentation, updated manually)

#### Weekly reports
* [Week 1 Report](docs/WEEK1.md)
* [Week 2 Report](docs/WEEK2.md)
* [Week 3 Report](docs/WEEK3.md)
* [Week 4 Report](docs/WEEK4.md)
* [Week 5 Report](docs/WEEK5.md)

#### The problem

To reduce the size of a file, and to be able to return the original file from the size-reduced file. This is also known as "lossless compression" (compared to "lossy comparison", which results in irrevocable data loss such as with JPEG files).

There are two main reasons to apply compression techniques. First is that of storage; smaller-size files can more easily fit onto storage media, either HDD, SSD, optical media (CD/DVD/Blu-ray) or tape drives. The second reason is that less bandwidth is needed to transfer the data.

A compressed file must be decompressed before it's usable, and this incurs a time-delay. On slower systems, decompressing a file can take a considerable amount of time (this is why installing new software could take a long time, it's decompressing).

#### Algorithms

Two different algorithms are compared in this project.

| algorithm | GitHub (source) | Wikipedia (info) | time complexity | space complexity |
| :-------  | :----- | :--- | :-------------- | :--------------- |
| Huffman coding | [source](src/main/java/io/github/gotonode/compress/algorithms/huffman) | [info](https://en.wikipedia.org/wiki/Huffman_coding) | O(n) | O(n) |
| LZW (Lempel-Ziv-Welch)| [source](src/main/java/io/github/gotonode/compress/algorithms/lzw) | [info](https://en.wikipedia.org/wiki/Lempel%E2%80%93Ziv%E2%80%93Welch) | O(n) | O(n) |

#### Sample data

You'll find sample data that you can use in the [data](data) folder. The contents of that folder are in the public domain.

More data (not created by me) can be found online with search query "`data compression corpora`".

| name | type | format | size | contents | compresses |
| :- | :- | :- | :- | :- | :- |
| [binary_search_tree.java](data/binary_search_tree.java) | JAVA | Java source code | 12,8 KB | sampling of my code | very well |
| [keychain.jpeg](data/keychain.jpeg) | JPEG† | JPEG picture | 83,9 KB | an old picture of my keychain | very poorly |
| [lorem_ipsum.docx](data/lorem_ipsum.docx) | DOCX | Word document | 19,6 KB | Lorem Ipsum | poorly |
| [lorem_ipsum.pdf](data/lorem_ipsum.pdf) | PDF | PDF document | 95,4 KB | Lorem Ipsum | poorly |
| [lorem_ipsum.txt](data/lorem_ipsum.txt) | TXT | text file | 17,2 KB | Lorem Ipsum | very well |
| [screenshot.png](data/screenshot.png) | PNG | PNG image | 53,6 KB | screenshot from Windows | poorly |

† the real name of a JPEG file is with the E ("Experts"), not just JPG without it (this was due to the 8.3 limit of older systems)