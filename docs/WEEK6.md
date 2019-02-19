# Week 6

#### What I did this week

Benchmarking has been completed, both with internal data made by me and with the Calgary corpus.

#### Progress

#### What I learned this week

#### What is unclear

I identified the issue with LZW compression performance.

Here's a snapshot of my test results:

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

Now I know which part is taking 86 % of the time (seeking forward). That was the easy part. For the remainder of the course, well, I'll see if it can't be made at least somewhat faster.

The loop in question can be found [here](https://github.com/gotonode/compress/blob/517a857048fa58e3ab633f0eb4c0ec63c45b3b33/src/main/java/io/github/gotonode/compress/algorithms/lzw/LZW.java#L126). And the part that's causing 86 % of the compression time is [here](https://github.com/gotonode/compress/blob/517a857048fa58e3ab633f0eb4c0ec63c45b3b33/src/main/java/io/github/gotonode/compress/algorithms/lzw/LZW.java#L187). Both links are permalinks to past commits on GitHub, and those commits do not reflect current code.

#### What I'll do next

Finishing touches, testing and documentation. LZW improvements.

#### Hours used

7 hours used so far. A lot of debugging.