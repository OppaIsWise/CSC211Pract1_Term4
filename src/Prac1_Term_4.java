import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

public class Prac1_Term_4 {
    private class School {
        //intiialze Strings for object
        String Name;
        String lat;
        String lng;
        String Rate;

        public School(String _Name, String _lat, String _lng, String _Rate) {
            // set values for Strings
            Name = _Name;
            lat = _lat;
            lng = _lng;
            Rate = _Rate;

        }

        @Override  // I override the toString method so that I can return my desired output in the toString method
        public String toString() {
            return "School [Name=" + Name + " , Lat=" + lat + " , Lng=" + lng + " , Rate=" + Rate + "]";
        }


    }

    private ArrayList<School> Schools = new ArrayList<>(); // initalize ArrayList that will take in objects School

    public int Size() {  // This will return intger size of the arrayList
        int count = -1;
        for (School school : Schools) {
            count++;
        }
        return count;
    }

    public ArrayList<School> read_file() { // method that reads in the csv file and populate ArrayList
        BufferedReader buff = null;   // set the Reader to null at initialization

        try {
            buff = new BufferedReader(new FileReader("Soweto.csv")); // reads in CSV File

            String line;
            while ((line = buff.readLine()) != null) { // reads in line by line if its not empty
                String[] input = line.split(";"); // splits the lines that had ";" in it
                var inputSchool = new School(input[0], input[1], input[2], input[3]); // adds object into a variable
                Schools.add(inputSchool);  // add the variable into the ArrayList
            }

        } catch (FileNotFoundException e) { // if the Soweto file is not found it will throw an exception and return File not found
            System.out.println("The Soweto File is not found");
        } catch (IOException e) { // throws an exception if there is a failure with inpur/outpur during run time
            e.printStackTrace();
        }
        return Schools;
    }
    public void MergeSort(ArrayList arrayList, int n){
        int current_Size;  // for the current size of the ArrayList

        int left_Start;   // picking the starting idex of left submerge to be marged

        for(current_Size =1; current_Size <= n-1; current_Size =2*current_Size){
            // Merge subarrays in the Bottom up mathod
            // merge subarray of size 1 to creat sorted
            // subarray of size 2 , then create sorted subarray of size 4

            for(left_Start = 0; left_Start < n-1; left_Start += 2*current_Size){    // pick starting point of different subarrays

                int mid = Math.min(left_Start + current_Size -1 , n-1);   //find end point of left subarray
                int Right_End = Math.min(left_Start + 2*current_Size -1 , n-1);

                merge(arrayList, left_Start,mid,Right_End); // merge subarrays
            }
        }
    }

    static void merge(ArrayList arrayList , int Start , int mid , int end){
        int n = end - Start +1;                                 //size of the range to be merged
        ArrayList<School> arrayList1 = new ArrayList<>(Collections.nCopies(132,null)); // temporay array to merge both halves

        int i1 = Start;  // next pointer to be first
        int i2 = mid +1; // next pointer to be second
        int j = 0; // next opne position


        while(i1 <= mid && i2 <= end) {
            School one = (School) arrayList.get(i1); // fetching element of ArrayList at first pointer
            School two = (School) arrayList.get(i2); // fetching elemdnt of ArrayList at second pointer
            if (one.Name.compareTo(two.Name) < 0) {  // Compare the Elements Name of the ArrayList
                arrayList1.set(j, (School) arrayList.get(i1)); // add first pointer element into temp ArrayList
                i1++;
            } else {
                arrayList1.set(j, (School) arrayList.get(i2)); // add second pointer element into temp ArrayList

                i2++;
            }
            j++;
        }

            while(i1 <= mid){           // copy remainings of the first half
                arrayList1.set(j, (School) arrayList.get(i1));
                i1++;
                j++;
            }

            while(i2 <= end){   // copy remainings of the second half
                arrayList1.set(j, (School) arrayList.get(i2));
                i2++;
                j++;
            }

            for(j=0;j<n;j++){ // add back from temp ArrayList
                arrayList.set(Start + j, arrayList1.get(j));
            }

        }
    static int partition(ArrayList arrayList, int L , int H){

        School pivot = (School) arrayList.get(H);
        int i = (L -1 );   // index of smaller elements

        for(int j = L; j<= H -1; j++) {

            School one = (School) arrayList.get(j); // fetching element of ArrayList
            if (one.Name.compareTo(pivot.Name) <= 0) { // comparing First/Lowest Element with Highest
                i++;

                School temp = (School) arrayList.get(i);  // creating Temp Obj to store in an obj
                arrayList.set(i, arrayList.get(j)); // set new obj into new placing
                arrayList.set(j, temp); // place higher obj into temp
            }
        }

        School temp = (School) arrayList.get(i+1);
        arrayList.set(i+1,arrayList.get(H));
        arrayList.set(H,temp);

        return i+1;




        }

        public void Quick(ArrayList arrayList, int L , int H){

        if(L < H){   // if L is smaller thean than H , it will partition the elemens
            int pi = partition(arrayList,L,H);

            Quick(arrayList,L,pi -1);
            Quick(arrayList,pi+1,H);
        }

        }










    public static void main(String[] args) {

        Prac1_Term_4 S = new Prac1_Term_4();
        S.read_file();

        try {
            PrintWriter Write = new PrintWriter("Schools.txt"); // print writer is used to create and write if no files
            // with the same name is there ,if there is , it overwrite the file
            Write.println("The Schools extracted from The CSV File");
            for (int i = 0; i < S.Schools.size(); i++) {  // for loop to write to file
                Write.println(S.Schools.get(i));
            }
            S.MergeSort(S.Schools,S.Schools.size());

            Write.println("-------------------------------------------------------------------------------------------");
            Write.println("-------------------------------------------------------------------------------------------");
            long START_TIME = System.nanoTime(); // time at the start of operation in Nano Seconds
            S.MergeSort(S.Schools,S.Schools.size());
            long END_TIME = System.nanoTime(); // time at the end in Nano

            Write.println("The running time in milliseconds for the MERGE SORT is : " + (END_TIME-START_TIME)/1000000); // divide it by a mil and you get milliseconds
            Write.println("The Schools extracted from The CSV File: MERGE SORT");
            for (int i = 0; i < S.Schools.size(); i++) {
                Write.println(S.Schools.get(i));
            }
            Write.println("-------------------------------------------------------------------------------------------");
            Write.println("-------------------------------------------------------------------------------------------");
            int n = 132;
            long START_TIMEE = System.nanoTime();
            S.Quick(S.Schools,0,n-1);
            long END_TIMEE = System.nanoTime();
            Write.println("The running time in milliseconds for the QUICK SORT is : " + (END_TIMEE-START_TIMEE)/1000000);
            Write.println("The Schools extracted from The CSV File: QUICK SORT");
            for (int i = 0; i < S.Schools.size(); i++) {
                Write.println(S.Schools.get(i));
            }


            Write.close();
        } catch (IOException e) {   // use a try catch method to catch input / output exception during run time
            e.printStackTrace();
        }

    }
}



