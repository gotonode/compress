# Week 2

##### What I did this week

I've made the skeleton for the user interface. It's currently not yet working, but the gist of it can already be understood. A lot of studying has been going on, as well as first test versions of both the Huffman and LZW. They only work with strings right now, not with binary files.

##### Progress

Work has been started on Huffman and LZW. One can now turn a string into a Huffman-coded binary sequence and back again. Same with LZW, although it produces different results. Those algorithms are not to be yet considered correct.

The user has several options regarding the UI already:
* Compress a file using Huffman
* Compress a file using LZW
* Decompress a file
* Benchmark Huffman against LZW
* Print instructions
* Exit the app

What each command will do can be tested, although they don't yet actually do anything.

##### What I learned this week

Having gone through the code for Huffman in various forms (pseudo, Java, JavaScript, C#), I learned that there are many implementations.

Not all implementations produce the same binary sequences for the same data. It also depends on how the trees are built, if the left node is 0 and the the right node is 1 or the other way around.

When decoding a Huffman-coded binary sequence, it's best done with the same app that did the original coding in the first place.

Bitwise operations are very interesting, and it's clear one must know a good deal about them to implement either of these algorithms.

##### What is unclear

How to read binary data and how to write binary data. I think that'll be at least a minor challenge for the next week.

I need to create some data structures myself. Does the `File`-object need to be created by hand? That would seem like a big challenge. How about `BufferedInputStream` and `BufferedOutputStream`? I think I'll be using those two to read and write my data.

But before I write any needed structures, I'll be using Java's own and get Huffman and LZW to work with them.

##### What I'll do next

I'll see how to read binary data and how to write binary data.

Rewriting of both Huffman and LZW will need to be done. At the present stage, they only operate on strings provided by the user, and not on a bit level. I'll follow the Princeton University's example as I code the necessary functionality in. I wrote them in as a test to see how they work and if I had understood everything correctly up to that point. It seems so.

More tests will be added later when there's functionality to be tested (rather than just UI printing).

##### Hours used

This week consumed 19 hours. But some of which was used watching videos on bitwise (etc) operations and practicing on paper (such as `>>`, `|`, `&`).
