import java.util.Scanner;

public class LoopAndConditionDemo {
    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            // For loop : print odd numbers 1-10
            System.out.print("Odd numbers between 1 and 10: ");
            for (int i = 1; i <= 10; i++) {
                if (i % 2 == 0) continue;
                System.out.print(i + " ");
            }
            System.out.println();

            // Do-while loop: user input between 1 and 10
            int n;
            do {
                System.out.print("Enter a number between 1 and 10: ");
                n = sc.nextInt();
            } while (n < 1 || n > 10);
            System.out.println(n + " is between 1 and 10");

            // If-else vs switch
            if (n % 2 == 0) {
                System.out.println(n + " is even (if-else check)");
            } else {
                System.out.println(n + " is odd (if-else check)");
            }

            switch (n) {
                case 1,3,5,7,9:
                    System.out.println(n + " is odd (switch check)");
                    break;
                default:
                    System.out.println(n + " is even (switch check)");
            }
        }
    }
}








