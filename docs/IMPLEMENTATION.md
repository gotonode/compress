# Implementation

This document outlines the structure and functionality of the app.

#### General structure

Compress is a Java app with a console user interface. It can be used to compress and decompress files of any type using either Huffman or LZW. Benchmarking these two algorithms against each other is also possible.

Software design philosophies & techniques in use include:
* DRY (avoid code repetition)
* encapsulation
* inheritance
* packages
* package-level comments
* variable- and method-level comments
* unit testing (soft goal 80 % coverage)
* separation of concern
* single-purpose principle
* interface segregation principle

#### File structure

Compressed file contents are showcased here.

For Huffman:

| 32 bits | 32 bits | varies | varies |
| :-: | :-: | :-: | :-: |
| identification | data's length | Huffman table | Huffman data |

For LZW:

| 32 bits | 32 bits | varies | 8 or 16 bits |
| :-: | :-: | :-: | :-: |
| identification | data's length | LZW dictionary | ending codeword |

#### Package structure

Here we'll go over the different packages and what they contain.

##### Package "algorithms"

Contains two sub-packages, one for each of the algorithms in use. Please refer to these packages for more information.

Also contains `CompressAlgorithm.java` which is an interface that the algorithms implement. It defines that each algorithm should have a `compress`, a `decompress` and `toString` -functions.

##### Package "algorithms.huffman"

Everything related to Huffman coding is in this package. In `Huffman.java` we have the functions to compress and decompress files using that algorithm. It makes use of `HuffmanNode.java` which acts both as a node, and a tree (collection of such nodes).

##### Package "algorithms.lzw"

This package contains things related to LZW. `LZW.java` has the standard compression and decompression functionality, as well as the code for creating LZW nodes and LZW trees. For those, `LZWTree.java` and `LZWNode.java` are used.

##### Package "app"

Contains a single file, `App.java`, which serves as the engine for this project. This is created from `Main.java` in its respective package. The App-class is an instanced one, but only one should ever exist at a time.

The purpose of the App-class is to handle all of the high-level functioning of this program. It asks, via the UI class, the user for input and, also via the UI class, provides output to the console.

It also creates and invokes the necessary objects for Huffman and LZW compression, decompression and also benchmarking them against each other.

Results, either success or failure (or an exception) are reported by this object. For an example, running the benchmark from its respective package will only output the results as an object. It is up to this class to print to the console, via the UI class.

An infinite loop is used to repeatedly ask the user for commands, processing them, and providing output (success or error). Only when the user gives the exit command is the loop ended, at which point execution is returned to the `Main.java` calling file.

##### Package "benchmarking"

Benchmarking Huffman against LZW is done by utilising `Benchmark.java` from this package.

It takes as input a file to be used in the benchmarking process, and produces, as output, a BenchmarkResult-object from the `BenchmarkResult.java` file.

The aforementioned results object is a simple "struct" that contains three pieces of information; compression time (in milliseconds), decompression time (in milliseconds), and the file size of the compressed file (in bytes). All of these values are of the type long.

This functionality is called by the App-object. First, it runs the benchmarking code on the Huffman algorithm, and stores the results in a local variable. Then, it runs the same code, using the exact same input file, on LZW and stores the results.

Printing the results about the benchmark operation is not handled by this package in any way. It just executes the benchmark and creates objects to store the relevant results. Results are printed to the console by the App-class, via the UI-object.

##### Package "enums"

Three files are housed here. `Algorithms.java` contains all of the algorithms used in this app (currently Huffman and LZW), and associated with each of the enums is their actual, longer name.

`Commands.java` has all of the commands that the user can use, and the character codes that are required to invoke them. For an example, the character 'B' is used to invoke the benchmarking functionality.

Finally, `TextStyles.java` is used to style the output text in various ways (colors, boldness etc). Optionally, these styles can be disabled from `Main.java` if they are causing issues.

##### Package "io"

Functionality related to input/output (IO) is found here.

`BinaryReadTool.java`, as the name implies, handles reading binary data from the input file. It does not write anything to anywhere. For convenience, functionality to read in various lengths of binary data are provided (one bit, a byte, a character, an integer, a String etc).

`BinaryWriteTool.java` is the opposite of the read tool. This one writes binary data, and never reads any. An output file is specified, into which the data is written.

Both of the aforementioned tools use a buffer to store written bits. If the buffer is full, at 8 bits (1 byte), it is written to the stream. And once the compression/decompression operation (Huffman, LZW) is complete, the stream is flushed into the output file.

`IO.java` is a file who's future is uncertain. It encapsulates basic IO handling, but it might be removed in the future.

##### Package "main"

The main entry point for this app is in `Main.java`. It has the static main-method, as such:

```java
public static void main(String[] args) {
    // Instantiate App-object, call it's run-method, exit the program.
}
```

This file is not tested and is ignored in code coverage.

The purpose of this class is to instantiate the App-object. Before that, it creates UiController and IO -objects which it passes to the App-object via its constructor.

Once that's done, it calls the App-object's run-method which contains an infinite loop. Once that loop is exited (by user request), control is returned to this file and the program will terminate. 

`Main.java` also has a lot of static final variables, such as:

```java
public static final String APP_NAME = "Compress";
public static final String APP_URL = "https://github.com/gotonode/compress/";
```

These are callable from anywhere and cannot be changed at runtime. They are commented well.

