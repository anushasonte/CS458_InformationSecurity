Task 3: Generating Two Executable Files with the Same MD5 Hash
In this task, you are given the following C program . Your job is to create two different versions of this
program, such that the contents of their xyz arrays are different, but the hash values of the executables
are the same.
#include <stdio.h>
unsigned char xyz[200] = {
/* The actual contents of this array are up to you */
};
int main()
{
int i;
for (i=0; i<200; i++){
printf("%x", xyz[i]);
}
printf("\n");
}
You may choose to work at the source code level, i.e., generating two versions of the above C program ,
such that after compilation, their corresponding executable files have the same MD5 hash value . However,
it may be easier to directly work on the binary level. You can put some random values in the xyz array,
compile the above code to binary. Then you can use a hex editor tool to modify the content of the xyz
array directly in the binary file.
Finding where the contents of the array are stored in the binary is not easy. However, if we fill the array with
some fixed values, we can easily find them in the binary. For example, the following code fills the array with
0x41 , which is the ASCII value for letter A . It will not be difficult to locate 200 A’s in the binary.
Guidelines. From inside the array, we can find two locations, from where we can divide the executable file
into three parts: a prefix , a 128-byte region, and a suffix . The length of the prefix needs to be
multiple of 64 bytes. See Figure 3 for an illustration of how the file is divided.
Figure 3: Break the executable file into three pieces.
We can run md5collgen on the prefix to generate two outputs that have the same MD5 hash value .
Let us use P and Q to represent the second part (each having 128 bytes) of these outputs (i.e., the part
after the prefix ). Therefore, we have the following:
MD5 (prefix || P) = MD5 (prefix || Q)
Based on the property of MD5 , we know that if we append the same suffix to the above two outputs, the
resultant data will also have the same hash value. Basically, the following is true for any suffix :
MD5 (prefix || P || suffix) = MD5 (prefix || Q || suffix)
Therefore, we just need to use P and Q to replace 128 bytes of the array (between the two dividing
points), and we will be able to create two binary programs that have the same hash value. 
