import accounts.*;

import java.io.*;
import java.util.*;

public class Main {

    public static void saveBankAccount(BankAccount acc, PrintWriter out) {
        out.println(acc.getIBAN());
        out.println(acc.getAccHolder());
        out.println(acc.getPassword());

        out.println(acc.getNumberOfDeposits());
        for (int i = 0; i < acc.getNumberOfDeposits(); i++)
            out.print(acc.getDeposits()[i] + " ");
        out.println();

        out.println(acc.getNumberOfWithdraws());
        for (int i = 0; i < acc.getNumberOfWithdraws(); i++)
            out.print(acc.getWithdraws()[i] + " ");
        out.println();
        out.println("-----------------------------");
    }

    public static void saveAllAccounts(List<BankAccount> accounts, String filename) throws IOException {
        try (PrintWriter out = new PrintWriter(new FileWriter(filename))) { // overwrite
            for (BankAccount acc : accounts) {
                saveBankAccount(acc, out);
            }
        }
    }

    public static List<BankAccount> loadBankAccounts(String filename) throws IOException {
        File file = new File(filename);
        if (!file.exists()) file = new File("src/" + filename);
        if (!file.exists()) file = new File("../src/" + filename);
        if (!file.exists()) throw new FileNotFoundException("File doesn't exist!");
        if (file.length() == 0) throw new FileNotFoundException("File is empty!");

        List<BankAccount> accounts = new ArrayList<>();

        try (Scanner sc = new Scanner(file)) {
            sc.useLocale(Locale.US);

            while (sc.hasNextLine()) {
                String iban = sc.nextLine().trim();
                if (iban.isEmpty() || iban.equals("-----------------------------")) continue;

                String holder = sc.nextLine().trim();
                String pass = "default";
                BankAccount acc = new BankAccount(iban, holder, pass);

                int depCount = Integer.parseInt(sc.nextLine().trim());
                String[] depValues = sc.nextLine().trim().split(" ");
                for (int i = 0; i < depCount; i++) {
                    acc.deposit(Double.parseDouble(depValues[i]));
                }

                int withCount = Integer.parseInt(sc.nextLine().trim());
                String[] withValues = sc.nextLine().trim().split(" ");
                for (int i = 0; i < withCount; i++) {
                    acc.withdraw(Double.parseDouble(withValues[i]));
                }

                if (sc.hasNextLine()) sc.nextLine();
                accounts.add(acc);
            }
        }
        return accounts;
    }

    public static BankAccount selectAccount(List<BankAccount> accounts, Scanner sc) {
        if (accounts.size() == 1) return accounts.get(0);

        System.out.println("\nSelect an account:");
        for (int i = 0; i < accounts.size(); i++) {
            BankAccount a = accounts.get(i);
            System.out.printf("%d. %s (%s)%n", i + 1, a.getIBAN(), a.getAccHolder());
        }
        System.out.print("Enter your choice: ");
        int index = Integer.parseInt(sc.nextLine()) - 1;
        if (index < 0 || index >= accounts.size()) {
            System.out.println("Invalid account choice! Defaulting to first account.");
            return accounts.getFirst();
        }
        return accounts.get(index);
    }

    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        List<BankAccount> accounts = new ArrayList<>();

        System.out.println("1. Enter accounts");
        System.out.println("2. Load from file");
        System.out.print("Enter your choice: ");
        String choice = sc.nextLine();

        if (choice.equals("2")) {
            System.out.print("Enter file name: ");
            String loadFileName = sc.nextLine();
            accounts = loadBankAccounts(loadFileName);
            System.out.println(accounts.size() + " accounts loaded!");
        } else if (choice.equals("1")) {
            System.out.print("Enter IBAN: ");
            String IBAN = sc.nextLine();
            System.out.print("Enter account holder name: ");
            String accHolder = sc.nextLine();
            System.out.print("Enter password: ");
            String pass = sc.nextLine();

            BankAccount acc = new BankAccount(IBAN, accHolder, pass);
            accounts.add(acc);
        } else {
            System.out.println("Error! Invalid choice!");
            sc.close();
            return;
        }

        BankAccount acc = selectAccount(accounts, sc);

        while (true) {
            System.out.println("\n--- Menu ---");
            System.out.println("1. Deposit");
            System.out.println("2. Withdraw");
            System.out.println("3. Check account info");
            System.out.println("4. Switch account");
            System.out.println("5. Save and Exit");
            System.out.print("Enter your choice: ");
            String option = sc.nextLine();

            switch (option) {
                case "1":
                    System.out.print("Enter value to deposit: ");
                    String to_deposit = sc.nextLine();
                    if (!to_deposit.matches("[0-9]+(\\.[0-9]+)?"))
                        System.out.println("Deposit value is invalid!");
                    else
                        acc.deposit(Double.parseDouble(to_deposit));
                    break;

                case "2":
                    System.out.print("Enter value to withdraw: ");
                    String to_withdraw = sc.nextLine();
                    if (!to_withdraw.matches("[0-9]+(\\.[0-9]+)?"))
                        System.out.println("Withdraw value is invalid!");
                    else
                        acc.withdraw(Double.parseDouble(to_withdraw));
                    break;

                case "3":
                    acc.printAccountInformation();
                    break;

                case "4":
                    acc = selectAccount(accounts, sc);
                    break;

                case "5":
                    System.out.print("Enter file name to save: ");
                    String filename = sc.nextLine();
                    saveAllAccounts(accounts, filename);
                    sc.close();
                    System.out.println("Accounts saved.");
                    return;

                default:
                    System.out.println("Error! Invalid option!");
            }
        }
    }
}