##### Package "ui"

Not to be confused with IO. User-interface, and everything related to it, is handled here. An UI object is passed to the App-object, which calls the methods in the UI-object to provide basic input/output services such as printing to the console and reading input from the user.

`UiController.java` has a lot of public methods. Here are some as an example:

```java
public void printGreetings() {
    System.out.println("Welcome to " + titleText(Main.APP_NAME) + " (version " + Main.APP_VERSION + ")!");
}

public void printGoodbye() {
    System.out.println("Thanks for using " + titleText(Main.APP_NAME) + ". Come back soon!");
}
```

This is the only class that calls any `System.out` functions. A Scanner-object is passed to this class, and that is used to read data from the console.

Of interesting note are functions which ask the user for either a single character or a string value. These are implemented in a way that the only way to exit from them is to provide a valid value.

Asking the user for a character value will continuously do so until a valid value is given. Hints are given to the user as to what to do next, or which commands are available.

When this class is used to ask for string values, they are primarily used to indicate the input and output files for the algorithms to use. The user cannot enter an empty value. 

##### Package "utils"

Data structures in this project are implemented from scratch, and the only one of general function (apart from the trees and nodes used by Huffmand and LZW) is the minimum priority queue.

`MinQueue.java` acts as that minimum priority queue data structure. It has the following functionality:
* `offer(Type type)` adds an object into the queue, arranging the queue as necessary
* `poll()` returns the next object from the queue (with the minimum value)
* `getSize()` returns an integer indicating how many objects are present in this queue

It also has a constructor that takes no parameters. The size is defined dynamically and does not need to be defined in advance.

#### Shortcomings

If the output file exists, it will be overwritten without asking the user if they want to overwrite it. However, in the UI, information that this will happen is presented, so it shouldn't come as a surprise.

LZW is working fine in regards to decompression and output file size. But currently it's compression time is way too long. Much bigger files can't be compressed at all, or the time it would take to do so would be too great. I'll see if I can fix this by the end of the course.

On some systems, if the console (terminal, shell) doesn't interpret the ANSI escape sequences properly, they might be printed in with the normal output text. This creates a mess. But on most systems this shouldn't be an issue.

Here's how it would look if the ANSI escaping doesn't work.

```text
Normal text here, and \u001B[1mHIGHLIGHTED TEXT HERE\u001b[0m, and again normal text here.
```

Each file compressed via this app has identification information at the beginning to indicate which compression algorithm was used to compress it. If that information is missing or invalid, the app won't attempt to decompress that file. However, it is feasible that a file not compressed by this app has data such that it matches the identification information, in which case the resulting decompressed file is not what the user would expect. Furthermore, and this is especially true with Huffman coding, when the app reads the data's length from the compressed file, that value might be really big and so the decompressed mangled file would also end up being very large. Potentially, the new file might cause the user's storage space to run out within minutes.

At first, I was using a simple bit to indicate the algorithm (0 = Huffman, 1 = LZW), but later decided to change to a complete 32-bit integer. This is to make it even less likely that the app would accept a non-compressed file for decompression. Huffman will get 0x‭AAAAAAAA (‭‭2863311530‬) and LZW will get 0x‭‭BBBBBBBB (‬‭3149642683‬) as their identification integers. This of course creates more overhead (32 bytes), which makes compressing very small files less productive. But it might still be worth it. Choosing integers such as 0 or 2^32 - 1 for the ID is not optimal due to them being so common.

The biggest shortcoming, however, is LZW compression performance. Here's some sample output from my tests:

```text
Name of your source file (must already exist): data/keychain.jpeg
Name of your target file (will be overwritten if it exists): keychain.lzw
Time spent finding prefixes (ms): 175
Time spent writing codewords (ms): 82
Time spent adding nodes to tree (ms): 11
Time spent seeking forward (ms): 1633
Total time spent (ms): 1901
Done! Compression with LZW was successful, and your new and tiny file is located at 'keychain.lzw'.
```

As can be seen, about 86 % of the total operation time (compression) is taken up by what I chose to call seeking forward. Essentially it's this part of the code:

```java
data = data.substring(prefixLength);
```

For the next iteration of the loop, we use a subset of the data for finding prefixes for the codewords. It would seem that, in Java at least, this internal algorithm isn't the most effective for what we're trying to accomplish.

Finding a better way to do this (perhaps with just an index integer) would net the greatest compression performance gains.

#### Sources

Please see the following for more information.

Generic (both):
* [Algorithms, Part II](https://www.coursera.org/learn/algorithms-part2) (Coursera)
* [Compression Algorithms: Huffman and Lempel-Ziv-Welch (LZW)](http://web.mit.edu/6.02/www/s2012/handouts/3.pdf) (MIT)

Huffman:
* [Huffman coding](https://en.wikipedia.org/wiki/Huffman_coding) (Wikipedia)
* [Huffman Code](https://brilliant.org/wiki/huffman-encoding/) (Brilliant.org)

Lempel-Ziv-Welch:
* [Lempel–Ziv–Welch](https://en.wikipedia.org/wiki/Lempel%E2%80%93Ziv%E2%80%93Welch) (Wikipedia)
* [LZW (Lempel-Ziv-Welch) Compression technique](https://www.geeksforgeeks.org/lzw-lempel-ziv-welch-compression-technique/) (GeeksforGeeks)
