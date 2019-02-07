# Week 4

#### What I did this week

Made the Huffman coding much, much faster. Previously I was reading the data into an array of characters, but now I'm processing the data directly.

I couldn't compress even a file of 1 MB, but now bigger files are no problem. Being able to make something this much faster just means that my original implementation wasn't that good to begin with.

A lot of cleanup was also done. I had included `equals` and `hashCode` -methods for the `HuffmanNode` but it turns out those were not needed, and what's more they drove my test coverage down.

#### Progress

#### What I learned this week

I got stuck trying to figure out why my Huffman algorithm was broken and returned a compressed file with the same size (shown by Explorer as 0,99 MB) as the original. The reason was that I was using a JPEG file, which already is compressed well and wouldn't get much (any) smaller.

That I shouldn't implement features that might be needed unless they are actually needed, like mentioned above with the `HuffmanNode`-class.

#### What is unclear

When trying to decompress a file that hasn't been compressed, my app continously keeps writing to the output file. How exactly to detect if a file is compressed or not? Or is this just an issue that the user has to be careful about.

I added a notice when trying to decompress a file of how many kilobytes are going to be written to disk.

#### What I'll do next

#### Hours used