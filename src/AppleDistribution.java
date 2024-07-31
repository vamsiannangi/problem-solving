import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AppleDistribution {

    private static final int RAM_PAYMENT = 50;
    private static final int SHAM_PAYMENT = 30;
    private static final int RAHIM_PAYMENT = 20;
    private static final int TOTAL_PAYMENT = RAM_PAYMENT + SHAM_PAYMENT + RAHIM_PAYMENT;

    private static List<Integer> getAppleWeights() {
        Scanner scanner = new Scanner(System.in);
        List<Integer> weights = new ArrayList<>();
        int weight;

        System.out.println("Enter apple weight in grams (-1 to stop):");
        while ((weight = scanner.nextInt()) != -1) {
            weights.add(weight);
            System.out.println("Enter apple weight in grams (-1 to stop):");
        }
        return weights;
    }

    private static void distributeApples(List<Integer> weights) {
        int totalWeight = weights.stream().mapToInt(Integer::intValue).sum();
        int ramTarget = totalWeight * RAM_PAYMENT / TOTAL_PAYMENT;
        int shamTarget = totalWeight * SHAM_PAYMENT / TOTAL_PAYMENT;
        int rahimTarget = totalWeight * RAHIM_PAYMENT / TOTAL_PAYMENT;

        List<Integer> ramApples = new ArrayList<>();
        List<Integer> shamApples = new ArrayList<>();
        List<Integer> rahimApples = new ArrayList<>();
        for (int weight : weights) {
            if (ramTarget >= weight) {
                ramApples.add(weight);
                ramTarget -= weight;
            } else if (shamTarget >= weight) {
                shamApples.add(weight);
                shamTarget -= weight;
            } else if (rahimTarget >= weight) {
                rahimApples.add(weight);
                rahimTarget -= weight;
            }
        }

        System.out.println("Distribution Result:");
        System.out.println("Ram: " + ramApples);
        System.out.println("Sham: " + shamApples);
        System.out.println("Rahim: " + rahimApples);
    }

    public static void main(String[] args) {
        List<Integer> weights = getAppleWeights();
        distributeApples(weights);
    }
}
