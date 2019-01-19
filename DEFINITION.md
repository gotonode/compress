# Definition

*This document is similar to that of "App Requirements" or "App Specifications" documents.*

#### Algorithms

I'll be doing a comparison between the **Huffman coding** and **LZW coding** algorithms. Both are used to compress data in a lossless fashion. A lossless compression means that no data is lost in the compression/decompression process.

#### The problem

The smaller the files, the faster they can be trasnferred over a network. Likewise the less storage space they occupy while being stored on a client or a server somewhere. Both Huffman coding and LZW work in binary mode to compress documents of any type, so that they take up less space.

#### How to use

Compress is a console application, with no graphical user interface. The user interface is made up of ASCII characters.

Once the app is launched, the user can either compress a file, decompress a file, or compare the speeds of the two algorithms. In the first case (compressing a single file), the user must define whether to use Huffman coding or LZW. Decompression automatically detects the used compression method, and the benchmark will use both.

After that, the path to the file will be provided.

#### Input

The app ("Compress") will only read in binary data from files. The user cannot enter binary sequences into the console, but rather will input the path to the file in question. The path can either be absolute or relative.

An absolute path contains everything needed to know, starting from the root, like this:  
```C:\Users\gotonode\Apps\Compress\data.bin```

A relative path is relative to the app's location, such as:  
```.\data.bin```

#### Sample data

With this project, I'll be providing sample data so that the user can easily test the algorithms.

Currently planned data:

* Completely random ("pseudorandom") sequence of bits
* "Lorem ipsum" text in the order of a few megabytes
* Uncompresseable sequence of zero's
* An audio file (personally recorded)
* A video file (personally recorded)
* A PDF document (personally created)

I'll probably implement a feature which tests all of the files in a special `data`-folder with both of these algorithms and outputs the results to the console. Then the user can easily put files into this folder and run the benchmarks. At the end of the benchmarks, the created (compressed) files can be deleted.

#### Big O -notation

The time complexity for Huffman coding is O(n) and the space complexity is O(k) for the tree and O(n) for the coded data.

Same time complexity applies for LZW.

It'll be interesting to see how these two algorithms fare up against each other. Obvisouly implementation matters, and the type and quality of it. A LZW done will could be 10 times as fast as a Huffman done wrong.

#### Sources

* [Huffman coding on Wikipedia](https://en.wikipedia.org/wiki/Huffman_coding) (used as a strating point for reference)
* [Huffman Code on Brilliant.org](https://brilliant.org/wiki/huffman-encoding/)
* [Lempel–Ziv–Welch on Wikipedia](https://en.wikipedia.org/wiki/Lempel%E2%80%93Ziv%E2%80%93Welch) (used as a strating point for reference)
* [LZW (Lempel–Ziv–Welch) Compression technique on GeeksforGeeks](https://www.geeksforgeeks.org/lzw-lempel-ziv-welch-compression-technique/
