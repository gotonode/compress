# Week 1

##### What I did this week

During the first week, I chose the problem(s) I'd like to solve within this course, and introduced myself to the solutions that I'm going to be using.

Originally, I had intended to do a comparison between 10 or more different sorting io.github.gotonode.compress.algorithms. But I already have a little bit of experience with those, and none with compression io.github.gotonode.compress.algorithms. So I switched problems.

I'm now dedicated to completing an app that tests Huffman coding and LZW io.github.gotonode.compress.algorithms on various different files.

What motivated me? I remember when I was about 8-10 years old, I discussed RAR archives with someone at school. And we concluded that it does indeed make games fit into smaller space, such as on a 1.44 MB floppy disk, but that repeated runs of the same RAR algorithm on a file would begin to increase the file size. Back then, we didn't realize that it's because of the overhead that is generated, but it was a fascinating topic, one that I haven't explored until now.

I also chose the IDE. The IDE I'm going to be using is **IntelliJ IDEA Community**, testing will be done with **JUnit 4** (skipping version 5 for now) and code coverage is by **JaCoCo**. Some online tools will be used to assess the quality of my code.

##### Progress

I created the project with my chosen IDE and pushed the committed changes to GitHub. I also made a mock-up test file that will be used as the base for writing more tests for my app. I'll be writing tests as I progress with the app, so that the tests wouldn't lag behind.

##### What I learned this week

A lot about Huffman and LZW, and about binary coding in general.

##### What is unclear

So far so good. I think this section will be a lot more populated come next week.

##### What I'll do next

I'll be creating the skeleton for the app. The io.github.gotonode.compress.algorithms will start taking a little bit of shape, but will not yet be functional the next week. I'll be using built-in data structures (such as ArrayLists), and migrate to my own implementations once the basics are working. This way, I'll know which data structures I need to make myself. There's little point in making an implementation of a HashMap yourself if you're not going to be using it during the confines of this course.

Because this app is pretty useless without data, next week I'll create all the data files defined in `DEFINITION.md`. I'll also add functionality to read the data in.

##### Hours used

Counting the time I used to read all of the articles (such as on Wikipedia and Brilliant.org), I used 11 hours the first week. I made this repository and pushed the changes only at the last moment.
