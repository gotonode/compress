# Performance

Here you can read about the performance benchmarking run on the two algorithms (Huffman, LZW).

Because we are primarily interested in the size of the compressed file, we'll focus on that here. Compression and decompression speeds are of lesser concern in this case.

All of the files that we benchmarked (compression) were also decompressed to verify that the compression actually worked. The decompressed file was then compared with the original to see that they were an exact (100 %) match, and that not a single bit went missing or got corrupted.

Please note! These tests have been run on 2019-02-17. If your results differ using the same data, it's most likely because the algorithms have changed from that date. The test data is not updated after each new version.

#### Results (external data)

Here are the benchmarking results using the [Calgary corpus](https://en.wikipedia.org/wiki/Calgary_corpus).

The sizes are in bytes. Divide them by 1024 to get kilobytes (kB).

| file | original | Huffman | LZW | winner |
| :- | :- | :- | :- | :- |
| bib | 111261 | 72870 (- 34,51 %) | 53852 (- 51,6 %) | LZW |
| book1 | 768771 | 438484 (- 42,96 %) | 390818 (- 49,16 %) | LZW |
| book2 | 610856 | 368428 (- 39,69 %) | 346538 (- 43,27 %) | LZW |
| geo | 102400 | 72884 (- 28,82 %) | 78763 (- 23,08 %) | Huffman |
| news | 377109 | 246524 (- 34,63 %) | 232819 (- 38,26 %) | LZW |
| obj1 | 21504 | 16379 (- 23,83 %) | 16934 (- 21,25 %) | Huffman |
| obj2 | 246814 | 194424 (- 21,23 %) | *302551 (+ 22,58 %)* | Huffman |
| paper1 | 53161 | 33464 (- 37,05 %) | 31190 (- 41,33 %) | LZW |
| paper2 | 82199 | 47737 (- 41,93 %) | 47737 (- 49,30 %) | LZW |
| pic | 513216 | 106758 (- 79,20 %) | 70235 (- 86,31 %) | LZW |
| progc | 39611 | 26037 (- 34,27 %) | 24475 (- 38,21 %) | LZW |
| progl | 71646 | 43099 (- 39,84 %) | 34924 (- 51,25 %) | LZW |
| progp | 49379 | 30333 (- 38,57 %) | 23296 (- 52,82 %) | LZW |
| trans | 93695 | 65349 (- 30,25 %) | 50552 (- 46,05 %) | LZW |

And here they are illustrated as a graph. The sizes are in bytes and a lower value is better.

![App](https://github.com/gotonode/compress/blob/master/docs/images/results02.png)

#### Results (own data)

In the following table we list the original and compressed sizes for the files found in the [data](../data) folder.

The sizes are in bytes. Divide them by 1024 to get kilobytes (kB).

| file | original | Huffman | LZW | winner |
| :- | :- | :- | :- | :- |
| binary_search_tree.java | 13204 | 7404 (- 43,93 %) | 5201 (- 60,61 %) | LZW |
| keychain.jpeg | 85959 | 86172 (+ 0,25 %) | 120040 (+ 39,65 %) | Huffman |
| lorem_ipsum.docx | 20164 | 19418 (- 3,70 %) | 25267 (+ 25,31 %) | Huffman |
| lorem_ipsum.pdf | 97786 | 96221 (- 1,60 %) | 129392 (+ 32,32 %) | Huffman |
| lorem_ipsum.txt | 17638 | 9519 (- 46,03 %) | 7400 (- 58,05 %) | LZW |
| screenshot.png | 54984 | 54499 (- 0,88 %) | 72928 (+ 32,63 %) | Huffman |

Here's an illustrative image. The sizes are in bytes and a lower value is better.

![App](https://github.com/gotonode/compress/blob/master/docs/images/results01.png)

#### Q&A

* Why did the size increase in some of the cases?

  * Some files are already pretty well compressed, such as PDF files and JPEG files. Others, like TXT files, are not.

  * Compressing an already compressed file usually results in a compressed file that is about the same size, or perhaps a little bit bigger or smaller. The new compressed file might be bigger than the original because of the overhead involved. Overhead is data added to the input data for processing purposes.
  
  * It would seem that with LZW it isn't wise to compress files that are already compressed. With Huffman, there's a chance that the new file is actually a little bit smaller, so it might be worth it to give it a shot. But with all of the tested files that already employ some sort of compression, applying LZW compression only makes them a lot bigger.
  
  * It's common knowledge that JPEG files are compressed out of the world, and even Huffman couldn't bring the test file's size down but managed to increase it a bit (due to overhead and other issues). It is the only file type that, with Huffman, resulted in a larger file than the original.
  
  * Huffman doesn't create much overhead, but LZW can in some cases because of the dictionary that it builds. This causes LZW-compressed files to get much bigger than the original, when that original doesn't compress very well.
  
  * So the optimal thing to do with pre-compressed files is to leave them as is rather than try to use LZW. Huffman can bring their sizes down a bit, but we must also consider the fact that compression/decompression takes CPU time, memory and storage space (some for the compressed file, some for the decompressed original). So, perhaps it is optimal to not compress pre-compressed files, even with Huffman.
  
* Any further observations?
  
  * LZW performs very well on the Lorem Ipsum text because it contains a lot of word repetitions (meaning that the same word appears many times within the file). In LZW's dictionary, such longer words would get a shorter codeword to represent them, saving space.
  
  * The same is true with the Java source code file. LZW performs much better than Huffman.
  
  * Huffman compresses text files very well also, because more often than not they don't consist of the entire available character space (all ASCII or extended ASCII characters), but a small subset of those (primarily letters, numbers, dots, question marks and the like).
  
  * Huffman's performance on the files that were already heavily compressed comes as a minor surprise. Because of the way Huffman works, it is interesting to see that it can squeeze even more out of them.
