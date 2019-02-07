# Testing

This document goes over the testing of this app.

To browse the source code for the tests, follow [this](https://github.com/gotonode/compress/tree/master/src/test/java/io/github/gotonode/compress) link.

#### What has been tested and how

`JUnit` was chosen as the testing framework. It had just got a new release, version 5, but its use was purposefully deferred within this project. So version 4.12 was used instead.

This app has somewhat extensive unit tests. Integration tests were not part of the requirements, but naturally some have been made as well.

Huffman coding and LZW both rely on `BinaryReadTool` and `BinaryWriteTool` -tools. Testing those in an integration test also tests these binary tools.

Even enums are tested in case someone accidentally changes or breaks them. They were also driving the code coverage percentage down.

Both Huffman and LZW have tests for the following:
* Compress a deterministic binary file
* Decompress a previously compressed file
* Benchmark both algorithms against each other

#### Structure

A class called `_Generic` is used by multiple different tests. It provides functionality to create deterministic TXT and binary files, return random integer values as well as the ability to compare two files together (to see if they are identical).

Huffman and LZW both use tree structures, which have been defined in their own files (`HuffmanNode`, `LZWNode` and `LZWTree`). Unit tests have been created targeting just those parts.

#### Code coverage

You can find the code coverage reports on [Codecov](https://codecov.io/gh/gotonode/compress).

The Main-class as well as the UI components have been ignored in code coverage.

The functionality provided by the app is tested directly without using any UI components.

#### Performance

Here you'll find performance testing results for both of the algorithms.

Soon.