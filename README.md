# Compress
*Compress, decompress and benchmark using Huffman coding and LZW*

| Travis CI | BCH | Codacy | Codecov | codebeat | CodeFactor | LGTM (alerts) |
| :-: | :-: | :-: | :-: | :-: | :-: | :-: |
|[![Build Status](https://travis-ci.org/gotonode/compress.svg?branch=master)](https://travis-ci.org/gotonode/compress) | [![BCH compliance](https://bettercodehub.com/edge/badge/gotonode/compress?branch=master)](https://bettercodehub.com/) | [![Codacy Badge](https://api.codacy.com/project/badge/Grade/89a0544739ac4db8a43db10c8668d9ce)](https://www.codacy.com/app/gotonode/compress?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=gotonode/compress&amp;utm_campaign=Badge_Grade) | [![codecov](https://codecov.io/gh/gotonode/compress/branch/master/graph/badge.svg)](https://codecov.io/gh/gotonode/compress) | [![codebeat badge](https://codebeat.co/badges/2df89018-36e8-40c1-a9d2-7d229a223afa)](https://codebeat.co/projects/github-com-gotonode-compress-master) | [![CodeFactor](https://www.codefactor.io/repository/github/gotonode/compress/badge)](https://www.codefactor.io/repository/github/gotonode/compress) | [![Total alerts](https://img.shields.io/lgtm/alerts/g/gotonode/compress.svg?logo=lgtm&logoWidth=18)](https://lgtm.com/projects/g/gotonode/compress/alerts/) |

![App](https://github.com/gotonode/compress/blob/master/docs/images/app02.png)

> Please notice! The current LZW implementation works, but is very slow when using larger files (over 0.5 MB). The app will become unresponsive for the longest time if you try to compress a large file with LZW or use a large file with the benchmarking functionality. Thus, consider using smaller files (for now).

In this project, you can compress files using either **Huffman coding** or **LZW (Lempel-Ziv-Welch)**. Decompression, as well as comparisons between the two algorithms, are also possible.

You can download a pre-built `JAR`-file from [here](https://github.com/gotonode/compress/releases).

#### Documentation
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
* [Week 1 Report](docs/WEEK1.md) (first week)
* [Week 2 Report](docs/WEEK2.md)
* [Week 3 Report](docs/WEEK3.md)
* [Week 4 Report](docs/WEEK4.md)
* [Week 5 Report](docs/WEEK5.md)
* [Week 6 Report](docs/WEEK6.md) (final week)

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

##### Algorithm "Huffman"

Huffman works by counting the instances of specific characters from the data. It then creates a tree structure that maps each character as a sequence of bits. Characters that appear more frequently get a shorter bit string representation, and uncommon/rare characters get a longer string.

Usually, each character is encoded with either 8 or 16 bits. For an example, the character 'A' is 01000001. Huffman coding can encode 'A' to be 01 or 0110 for an example, potentially saving a lot of space.

##### Algorithm "LZW"

LZW works differently from Huffman. It creates a dictionary of strings (preferably long strings) that map to binary sequences. For an example, if textual data contains the word "welcome" many times, each entry of that word gets a binary representation that is shorter than the original (in the optimal scenario).

Thus, LZW works best with data that has many long, repeating strings containing the same data. Usually this is the case with text files, but not the case with binary files.

#### Sample data

You'll find sample data that you can use in the [data](data) folder. The contents of that folder are in the public domain.

More data (not created by me) can be found online with search query "`data compression corpora`".

| name | type | format | size | contents | compresses |
| :- | :- | :- | :- | :- | :- |
| [cities.sql](data/cities.sql) | SQL | SQL data | 6,39 KB | list of cities in Finland | very well |
| [keychain.jpeg](data/keychain.jpeg) | JPEG† | JPEG picture | 83,9 KB | an old picture of my keychain | very poorly |
| [lorem_ipsum.docx](data/lorem_ipsum.docx) | DOCX | Word document | 19,6 KB | Lorem Ipsum | poorly |
| [lorem_ipsum.pdf](data/lorem_ipsum.pdf) | PDF | PDF document | 95,4 KB | Lorem Ipsum | poorly |
| [lorem_ipsum.txt](data/lorem_ipsum.txt) | TXT | text file | 17,2 KB | Lorem Ipsum | very well |
| [screenshot.png](data/screenshot.png) | PNG | PNG image | 53,6 KB | screenshot from Windows | poorly |

† the real name of a JPEG file is with the E ("Experts"), not just JPG without it (this was due to the 8.3 limit of older systems)
