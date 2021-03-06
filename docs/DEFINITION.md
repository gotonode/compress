# Definition

Here we go over the build definition of the app. This outlines (roughly) how the app will/should turn out to be. Please refer to the [Implementation](IMPLEMENTATION.md) documentation to see a "a posteriori" technical documentation. It'll also more closely reflect the actual app as it is turning out to be.

#### Algorithms

I'll be doing a comparison between the **Huffman coding** and **LZW** algorithms. Both are used to compress data in a lossless fashion. A lossless compression means that no data is lost in the compression/decompression process.

#### The problem

The smaller the files, the faster they can be transferred over a network. Likewise the less storage space they occupy while being stored on a client or a server somewhere. Both Huffman coding and LZW work in binary mode to compress files of any type, so that they take up less space.

#### How to use

Compress is a console application, with no graphical user interface. The user interface is made up of ASCII characters.

Once the app is launched, the user can either compress a file, decompress a file, or compare the speeds of the two algorithms. In the first case (compressing a single file), the user must define whether to use Huffman coding or LZW. Decompression automatically detects the used compression method, and the benchmark will use both.

After that, the path to the file must be provided.

#### Input

The app will only read in binary data from files. The user cannot enter binary sequences into the console, but rather will input the path to the file in question. The path can either be absolute or relative.

An absolute path contains everything needed to know, starting from the root, like this:  
```C:\Users\gotonode\Apps\Compress\data.bin```

A relative path is relative to the app's location, such as:  
```.\data.bin```

#### Sample data

With this project, I'll be providing sample data so that the user can easily test the algorithms.

Currently planned data:

* Completely random ("pseudorandom") sequence of bits
* "Lorem ipsum" text in the order of a few megabytes
* Incompressible sequence of zero's
* An audio file (personally recorded)
* A video file (personally recorded)
* A PDF document (personally created)

I'll probably implement a feature which tests all of the files in a special `data`-folder with both of these algorithms and outputs the results to the console. Then the user can easily put files into this folder and run the benchmarks. At the end of the benchmarks, the created (compressed) files can be deleted.

#### Big O -notation

The time complexity for Huffman coding is O(n) and the same time complexity applies for LZW.

It'll be interesting to see how these two algorithms fare up against each other. Obviously implementation matters, and the type and quality of it. Huffman done well could be 10 times as fast as LZW done poorly.

#### Sources

* [Huffman coding on Wikipedia](https://en.wikipedia.org/wiki/Huffman_coding) (used as a starting point for reference)
* [Huffman Code on Brilliant.org](https://brilliant.org/wiki/huffman-encoding/)
* [Lempel–Ziv–Welch on Wikipedia](https://en.wikipedia.org/wiki/Lempel%E2%80%93Ziv%E2%80%93Welch) (used as a starting point for reference)