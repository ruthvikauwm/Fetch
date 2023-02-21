import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.Instant;
import java.util.*;

public class Accounts {
    public static void main(String[] args) {

        // Verify that the input arguments are valid
        if (args.length != 2) {
            System.out.println("Please provide the input in correct format: java <program_name> <points_to_spend> <csv_file>");
            return;
        }
        int pointsToSpend;
        try {
            pointsToSpend = Integer.parseInt(args[0]);
        } catch (NumberFormatException e) {
            System.out.println("Invalid input for points to spend: " + args[0]);
            return;
        }

        // Read the transactions from the CSV file
        List<Transaction> transactions = readTransactions(args[1]);

        // Create a map of payers to their current point balances
        Map<String, Integer> payerBalances = new HashMap<>();
        for (Transaction transaction : transactions) {
            payerBalances.put(transaction.payer, payerBalances.getOrDefault(transaction.payer, 0) + transaction.points);
        }

        // Sort the transactions by timestamp in ascending order
        transactions.sort(Comparator.comparing(Transaction::getTimestamp));

        // Spend the points according to the rules specified in the prompt
        for (Transaction transaction : transactions) {
            if (pointsToSpend <= 0)
                break;
            int amountToSpend = Math.min(pointsToSpend, transaction.points);
            pointsToSpend -= amountToSpend;
            payerBalances.put(transaction.payer, payerBalances.get(transaction.payer) - amountToSpend);
        }

        // Print the remaining balances for each payer
        for(Map.Entry<String,Integer> balance: payerBalances.entrySet())
            System.out.println(balance.getKey()+" : "+balance.getValue());
    }

    private static List<Transaction> readTransactions(String filename) {
        List<Transaction> transactions = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                // skip header or malformed lines
                if (parts.length != 3 || parts[0].substring(1,parts[0].length()-1).equals("payer") )
                    continue;
                String payer = parts[0].substring(1,parts[0].length()-1);
                int points = Integer.parseInt(parts[1]);
                Instant timestamp = Instant.parse(parts[2].substring(1,parts[2].length()-1));
                transactions.add(new Transaction(payer, points, timestamp));
            }
        } catch (IOException e) {
            System.out.println("Failed to read transactions from file: " + filename);
        } catch (NumberFormatException e) {
            System.out.println("Invalid transaction format: " + e.getMessage());
        }
        return transactions;
    }
}
   class Transaction {
        String payer;
        int points;
        Instant timestamp;

        Transaction(String payer, int points, Instant timestamp) {
            this.payer = payer;
            this.points = points;
            this.timestamp = timestamp;
        }

        public int getPoints() {
            return points;
        }

        public Instant getTimestamp() {
            return timestamp;
        }

    }


