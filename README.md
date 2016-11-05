#hyg_stars_parsecs

##Introduction
I have developed and implemented a program that reads in a file from the HYG (Hipparcos, Yale, Gliese) database and computes the minimum, maximum, and mean shortest distances between stars. Stars whose distance is not accurately known was already coded into the file and not recognized by the program I implemented. The data that was not needed to make the computations for distance was filtered out in the implementation as well. After some analysis, I decided reading over those twenty additional fields per star would be costly in terms of time and space complexity given the size of the csv file. The computational part of the program has been divided into two separate functions called distanceTo and processData. distanceTo takes in two arrays and applies the distance formula on three dimensions in euclidean space and returns that value. processData takes in a two dimensional array and a double holding the number of stars to be processed. Two nested for loops were utilized to make those distance comparisons between every star as the nearest neighbor relation is not symmetric. Also, the algorithm did not include the comparison when the program iterates over the star itself within the inner for loop as this would create bad data of a distance of zero. The program was implemented in the Java programming language.

##Problem Definition
**Input:** A set of n stars containing 25 fields formatted CSV file named “hygxyz.csv”.<br>
**Output:** Number of stars found in known range from Sol and minimum, maximum, and mean shortest distances between stars.

##Extra<br>
I implemented multithreading by extending the Thread class. The main purpose of adding the multithreading on this assignment was to provide simultaneous execution of two or more parts of a program to maximally utilize processor time. My computer has a dual core processor, so I decided to use two threads. To handle the two threads some adjustments were added to the PA2 class that I had already created. An overloaded constructor was added to the class to create a Thread object with its associated bounds. The 2 dimensional array was made a public class variable so run() function could access the data to process from the array. The output was the same, but some precision may have been lost due to the computations being done on a data type double. The min and max stayed the same as previously as no computation is being done repetitively as with the mean. The runtime was reduced by 45 seconds and I believe this to be significant as a large part of the runtime associated with this algorithm is likely to be with printing the x, y, and z components of each star and then printing each starID itself.
