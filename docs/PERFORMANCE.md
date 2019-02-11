# Performance

Here you can read about the performance benchmarking run on the two algorithms (Huffman, LZW).

Because we are primarily interested in the size of the compressed file, we'll focus on that here. Compression and decompression speeds are of lesser concern in this case.

All of the files that we benchmarked (compression) were also decompressed to verify that the compression actually worked. The decompressed file was then compared with the original to see that they were an exact (100 %) match, and that not a single bit went missing or got corrupted.

Please note! These tests have been run on 2019-02-10. If your results differ using the same data, it's most likely because the algorithms have changed from that date. You can always get a version of this app from the past to confirm these are actual benchmarking results. The test data is not updated after each new version.

#### Results

In the following table we list the original and compressed sizes for the files found in the [data](../data) folder.

The sizes are in bytes. Divide them by 1024 to get kilobytes (kB).

| file | original | Huffman | LZW | winner |
| :- | :- | :- | :- | :- |
| lorem_ipsum.txt | 17638 | 9519 (- 46,03 %) | 7400 (- 58,05 %) | x |
| lorem_ipsum.docx | 20164 | 19418 (- 3,7 %) | 25267 (+ 25,31 %) | x |
| lorem_ipsum.pdf | 97786 | 96221 (- 1,6 %) | 129392 (+ 32,32 %) | x |
| nice_picture.jpeg | 85959 | 86172 (+ 0,25 %) | 120040 (+ 39,65 %) | x |
| just_an_image.png | 45412 | 44994 (- 0,92 %) | 64896 (+ 42,9 %) | x |

Here's an illustrive image. The sizes are in bytes and a lower value is better.

![App](https://github.com/gotonode/compress/blob/master/docs/images/results01.png)

#### Q&A

* Why did the size increase in some of the cases?

  * Some files are already pretty well compressed, such as PDF files and JPEG files. Others, like TXT files, are not.

  * Compressing an already compressed file usually results in a compressed file that is about the same size, or perhaps a little bit bigger or smaller. The new compressed file might be bigger than the original because of the overhead involved. Overhead is data added to the input data for processing purposes.
  
  * It would seem that with LZW it isn't wise to compress files that are already compressed. With Huffman, there's a chance that the new file is actually a little bit smaller, so it might be worth it to give it a shot.
  
  * It's common knowledge that JPEG files are compressed out of the world, and even Huffman couldn't bring the test file's size down but managed to increase it a bit (due to overhead and other issues).
  
* Any further observations?

  * Huffman doesn't create much overhead, but LZW can in some cases because of the dictionary that it builds. This causes LZW-compressed files to get much bigger than the original, when that original doesn't compress very well.
  
  * LZW performs very well on the Lorem Ipsum text because it contains a lot of word repetitions (meaning that the same word appears many times within the file). In LZW's dictionary, such longer words would get a shorter codeword to represent them, saving space.
  
  * Huffman compresses TXT files very well also, because more often than not they don't consist of the entire available character space (all ASCII or extended ASCII characters), but a small subset of those (primarily letters, numbers, dots, question marks and the like).
  
  * Huffman's performance on the files that were already heavily compressed comes as a minor surprise. Because of the way Huffman works, it is interesting to see that it can squeeze even more out of the files.
