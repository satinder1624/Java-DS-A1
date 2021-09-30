package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

// Statement of Authorship: This is Satinder Singh, 000824885. I build this file on my own.

public class Main {

    // r1,r2.c1.c2 are use to calculate distance between two highest peaks

    static double r1 = 0; // Row 1
    static double r2 = 0; // Row 2

    static double c1 = 0; // Col 1
    static double c2 = 0; // Col 2


    static double d = 0;  // d: distance between two highest peak


    static double[] distance = new double[1000000]; // Storing distance between each peak
    static int countLength = 0;  // Increment when we calculate new distance and use for indexing purpose for upper array


    /**
     * This method print the lowest element from the data set and its occurrence as well..
     * @param arr : Takes array which contains Elevation's file data set
     * @param low : We supposed that data set contain one element so that it is the smallest data from our set so we set to lowValue
     * @param pMax : We supposed that data set contain one element so that it is the Max data from our set so we set to pMax
     * @param cnt : Determine how many times the lowest value occur in the data set
     */
    public static void lowestEvaluation(int[][] arr,int low,int pMax,int cnt){
        System.out.println(".....................................................");
        int lowV = low;
        // Find Smallest number and count its occurrence
        for(int row=0; row<arr.length;row++){
            for(int col=0; col<arr[row].length;col++){
                if(lowV >= arr[row][col]) {
                    lowV = arr[row][col];
                    if(lowV != pMax) {
                        cnt = 1;
                    }else{
                        cnt++;
                    }
                    pMax = lowV;
                    lowV = arr[row][col];
                }

            }
        }

        System.out.println(lowV+" is the lowest value and it occurs "+cnt+" times");
        System.out.println(".....................................................");
        System.out.println();
    }

    /**
     * This method print all the highest peaks we get from our data set in [ row,col : highestValue ]
     * @param array : Takes array which contains Elevation's file data set
     * @param radius : This value decides from where we start and finish from all sides
     * @param matchValue : This determine print that value which is greater than or equal to from the data set
     * @param outsideValue : This store current value that we getting from data set inside loop
     */
    public static void printAllPeaks(int[][] array,int radius,int matchValue,int outsideValue){
        System.out.println(".....................................................");

        boolean firstPeak = true; // When we get our second peak everytime throughout the loop procedure it changes and it determines r1,r2,c1,c2 values
        int totalPeaks = 0; // Total peaks we found fronm data set

        // Exclusive Radius Looping
        for(int row=radius;row< array.length-radius;row++){
            for(int col=radius;col<array[row].length - radius;col++){
                if(matchValue <= array[row][col]) { // If current looping value (array[row][col] value is greater than or equal to match value then this block execute

                    outsideValue = array[row][col]; // Before going further, store current value into temp var
                    boolean checking = true; // This tells the current value is higher peak from its inner radius area
                    int currentHighestPeak = outsideValue; // We supposed that our current value is highest

                    // Inclusive Radius Looping
                    for(int OuterRow = row-radius;OuterRow<=row+radius;OuterRow++){
                        for(int OuterCol = col-radius;OuterCol<=col+radius;OuterCol++){

                            int aroundValue = array[OuterRow][OuterCol]; // Storing current inner looping value

                            if(aroundValue == outsideValue && (row != OuterRow && col != OuterCol)){
                                checking = false;
                            }

                            if(aroundValue > currentHighestPeak){ // If we found another highest peak around inner radius area then we ignore it..
                                checking = false;
                            }
                        }

                    }

                    if(checking){
                        System.out.println("[ "+row+","+col+" : "+array[row][col]+" ]");
                        if(firstPeak){
                            r1 = row;
                            c1 = col;
                            firstPeak = false;
                        }else{
                            r2 = row;
                            c2 = col;
                            firstPeak = true;
                            calculateDistance(); // We calculate distance when the values of r2 and c2 changed
                        }
                        totalPeaks++;
                    }

                }
            }
        }
        System.out.println("Total Peaks are "+totalPeaks);
        System.out.println(".....................................................");
        System.out.println();
    }

    /**
     * This method will calculate the distance between two highest peak and storing into an array
     */
    public static void calculateDistance(){
        d = Math.pow(r1-r2,2) + Math.pow(c1-c2,2);
        d = Math.sqrt(d);
        double result = Math.round(d * 100.0) / 100.0; // Round to 2 decimal value
        distance[countLength] = result;
        countLength++;
    }

    /**
     * This method only print the lowest value from the array, which will be closest distance from two high peaks
     */
    public static void printDistance(){
        distance = Arrays.copyOf(distance,countLength);
        System.out.println(".....................................................");
        Arrays.sort(distance);
        System.out.println("Distance " + distance[0]);
        System.out.println(".....................................................");
    }

    public static void commonElement(int[][] arr){
        System.out.println();
        System.out.println("Loading.....");
        int counter, element = arr[0][0], timesRepeat = 0;
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[i].length; j++) {
                counter = 0;
                for (int innerJ = i; innerJ < arr.length; innerJ++) {
                    for (int k = 0; k < arr[innerJ].length; k++) {
                        if (arr[i][j] == arr[innerJ][k]) {
                            counter++;
                        }
                    }

                    if (counter > timesRepeat) {
                        element = arr[i][j];
                        timesRepeat = counter;
                    }
                }
            }
        }

        System.out.println("Lowest element is: "+element+" and it occurs "+timesRepeat+" times in the data set");
    }

    /**
     * From here, program runs, This method will call other functions one by one which we define above in order to get answers for our four questions...
     * @param args
     * @throws FileNotFoundException
     */
    public static void main(String[] args) throws FileNotFoundException {
        final String filename = "ELEVATIONS.TXT";
        File file = new File(filename);
        Scanner fileInput = new Scanner(file);
        int[] firstLine = new int[4]; // firstLine: Make an array of values which present in the first row of data set file
        int ct = 0; // ct: use as an indexing purpose

        // Reading first row from the data set
        while(fileInput.hasNextInt()){
            firstLine[ct] = fileInput.nextInt();
            ct++;
            if(ct ==4){
                break;
            }
        }

        int rowLength = firstLine[0];
        int colLength = firstLine[1];
        int matchValue = firstLine[2];
        int radius = firstLine[3];
        int outsideValue = 0;

        int [][] myArray = new int[rowLength][colLength];
        // Reading data set form here
        while(fileInput.hasNextInt()) {
            for (int i=0; i<myArray.length; i++) {

                for (int j=0; j<myArray[i].length; j++) {
                    myArray[i][j] = fileInput.nextInt();
                }
            }
        }

        long startTime = System.nanoTime();

        int lowValue = myArray[0][0]; // we currently supposed that the lowest element in the data set is the first element
        int prevMax = myArray[0][0]; // we currently supposed that the highest element in the data set is the first element
        int count = 0; // Count the lowest element occurrence in data set

        /**
         * Answer 1
         * This method print the lowest element from the data set and its occurrence as well..
         */
        lowestEvaluation(myArray,lowValue,prevMax,count);

        /**
         * Answer 2
         * This method print all the highest peaks we get from our data set in [ row,col : highestValue ]
         */
        printAllPeaks(myArray,radius,matchValue,outsideValue);

        /**
         * Answer 3
         * Print distance between two closest peaks
         */
        printDistance();

        /**
         * Answer 4
         * Printing common element and its occurrence...
         */
        commonElement(myArray);

        long endTime = System.nanoTime();

        long duration = (endTime - startTime) /1000000;

        System.out.println("It takes "+duration+" milliseconds");
    }
}
