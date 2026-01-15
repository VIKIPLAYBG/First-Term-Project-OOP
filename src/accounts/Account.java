package accounts;

import java.util.Arrays;

public abstract class Account {
    protected double[] withdraws;
    protected int numberOfWithdraws;
    protected double[] deposits;
    protected int numberOfDeposits;
    static int numberOfActiveAccounts = 0;

    public Account() {
        withdraws = new double[1];
        deposits = new double[1];
    }

    public Account(double[] withdraws, int numberOfWithdraws, double[] deposits, int numberOfDeposits) throws IllegalArgumentException {
        if(deposits == null)
            throw new IllegalArgumentException("(Para) Deposits list is empty!");
        this.deposits = deposits;
        if(withdraws == null)
            throw new IllegalArgumentException("(Para) Withdraws list is empty!");
        this.withdraws = withdraws;
        if(numberOfDeposits < 0)
            throw new IllegalArgumentException("(Para) Number of Deposits cannot be negative (0 or more)");
        this.numberOfDeposits = numberOfDeposits;
        if(numberOfWithdraws < 0)
            throw new IllegalArgumentException("(Para) Number of Withdraws cannot be negative (0 or more)");
        this.numberOfWithdraws = numberOfWithdraws;
        numberOfActiveAccounts++;
    }

    public Account(final Account other) throws IllegalArgumentException {
        if(other.deposits == null)
            throw new IllegalArgumentException("(Copy) Deposits list is empty!");
        this.deposits = Arrays.copyOf(other.deposits, other.deposits.length);
        if(other.withdraws == null)
            throw new IllegalArgumentException("(Copy) Withdraws list is empty!");
        this.withdraws = Arrays.copyOf(other.withdraws, other.withdraws.length);
        if(other.numberOfDeposits < 0)
            throw new IllegalArgumentException("(Copy) Number of Deposits cannot be negative (0 or more)");
        this.numberOfDeposits = other.numberOfDeposits;
        if(other.numberOfWithdraws < 0)
            throw new IllegalArgumentException("(Copy) Number of Withdraws cannot be negative (0 or more)");
        this.numberOfWithdraws = other.numberOfWithdraws;
        numberOfActiveAccounts++;
    }

    public static int getNumberOfActiveAccounts() {
        return numberOfActiveAccounts;
    }

    public double getBalance() {
        double balance = 0;
        for(int i = 0; i < this.numberOfDeposits; i++) {
            balance += deposits[i];
        }

        for(int i = 0; i < this.numberOfWithdraws; i++) {
            balance -= withdraws[i];
        }
        return balance;
    }

    public void deposit(double to_deposit) {
        if (to_deposit <= 0)
            throw new IllegalArgumentException("Deposit value is invalid!");

        if (numberOfDeposits == deposits.length) {
            double[] newArr = new double[deposits.length * 2];
            System.arraycopy(deposits, 0, newArr, 0, deposits.length);
            deposits = newArr;
        }

        deposits[numberOfDeposits++] = to_deposit;
        System.out.println("Successful deposit!");
    }

    public int getNumberOfDeposits() {
        return numberOfDeposits;
    }

    public int getNumberOfWithdraws() {
        return numberOfWithdraws;
    }

    public double[] getDeposits() {
        return deposits;
    }

    public double[] getWithdraws() {
        return withdraws;
    }

    public void withdraw(double to_withdraw) {
        if (to_withdraw <= 0)
            throw new IllegalArgumentException("Withdraw value is invalid!");

        if (numberOfWithdraws == withdraws.length) {
            double[] newArr = new double[withdraws.length * 2];
            System.arraycopy(withdraws, 0, newArr, 0, withdraws.length);
            withdraws = newArr;
        }

        withdraws[numberOfWithdraws++] = to_withdraw;
        System.out.println("Successful withdrawal!");
    }

    public void printAccountInformation() {
        System.out.println("Balance :" + this.getBalance());
        System.out.println("Number of Deposits: " + this.numberOfDeposits);
        System.out.println("Number of Withdraws: " + this.numberOfWithdraws);
    }

    void resizeDeposits() {
        double[] newArr = new double[deposits.length * 2];
        System.arraycopy(deposits, 0, newArr, 0, deposits.length);
        deposits = newArr;
    }

    void resizeWithdraws() {
        double[] newArr = new double[withdraws.length * 2];
        System.arraycopy(withdraws, 0, newArr, 0, withdraws.length);
        withdraws = newArr;
    }
}
