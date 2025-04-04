import java.util.Scanner;
import net.jpountz.xxhash.XXHashFactory;
import net.jpountz.xxhash.XXHash32;

public class hash_app {
    public static void hashing(int n){
        Scanner sc = new Scanner(System.in);

        // By setting the seed to a certain number, the hash function will return the same result given the same inputs
        int seed = 0;
        XXHashFactory xxHashFactory = XXHashFactory.fastestInstance();
        XXHash32 hasher = xxHashFactory.hash32();

        System.out.print("Please enter the string: ");
        String input = sc.nextLine();
        byte[] bytes = input.getBytes();

        // This is the hashing functions that returns the 32-bit number
        int h1 = hasher.hash(bytes,0,bytes.length,seed);

        /*
        Then, as we need values in the range from 0 to n,
        we will take the remainder from division of
        h1 by n+1 (as, then we will have the max. remained of n)
        */
        int index = Math.floorMod(h1,n+1);

        System.out.println("The string " + input + " is stored in " + index);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        // Number of hash buckets
        int n = sc.nextInt();

        // Bucket size
        int s = sc.nextInt();
        sc.nextLine();

        hashing(n);
    }
}
