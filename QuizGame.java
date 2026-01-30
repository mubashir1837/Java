import java.util.Scanner;  // Import Scanner class for user input

public class QuizGame {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Initialize score variable
        int score = 0;

        // Questions array
        String[] questions = {
            "1. What is the capital of Pakistan?\nA. Karachi\nB. Lahore\nC. Islamabad\nD. Peshawar",
            "2. Which language is primarily used for Android development?\nA. Python\nB. Java\nC. C++\nD. Ruby",
            "3. What is 5 + 7?\nA. 10\nB. 11\nC. 12\nD. 13",
            "4. Who wrote 'Romeo and Juliet'?\nA. Charles Dickens\nB. William Shakespeare\nC. J.K. Rowling\nD. Mark Twain",
            "5. What is the boiling point of water at sea level?\nA. 50°C\nB. 75°C\nC. 100°C\nD. 150°C"
        };

        // Correct answers array
        char[] answers = {'C', 'B', 'C', 'B', 'C'};

        // Loop through each question
        for (int i = 0; i < questions.length; i++) {
            System.out.println(questions[i]);
            System.out.print("Enter your answer (A, B, C, or D): ");
            
            
            String input = scanner.nextLine().toUpperCase(); // Convert input to uppercase
            char userAnswer;

            // Input validation
            if (input.length() == 1 && (input.charAt(0) >= 'A' && input.charAt(0) <= 'D')) {
                userAnswer = input.charAt(0);
            } else {
                System.out.println("Invalid input. Defaulting to 'X'.");
                userAnswer = 'X'; // Invalid choice
            }

            // Check answer using if statement
            if (userAnswer == answers[i]) {
                System.out.println("Correct!\n");
                score++;
            } else {
                System.out.println("Incorrect! The correct answer was " + answers[i] + ".\n");
            }
        }

        // Compute final score as percentage
        double percentage = ((double) score / questions.length) * 100;

        // Display final score using switch-case for grade category
        System.out.println("You answered " + score + " out of " + questions.length + " correctly.");
        System.out.println("Your final score: " + percentage + "%");

        switch ((int) percentage / 10) {
            case 10:
            case 9:
                System.out.println("Grade: A");
                break;
            case 8:
                System.out.println("Grade: B");
                break;
            case 7:
                System.out.println("Grade: C");
                break;
            case 6:
                System.out.println("Grade: D");
                break;
            default:
                System.out.println("Grade: F");
        }

        scanner.close(); // Close scanner
    }
}
