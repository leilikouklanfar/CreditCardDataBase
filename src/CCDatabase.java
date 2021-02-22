
/**
 * Defines an interface for a credit card database.
 *
 * @author leilikouklanfar
 *
 */
public interface CCDatabase {
    /**
     * Creates an account with the given details if it does not exist. Returns
     * <code>true</code> if the account was created; <code>false</code> otherwise.
     */
    public boolean createAccount(long accountNumber, String name, String address, double creditLimit, double balance);

    /**
     * Deletes the account with the given account number if it does exists. Returns
     * <code>true</code> if an account was deleted; <code>false</code> otherwise.
     */
    public boolean deleteAccount(long accountNumber);

    /**
     * Adjusts the credit limit of the account with the given account number if it
     * exists. Returns <code>true</code> if the account exists and was modified;
     * <code>false</code> otherwise.
     */
    public boolean adjustCreditLimit(long accountNumber, double newLimit);

    /**
     * Returns the details of the given account as a string if it exists or
     * <code>null</code> if the account does not exist. The string should be in the
     * same format as for account creation, i.e. the credit card number, name of
     * card holder, address of card holder, credit limit, and balance each on a
     * separate line.
     */
    public String getAccount(long accountNumber);

    /**
     * Makes a purchase on the account with the given account number if it exists
     * and the account has sufficient credit. If the account exists, but has
     * insufficient funds (the old balance plus the purchase price is higher than
     * the account's credit limit), then an exception will be thrown. Returns
     * <code>true</code> if the account exists and was modified; <code>false</code>
     * otherwise.
     */
    public boolean makePurchase(long accountNumber, double price) throws Exception;
}
