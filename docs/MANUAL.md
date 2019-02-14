# Manual

How to use this app? Read the manual to know more.

Notice! If you're running on Windows, you can try substituting `gradlew` with `gradlew.bat`. That might solve any potential issues.

#### Disclaimer

This app never writes to the input file, but just in case, make backups of the important files you choose to work with. Or, alternatively, only use non-important files such as blurry pictures of your cat.

#### How to get the app

You can always get the latest version of this app from [GitHub](https://github.com/gotonode/compress).

If you'd like a pre-built version, go [here](https://github.com/gotonode/compress/releases) to download a `JAR`-file.

To clone this project (must have [Git](https://git-scm.com/) installed), issue this command:

```
git clone https://github.com/gotonode/compress.git
```

This will download the latest version (master branch) to a folder named `compress`. 

It will also download the `.git` folder which includes the complete (commit) history of this project. Over time, it will only get bigger in size. If you would like to avoid this, you can download the `ZIP`-file from GitHub. See the top of this document for the link.

#### How to run the app (Gradle or JAR-file)

You need to have Java installed, preferably an up-to-date version.

Issue this command in the folder you have downloaded/cloned this project:

```
gradlew run
```

If you downloaded the `JAR`-file, use this command instead:

```
java -jar compress.jar
```

Where `compress.jar` is the name of the downloaded `JAR`-file. It might differ.

#### How to build the app (source code)

If you have downloaded/cloned this project, and wish to build it, issue this command:

```
gradlew build
```

#### How to use the app

Once you've downloaded/cloned this project, and successfully started it, you'll be greeted with a view similar to this:

```text
Welcome to Compress (version 4)!
You can find the latest version here: https://github.com/gotonode/compress/

Please choose a command from the following:
  H: Compress a file using Huffman
  L: Compress a file using LZW
  D: Decompress a previously compressed file
  B: Benchmark Huffman against LZW
  X: Print these instructions again
  E: Exit from the program

Command:
```

Here you can give a command from the list. Next, we'll go over these commands.

##### Command 'H', compress with Huffman

With this, you can compress a single file using Huffman coding. You'll need to enter that file's full path or name (current directory).

After that, you'll specify the name of the compressed file. Please do not use the same name for the input and output files, as that will not work.

Once the compression is done, the app tells you the size of the compressed file as well as how much time the compression process took.

If your compressed file turned out bigger than your original file, that means the overhead (additional data used for management) along with the source compression data have a combined byte size that surpasses the original. It most often happens with already-compressed files such as JPEG-files. The optimum strategy with such files is not to use compression (at least my Huffman and LZW implementations) at all.

##### Command 'L', compress with LZW

Exactly as above with Huffman compression, except using LZW.

Please refer to that section.

##### Command 'D', decompress a compressed file

Once you have a file that is compressed with either Huffman or LZW, you can use this command to decompress that file.

You'll need to specify the input file as well as the target file (where the file will be decompressed to). Please do not specify these as the same file. That will not work.

After the decompression operation, the app will inform you about the time it took and how much was the size difference.

Notice! Detection measures are in place to figure out if your input file is indeed a Huffman or LZW compressed file. But if these measures somehow fail, and the app starts to decompress a file that isn't properly compressed, the result could be a very big file or an out of memory / disk space error. In that case, simple remove the decompressed garbage file.

##### Command 'B', benchmark Huffman against LZW

You can use this functionality to benchmark Huffman against LZW.

First, specify the input file that is used in the benchmarking process. No new files will be left as residue, as those are automatically removed.

After the benchmarking, the app prints the results for your convenience.

Here's a sample output from one benchmarking run on the `README.md` file (yours might look different, depending on app version):

```text
We'll benchmark Huffman against LZW using your chosen file. No new files will be left as residue.
Consider choosing a file that is not already compressed, as compressed files do not compress very well.
Name of your source file (must already exist): README.md

  Compression time results:
    Huffman took 7 ms to compress
    LZW took 59 ms to compress
    Regarding compression time, the winner is Huffman!

  Decompression time results:
    Huffman took 10 ms to decompress
    LZW took 4 ms to decompress
    Regarding decompression time, the winner is LZW!

  Compression size results (the original file was 3735 bytes):
    Huffman compressed it to 2572 bytes (a 31,14 % reduction)
    LZW compressed it to 2718 bytes (a 27,23 % reduction)
    Regarding file size, the winner is Huffman!
```

##### Command 'X', print instructions

In case you forget about the commands, you can ask for them again.

Simple as that.

##### Command 'E', exit

Once you're done with the app, use this command to exit.

Alternatively, the `[CTRL] + [Z]` shortcut will work.

#### Input and output

This app accepts any binary file as input. You can compress (and decompress), amongst other things, images, text documents, videos, music and even archives.

As output, you'll specify the name of the file you want to contain the compressed/decompressed data.

Notice! If your chosen output file exists, it will be overwritten without confirmation. The app will inform you about this.
