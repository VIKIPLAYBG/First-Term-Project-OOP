package accounts;

public class BankAccount extends Account {
    private String IBAN;
    private String accHolder;
    private String password;

    public BankAccount(String IBAN, String accHolder, String password) {
        super();
        if(IBAN == null || IBAN.isEmpty()) throw new IllegalArgumentException("(Para) IBAN is empty!");
        this.IBAN = IBAN;
        if(accHolder == null || accHolder.isEmpty() ) throw new IllegalArgumentException("(Para) Account Holder Name is empty!");
        this.accHolder = accHolder;
        if(password == null || password.isEmpty()) throw new IllegalArgumentException("(Para) Password is empty!");
        this.password = password;
    }

    public BankAccount(final BankAccount other) {
        super();
        if(other.IBAN == null || other.IBAN.isEmpty()) throw new IllegalArgumentException("(Copy) IBAN is empty!");
        this.IBAN = other.IBAN;
        if(other.accHolder == null || other.accHolder.isEmpty()) throw new IllegalArgumentException("(Copy) Account Holder Name is empty!");
        this.accHolder = other.accHolder;
        if(other.password == null || other.password.isEmpty()) throw new IllegalArgumentException("(Copy) Password is empty!");
        this.password = other.password;
    }

    public String getIBAN() {
        return this.IBAN;
    }

    public String getAccHolder() {
        return this.accHolder;
    }

    public String getPassword() {
        return this.password;
    }

    public void credit(double to_receive) {
        this.withdraws[numberOfWithdraws++] = to_receive;
        System.out.println("Successful Credit!");
    }

    @Override
    public void printAccountInformation() {
        System.out.println("IBAN: " + this.IBAN);
        System.out.println("Account Holder: ");
        System.out.println("Name: " + this.accHolder + " | Password: " + this.password);
        System.out.println("Balance: " + this.getBalance());
        System.out.println("Number of Deposits: " + this.numberOfDeposits);
        System.out.println("Number of Withdraws: " + this.numberOfWithdraws);
    }
}
