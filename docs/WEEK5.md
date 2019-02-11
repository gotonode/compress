# Week 5

#### What I did this week

I worked on my own implementation of the minimum priority queue, the `MinQueue` data structure. A lot of new documentation was also written.

#### Progress

Huffman and LZW are done and tested. I've also implemented all of the required data structures myself (minimum priority queue and ternary search tree).

#### What I learned this week

Too much time went into building the minimum priority queue because I just implemented many features that turned out not to be needed at all. Huffman doesn't remove items from the priority queue, without polling, so a `remove`-function wasn't necessary (for an example).

How useful it is to measure algorithms to see which parts are taking the most time. Then you'll know what to focus on to gain the greatest performance improvements.

I almost didn't measure the part that uses Java's `substring`-method, but I'm glad I did because that was the root cause of my problems. So I learned that I shouldn't assume that something's not the problem when I don't have previous knowledge that that's the case (or could be the case).

#### What is unclear

How to make LZW compression faster. I'll work on that for the final week, but if I'm unable to make it any faster, I'm still happy about the way it came out.

I'll return to this project at a later time during my studies, if there are still issues, and see if I can't fix them then.

Update! I identified the issue. Here's a snapshot of my test results:

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

Now I know which part is taking 86 % of the time (LZW compression). That was the easy part. For the final week, well, I'll see if it can't be made at least somewhat faster.

#### What I'll do next

For the final week, I'll do the second peer review and do it well.

Finishing touches here and there as well as documentation are also on the agenda. I'll also write a few more tests to fill any gaps that might've been left.

I'll also go through the requirements to see if I've missed anything. 

#### Hours used

16 hours so far.
