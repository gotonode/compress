# Performance

I chose to split this document from [TESTING.md](TESTING.md) for clarity reasons.

Here you can read about the performance benchmarking run on the two algorithms (Huffman, LZW).

Because we are primarily interested in the size of the compressed file, we'll focus on that here. Compression and decompression speeds are of lesser concern in this case.

#### Results

In the following table we list the original and compressed sizes.

The sizes are in bytes. Divide them by 1024 to get kilobytes (kB) values.

| | original | Huffman | LZW |
| :-: | :-: | :-: | :-: |
| lorem_ipsum.txt | 94895 | 50980 (- 46,28 %) | 34212 (- 63,95 %) |
| lorem_ipsum.docx | 42212 | 41800 (- 0,98 %) | 65230 (+ 54,53 %) |
| lorem_ipsum.pdf | 204449 | 198063 (- 3,12 %) | 241690 (+ 18,22 %) |
| just_an_image.png | 45412 | 44994 (- 0,92 %) | 64896 (+ 42,9 %) |