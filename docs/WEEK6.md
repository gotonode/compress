# Week 6

#### What I did this week

Benchmarking has been completed, both with internal data made by me and with the Calgary corpus. I also spent most of my time trying to improve LZW's performance, but so far have not made any progress. I re-implemented the whole algorithm, but that ended up being even slower than the current one so I scratched the code.

#### Progress

I took the hints & tips from the peer review that I got into consideration and implemented some of them, like the descriptions on the algorithms on the main `README.md`-file.

Project is almost complete, save for LZW compression performance improvements. Documentation and performance testing are done.

#### What I learned this week

How most of the time in a project can go into fixing seemingly small things. At least the big picture is working, but minor adjustments could make it work more optimally. Algorithms which look at a quick glance to be identical can actually have performance implications of several orders of magnitude or more.

When someone truly knows about algorithms, then, I think, that's something to be admired. Algorithms can be easy and super hard at the same time.

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

Finishing touches, testing and documentation. LZW improvements. I'll also mentally prepare for the demo event.

If I'm unable to make LZW any faster, I'll return the project as-is for grading.

#### Hours used

12 hours used so far. A lot of debugging.

Considering past weeks, here are the total used hours:

* Week 1: 11 hours
* Week 2: 23 hours
* Week 3: 22 hours
* Week 4: 25 hours
* Week 5: 19 hours
* Week 6: 12 hours

Total: 122 hours
