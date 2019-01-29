# Week 3

##### What I did this week

Work on Huffman and also on my binary reader and binary writer. Huffman is completed.

##### Progress

FINALLY! This week has been a big binary hurdle! Let's just say that hours upon hours went into debugging.

I started working on Huffman immediately on Saturday, and only now it is complete.

So Huffman is working, and I just ran 10,000 random binary files through it (compress + decompress) and compared that the decompressed file was identical to the original file.

##### What I learned this week

At first, I had a single class for reading in and writing binary data. This quickly became cumbersome as I had to juggle with variables and name them like "readBuffer" and "writeBuffer". I then just decided to split the two, since Java does it also (with classes such as `BinaryInputStream` and `BinaryOutputStream`).

About a few hours' worth of headache was caused because I forgot to change the input and output file designations. I was wondering why it couldn't read any data, but that was because I was reading from an empty file.

So what I learned is to never assume and to check first. Perhaps next time I won't need to use 3 hours on something trivial such as this, but start debugging from the very core.

I also used Apache Common's IO module. It's only for testing, so I don't think I'll need to implement its used functionality myself. I only use the functionality to compare two files from it (to see if the original and the compressed + decompressed files are identical).

##### What is unclear

How to split up tests on my Huffman coding, since I'd of course like to test compression and decompression in a different method, but how can one test decompression if there's nothing to decompress. So first I'll have to compress something; or, alternatively, supply test data with my program (but I'd like to avoid this and generate all of them on the fly).

I added a bit 0 into the beginning of the file to indicate it's a Huffman coded file, and a bit 1 for LZW files. An alternative is to ask the user each time which algorithm was used. But I'm not sure adding that bit is a good thing.

##### What I'll do next

Next week I'll implement LZW. I already understand how it works (creating a dictionary of fixed size elements from variable length data). Because I've already created my binary reader and binary writer, my hope is that I can use them directly with with the LZW (only minimal changes).

I haven't commented every important part so I'll go over all of my code and add the necessary clarifications where needed.

##### Hours used

So far I used 22 hours this week trying to get Huffman and binary reading/writing to work. But to be fair, I've never done actual binary reading/writing directly, only single characters at most.