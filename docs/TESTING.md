# Testing

Soft target for code coverage: **80 %**

This document goes over the testing of this app.

Source code for tests available on [GitHub](https://github.com/gotonode/compress/tree/master/src/test/java/io/github/gotonode/compress).

A new file, [PERFORMANCE.md](PERFORMANCE.md), has been spawned (for clarity).

#### How to run the tests

Run the following command on your favorite shell/terminal/console/command prompt:

```
gradlew test --console verbose
```

If that doesn't work, try this one (especially on Windows):

```
gradlew.bat test --console verbose
```

If you don't want verbose output, you can remove `--console verbose` from the command.

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

#### What hasn't been tested and why

One might wonder why the code coverage isn't 100 %. I've set a soft target of 80 % for this project, due to the following reasons.

Exceptions are currently not being tested and, as far as I know, they cannot be ignored in the code coverage reports.

Here's an example of an exception (Java):

```java
try {
    this.binaryReadTool = new BinaryReadTool(source);
} catch (IOException ex) {
    UiController.printErrorMessage(ex);
    return;
}
```

Code in the `try`-section is tested, but the code in the `catch`-block is not.