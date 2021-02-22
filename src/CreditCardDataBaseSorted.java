/**
 * Overwrites the interface for a credit card database for the sorted array data
 * structure.
 *
 * @author leilikouklanfar
 *
 */
public class CreditCardDataBaseSorted implements CCDatabase {

    private int arraySizeCounter = 0;
    private CreditCardAccount[] creditAccounts = new CreditCardAccount[101];

    @Override
    public boolean createAccount(long accountNumber, String name, String address, double creditLimit, double balance) {

        if (getAccount(accountNumber) == null) {

            if ((creditAccounts.length) == arraySizeCounter) {
                creditAccounts = doubleCapacity(creditAccounts);
            }

            int ii;
            ii = arraySizeCounter - 1;
            if (arraySizeCounter != 0) {

                if (arraySizeCounter != 0) {
                    for (; (ii >= 0 && (creditAccounts[ii].getCreditCardNumber() > accountNumber)); ii--) {
                        creditAccounts[ii + 1] = creditAccounts[ii];
                    }
                }
            }
            CreditCardAccount newAccount = new CreditCardAccount(accountNumber, name, address, creditLimit, balance);
            creditAccounts[ii + 1] = newAccount;
            // System.out.println("add:" +arraySizeCounter);
            arraySizeCounter++;
            return true;

        }
        return false;
    }

    /**
     * This method doubles the array size by a factor of two and copies each index
     * of the original array to the new array and returns the new array.
     *
     * @param creditAccounts is the original array
     * @return the new array which contains all the elements of the old array but is
     *         double the size
     */
    private CreditCardAccount[] doubleCapacity(CreditCardAccount[] creditAccounts) {

        CreditCardAccount[] newCreditAccounts = new CreditCardAccount[creditAccounts.length * 2];
        for (int i = 0; i < creditAccounts.length; i++) {
            newCreditAccounts[i] = creditAccounts[i];
        }
        return newCreditAccounts;
    }

    @Override
    public boolean deleteAccount(long accountNumber) {

        int accountIndex = binarySearch(accountNumber);
        // System.out.println("AN" + accountIndex);
        if (accountIndex == -1) {
            return false;
        } else {
            // System.out.println("CL" + creditAccounts.length);
            if (accountIndex == (creditAccounts.length - 1)) {
                creditAccounts[accountIndex] = null;
            } else {
                int ii;
                for (ii = accountIndex; ii < (creditAccounts.length - 1); ii++) {
                    creditAccounts[ii] = creditAccounts[ii + 1];
                }
            }
            arraySizeCounter -= 1;

            return true;
        }
    }

    /**
     * This method uses a binary search to go through a sorted array in order to
     * find the account with the same account number as accountNumber. If the
     * account with the following account number is found, the index at which it is
     * found is returned, otherwise the method returns -1.
     *
     * @param accountNumber the account with the following number is searched for
     * @return -1 is returned if account number is not found, otherwise the index at
     *         which the desired account can be found is returned
     */
    private int binarySearch(long accountNumber) {
        int start = 0;
        int end = (arraySizeCounter - 1);
        // System.out.println(arraySizeCounter);
        if (arraySizeCounter != 0) {
            while (start <= end) {

                // System.out.println("start:" + start);
                int mid = (start + end) / 2;
                // System.out.println("Size:" +arraySizeCounter);
                // System.out.println("mid:" +mid);
                if (creditAccounts[mid].getCreditCardNumber() == accountNumber) {
                    return mid;
                } else if (creditAccounts[mid].getCreditCardNumber() < accountNumber) {
                    start = mid + 1;
                } else {
                    end = mid - 1;
                }
                // System.out.println("mi" +mid);
            }
        }
        return -1;

    }

    @Override
    public boolean adjustCreditLimit(long accountNumber, double newLimit) {

        int accountIndex = binarySearch(accountNumber);
        if (accountIndex == -1) {
            return false;
        }
        creditAccounts[accountIndex].setCreditCardLimit(newLimit);
        return true;
    }

    @Override
    public String getAccount(long accountNumber) {
        int accountIndex = binarySearch(accountNumber);
        if (accountIndex == -1) {
            return null;
        }

        return Long.toString(creditAccounts[accountIndex].getCreditCardNumber()) + "\n"
                + creditAccounts[accountIndex].getCardHolderName() + "\n"
                + creditAccounts[accountIndex].getCardHolderAddress() + "\n"
                + Double.toString(creditAccounts[accountIndex].getCreditCardLimit()) + "\n"
                + Double.toString(creditAccounts[accountIndex].getCreditCardBalanceOwing()) + "\n";

    }

    @Override
    public boolean makePurchase(long accountNumber, double price) throws Exception {
        int accountIndex = binarySearch(accountNumber);
        if (accountIndex == -1) {
            return false;
        }
        double creditLimit = creditAccounts[accountIndex].getCreditCardLimit();
        double currentBalance = creditAccounts[accountIndex].getCreditCardBalanceOwing();
        double totalFunds = currentBalance + price;

        if (totalFunds > creditLimit) {
            throw new Exception("insufficient fund");
        } else {
            double newLimit = creditLimit - totalFunds;
            adjustCreditLimit(accountNumber, newLimit);
        }

        return true;
    }

}
