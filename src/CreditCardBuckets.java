/**
 * Creates a creditCardBucket class which holds all the information for one hash
 * table bucket.
 *
 * @author Leili Kouklanfar
 *
 */

public class CreditCardBuckets {
    /**
     * A CreditCardAccount holds all the information contained in a data structure
     * required for the back end for a small credit card accounting system.
     */
    private CreditCardAccount account;
    /**
     * Flag that indicates if this bucket has been deleted and if its data should be
     * ignored.
     */
    private boolean deleted = false;

    /**
     * Return a boolean representing if this bucket has been deleted.
     *
     * @return deleted {true} if this bucket is deleted; {false} otherwise.
     */
    public boolean isDeleted() {
        return this.deleted;
    }

    /**
     * Updates the deleted flag to the given value.
     *
     * @param deleted (true) if this bucket is deleted (false) otherwise
     */
    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    /**
     * Gives the credit card number.
     *
     * @return creditCardNumber a long representing the credit card number
     */
    public CreditCardAccount getAccount() {
        return account;
    }

    /**
     * sets the credit card number.
     *
     * @param account a long representing the credit card number
     */
    public void setAccount(CreditCardAccount account) {
        this.account = account;
    }
}
