Task 2: Understanding MD5’s Property
In this task, we will try to understand some of the properties of the MD5 algorithm. These properties are
important for us to conduct further tasks in this lab. MD5 is a quite complicated algorithm, but from very
high level, it is not so complicated. As Figure 2 shows, MD5 divides the input data into blocks of 64 bytes,
and then computes the hash iteratively on these blocks. The core of the MD5 algorithm is a compression
function, which takes two inputs, a 64-byte data block and the outcome of the previous iteration. The
compression function produces a 128-bit IHV , which stands for “Intermediate Hash Value”; this output
is then fed into the next iteration. If the current iteration is the last one, the IHV will be the final hash
value. The IHV input for the first iteration ( IHV0 ) is a fixed value.
Based on how MD5 works, we can derive the following property of the MD5 algorithm:
• Given two inputs M and N , if MD5(M) = MD5(N) , i.e., the MD5 hashes of M and N are the same,
then for any input T , MD5(M || T) = MD5(N || T) , where || represents concatenation.
• That is, if inputs M and N have the same hash, adding the same suffix T to them will result in two
outputs that have the same hash value.

This property holds not only for the MD5 hash algorithm, but also for many other hash algorithms.
Your job in this task is to design an experiment to demonstrates that this property holds for MD5 .
