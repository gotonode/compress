# Performance

I chose to split this document from [TESTING.md](TESTING.md) for clarity reasons.

Here you can read about the performance benchmarking run on the two algorithms (Huffman, LZW).

Because we are primarily interested in the size of the compressed file, we'll focus on that here. Compression and decompression speeds are of lesser concern in this case.

#### Results

In the following table we list the original and compressed sizes for the files found in the [data](../data) folder.

The sizes are in bytes. Divide them by 1024 to get kilobytes (kB).

| | original | Huffman | LZW |
| :-: | :-: | :-: | :-: |
| lorem_ipsum.txt | 17638 | 9519 (- 46,03 %) | 7400 (- 58,05 %) |
| lorem_ipsum.docx | 20164 | 19418 (- 3,7 %) | 25267 (+ 25,31 %) |
| lorem_ipsum.pdf | 97786 | 96221 (- 1,6 %) | 129392 (+ 32,32 %) |
| nice_picture.jpeg | 85959 | 86172 (+ 0,25 %) | 120040 (+ 39,65 %) |
| just_an_image.png | 45412 | 44994 (- 0,92 %) | 64896 (+ 42,9 %) |

![App](https://github.com/gotonode/compress/blob/master/docs/images/results01.png)

#### Q&A

* Why did the size increase in some of the cases?

  * Some files are already pretty well compressed, such as PDF files and JPEG files. Others, like TXT files, are not.

  * Compressing an already compressed file usually results in a compressed file that is about the same size, or perhaps a little bit bigger or smaller. The new compressed file might be bigger than the original because of the overhead involved. Overhead is data added to the input data for processing purposes.
  
* Any further observations?

  * Just as one would expect, only the TXT file compresses well.
  
  * Huffman doesn't create much overhead, but LZW can in some cases because of the dictionary that it builds. This causes LZW-compressed files to get much bigger than the original, when that original doesn't compress very well.