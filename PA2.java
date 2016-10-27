/* Principles of Programming: Java
 * PA2 
 * Tish Konz
 */
package pa2;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import static java.lang.Math.sqrt;

/**
 *
 * @author Tish
 */
class PA2 extends Thread
{
    // public class variables to handle threading
    public int start;
    public int end;
    public static int index=0;
    
    // public static arrays to hold each thread's data
    public static double [] mins = new double[2];
    public static double [] maxs = new double[2];
    public static double [] means = new double[2];
    
    // public static 2D array here so that all other classes and methods can get to it
    // this 2D array holds all star info
    public static double [][] starData = new double [115373][3];

     // overloaded constructor
     PA2(int start, int end)
    {
        this.start = start;
        this.end   = end;
    }
    
    public static void getData()throws FileNotFoundException, IOException
    {
        
        BufferedReader csvFile = new BufferedReader(new FileReader("/Users/Tish/Documents/CSCI3415/HYG-Database-master/hygxyz.csv"));
        //double [][] starData = new double [119617][3];
        String l1 = csvFile.readLine(); // First line is not needed.
        int starID = 0; // star number; essentially row number
        int count = 0;
        String row;
        double radius =  10000000;
        
        
        while ((row = csvFile.readLine())!=null)
        {
            String[] dataArray = row.split(",");
//            for (String item:dataArray) 
//            { 
//                System.out.print(item + "|"); 
//            }
                if (Double.parseDouble(dataArray[9]) < radius)
                {
                    for (int i = 17; i < 20; ++i)
                    {
                        // input the 18th column in the line into our
                        // star data array at row[starID][i-17= 0,1,2]
                        starData[starID][i-17] = Double.parseDouble(dataArray[i]);
                    }
                    ++starID; // increment row number
                }
               
            System.out.println(); // Print newline.
        }
    // Close the file once all data has been read.
    csvFile.close();
    for (int i = 0; i < starID; ++i)
    {
        for (int j = 0; j < 3; ++j)
        {
            System.out.print(starData[i][j] + "|"); 
        }
        System.out.println();
    }
    System.out.println(starID + " Found within a radius of " + radius + " parsecs from Sol.");

    // processData(starData, starID);
    
    } 
    
    public void run() // called with .start() in main()
    {
        
        double mean;
        double sum = 0;
        double distanceJ;
        double starMinJ = 1000000;
        double absStarMin = 1000000; 
        double absStarMax = 0;  
        
        //outer loop finds the nearest star for every star
        for (int i = start; i < end; ++i)
        {   starMinJ = 10000000;
            System.out.println("star " + ++i);
            //inner loop checks each star in the set to see if it is the nearest
            for (int j = 0; j < starData.length; ++j)
            {
                if (i != j)
                {
                    // compute distance
                    distanceJ = distanceTo(starData[i], starData[j]);
                 //   System.out.println("Distance is: " + distanceJ);
                    
                    // if calculated distance is less than current min, 
                    if (distanceJ < starMinJ)
                    {
                        // then make new this new min
                        starMinJ = distanceJ;
                    }
                }
            }
            // check for absolute min distance
            if (starMinJ < absStarMin)
            {
                absStarMin = starMinJ;
               // System.out.println("STARMIN is now = " + starMinJ);
            }
            
            if(starMinJ > absStarMax) 
            {
                absStarMax = starMinJ;
            }
            sum+=starMinJ;
           
                        
        } // outer for loop
        sum/=(end - start);
        printData(absStarMin, absStarMax, sum);    
    
    }
    
    public synchronized void printData(double min, double max, double mean)
    {
        means[index] = mean;
        mins[index] = min;
        maxs[index] = max;
        ++index;
        
        
        if(index == 2)
        {
            double average = (means[0] + means[1]) / 2;
            System.out.println("The mean is: " + average);
            
            if (mins[1] < mins[0])
            {
                System.out.println("The min distance is " + mins[1]);
            }
            else
            {
                System.out.println("The min distance is " + mins[0]);
            }
            
            if (maxs[1] > maxs[0])
            {
                System.out.println("The max distance is " + maxs[1]);
            }
            else
            {
                System.out.println("The max distance is " + maxs[0]);
            }
        }
        
        
    }
    
    public static double distanceTo(final double row1[], final double row2[])
    {
        double sum = 0;
        // loop through each paramenter and take difference
        // square the difference and add them together
        for (int i = 0; i < 3; ++i)
        {
            double difference;
            difference = row2[i] - row1[i];
            sum += (difference * difference);
        }
        // return the square root of the sum
        return sqrt(sum);
    }
    
    public static void main (String[] args) throws Exception
    {
        getData(); // have stars
        
        int a =0;
        int b = 55000;
        int a2 = 55001;
        int b2 = starData.length; // should be 115373
     

        Thread thread1 = new PA2(a,b);
        Thread thread2 = new PA2(a2,b2);
        thread1.start();
        thread2.start();
          
    }   
}