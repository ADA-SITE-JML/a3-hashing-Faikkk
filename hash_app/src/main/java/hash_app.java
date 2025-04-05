import java.io.File;
import java.util.Scanner;

import net.jpountz.xxhash.XXHashFactory;
import net.jpountz.xxhash.XXHash32;

public class hash_app {
    
    // We need to have a single variable that will be accessed from all threads,
    // so we will declare it as static volatile
    public static volatile boolean flag = true;

    public static void storeString(int index, String input) {

        try {
            String filename = String.format("buckets/%d.txt", index);
            File bucketFile = new File(filename);

            if (bucketFile.createNewFile()){
                System.out.println("The string " + input + " is stored in " + index + ".txt");
            }

        } catch (Exception e) {
            System.out.println("Error with file operations has occured.");
            e.printStackTrace();
        }

    }

    public static void hashing(int n) {
        Scanner sc = new Scanner(System.in);

        // By setting the seed to a certain number, the hash function will return the
        // same result given the same inputs
        int seed = 0;
        XXHashFactory xxHashFactory = XXHashFactory.fastestInstance();
        XXHash32 hasher = xxHashFactory.hash32();

        System.out.print("Please enter the string: ");
        String input = sc.nextLine();
        byte[] bytes = input.getBytes();

        // This is the hashing function that returns the 32-bit number
        int h1 = hasher.hash(bytes, 0, bytes.length, seed);

        /*
         * As we need values in the range from 1 to n,
         * we will take the remainder from division of
         * h1 by n (getting the values from 0 to n-1)
         * and simply add 1
         */
        int index = Math.floorMod(h1, n) + 1;
        storeString(index,input);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Number of hash buckets
        int n = sc.nextInt();

        // Bucket size
        int s = sc.nextInt();
        sc.nextLine();

        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                System.out.println("Ctrl + C has been pressed");
                flag = false;
            }
        });

        while (flag) {
            hashing(n);
        }

    }
}
