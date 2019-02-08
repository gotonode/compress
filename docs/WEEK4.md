# Week 4

#### What I did this week

LZW is now complete. Benchmarking is now available.

Made the Huffman coding much, much faster. Previously I was reading the data into an array of characters, but now I'm processing the data directly.

I couldn't compress even a file of 1 MB, but now bigger files are no problem. Being able to make something this much faster just means that my original implementation wasn't that good to begin with.

A lot of cleanup was also done. I had included `equals` and `hashCode` -methods for the `HuffmanNode` but it turns out those were not needed, and what's more they drove my test coverage down.

#### Progress

Like mentioned above, LZW and benchmarking are now complete.

Well, I still need to hone LZW to increase its performance, but that's only in relation to compression. Decompression is already pretty fast, much faster than Huffman in some cases.

#### What I learned this week

LZW implementation and the dictionaries it uses. That took quite a bit of time.

I got stuck trying to figure out why my Huffman algorithm was broken and returned a compressed file with the same size (shown by Explorer as 0,99 MB) as the original. The reason was that I was using a JPEG file, which already is compressed well and wouldn't get much (any) smaller.

That I shouldn't implement features that might be needed unless they are actually needed, like mentioned above with the `HuffmanNode`-class.

#### What is unclear

I split the testing documentation into two, one for testing and one for performance. I hope this is okay, and that when considering the length of the testing document, the combined length of both of those files is taken into account. So if the testing document is 1 A4, and performance is 1 A4, together they fulfill the requirement (2 A4).

If the previous is not okay, I'll combine them together again.

My code coverage is about 80 % due to exceptions not being tested, and binary reading/writing throws a lot of exceptions.

Is 80 % acceptable, or should I find a way to suppress exceptions to increase code coverage?

#### What I'll do next

Fully and carefully evaluate my peer review project. I intend on being exhaustive because that's also a learning experience.

I already created a class, `MinQueue`, to house my minimum priority queue. Currently it only encapsulates Java's own `PriorityQueue`, since now it's easy to just implement all the functions that are required from it and run tests to see if they still work and if it had any impact on the performance.

#### Hours used

25 hours went into the things mentioned above. It also includes documentation that I had written. And debugging.