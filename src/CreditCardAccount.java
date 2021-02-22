/**
 * Creates a CreditCardAccount class which holds all the information contained
 * in a data structure required for the back end for a small credit card
 * accounting system.
 *
 * @author Leili Kouklanfar
 *
 */
public class CreditCardAccount {

    private long creditCardNumber;
    private String cardHolderName;
    private String cardHolderAddress;
    private double creditCardLimit;
    private double creditCardBalanceOwing;

    /**
     * Creates an account with the given details.
     *
     * @param accountNumber a long representing the account number
     * @param name          a String representing the card holders name
     * @param address       a String representing the card holders address
     * @param creditLimit   a Double representing the credit Limit for that card
     * @param balance       a double representing the current balance owing on that
     *                      card
     */

    public CreditCardAccount(long accountNumber, String name, String address, double creditLimit, double balance) {
        this.creditCardNumber = accountNumber;
        this.cardHolderName = name;
        this.cardHolderAddress = address;
        this.creditCardLimit = creditLimit;
        this.creditCardBalanceOwing = balance;
    }

    /**
     * sets the address for the given account.
     *
     * @param cardHolderAddresss a String representing the card holders address
     *
     */
    public void setCardHolderAddress(String cardHolderAddresss) {
        this.cardHolderAddress = cardHolderAddresss;
        return;
    }

    /**
     * sets the credit card limit for the given account.
     *
     * @param creditCardLimitt a Double representing the credit Limit for that card
     *
     */
    public void setCreditCardLimit(double creditCardLimitt) {
        this.creditCardLimit = creditCardLimitt;
        return;
    }

    /**
     * sets the balance owing for the given account.
     *
     * @param creditCardBalanceOwingg a double representing the current balance
     *                                owing on that card
     */
    public void setCreditCardBalanceOwing(double creditCardBalanceOwingg) {
        this.creditCardBalanceOwing = creditCardBalanceOwingg;
        return;
    }

    public long getCreditCardNumber() {
        return creditCardNumber;
    }

    public String getCardHolderName() {
        return cardHolderName;
    }

    public String getCardHolderAddress() {
        return cardHolderAddress;
    }

    public double getCreditCardLimit() {
        return creditCardLimit;
    }

    public double getCreditCardBalanceOwing() {
        return creditCardBalanceOwing;
    }

}
