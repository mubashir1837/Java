import java.util.ArrayList;

public class StockAnalysis {

    // Method to calculate average price (Array)
    public static float calculateAveragePrice(float[] prices) {
        float sum = 0;
        for (int i = 0; i < prices.length; i++) {
            sum += prices[i];
        }
        return sum / prices.length;
    }

    // Method to calculate average price (ArrayList)
    public static float calculateAveragePrice(ArrayList<Float> prices) {
        float sum = 0;
        for (int i = 0; i < prices.size(); i++) {
            sum += prices.get(i);
        }
        return sum / prices.size();
    }




    // Method to find maximum price (Array)
    public static float findMaximumPrice(float[] prices) {
        float max = prices[0];
        for (int i = 1; i < prices.length; i++) {
            if (prices[i] > max) {
                max = prices[i];
            }
        }
        return max;
    }



    // Method to find maximum price (ArrayList)




    public static float findMaximumPrice(ArrayList<Float> prices) {
        float max = prices.get(0);
        for (int i = 1; i < prices.size(); i++) {
            if (prices.get(i) > max) {
                max = prices.get(i);
            }
        }
        return max;
    }






    // Method to count occurrences (Array)
    
    public static int countOccurrences(float[] prices, float targetPrice) {
        int count = 0;
        for (int i = 0; i < prices.length; i++) {
            if (prices[i] == targetPrice) {
                count++;
            }
        }
        return count;
    }




    // Method to compute cumulative sum (ArrayList)



    public static ArrayList<Float> computeCumulativeSum(ArrayList<Float> prices) {
        ArrayList<Float> cumulativeList = new ArrayList<>();
        float sum = 0;

        for (int i = 0; i < prices.size(); i++) {
            sum += prices.get(i);
            cumulativeList.add(sum);
        }

        return cumulativeList;
    }



    // Main method
    public static void main(String[] args) {

        float[] stockArray = {120.5f, 121.0f, 119.8f, 122.3f, 120.5f,
                              123.0f, 124.5f, 122.0f, 120.5f, 125.0f};

        ArrayList<Float> stockList = new ArrayList<>();
        for (float price : stockArray) {
            stockList.add(price);
        }

        float averageArray = calculateAveragePrice(stockArray);
        float averageList = calculateAveragePrice(stockList);

        float maxArray = findMaximumPrice(stockArray);
        float maxList = findMaximumPrice(stockList);

        int occurrence = countOccurrences(stockArray, 120.5f);

        ArrayList<Float> cumulativeSum = computeCumulativeSum(stockList);

        System.out.println("Average Price (Array): " + averageArray);
        System.out.println("Average Price (ArrayList): " + averageList);

        System.out.println("Maximum Price (Array): " + maxArray);
        System.out.println("Maximum Price (ArrayList): " + maxList);

        System.out.println("Occurrences of 120.5: " + occurrence);

        System.out.println("Cumulative Sum (ArrayList): " + cumulativeSum);
    }
}

