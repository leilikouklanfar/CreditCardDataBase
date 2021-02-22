/**
 * Overwrites the interface for a credit card database for the hash function
 * data structure.
 *
 * @author leilikouklanfar
 *
 */
public class CreditCardDataBaseHash implements CCDatabase {

    private long[] key = new long[101];
    private CreditCardBuckets[] bucket = new CreditCardBuckets[101];

    @Override
    public boolean createAccount(long accountNumber, String name, String address, double creditLimit, double balance) {

        int index = search(accountNumber);
        boolean inserted = false;

        if (index == -1) {
            tableResize();
            index = search(accountNumber);

        }

        while (!inserted) {

            CreditCardAccount newAccount = new CreditCardAccount(accountNumber, name, address, creditLimit, balance);
            if (bucket[index] == null || bucket[index].isDeleted()) {

                key[index] = accountNumber;

                CreditCardBuckets newBucket = new CreditCardBuckets();
                newBucket.setAccount(newAccount);
                bucket[index] = newBucket;

                inserted = true;

            } else if (bucket[index] != null && (key[index] == accountNumber)) {

                bucket[index].setAccount(newAccount);
                inserted = true;
            }
        }

        return inserted;
    }

    /**
     * The following method approximately doubles the table size by two. The new
     * table size is the next prime number after the old table size is doubled. This
     * is done by doubling the old prime number and adding one. If the new number is
     * not a prime number, two is added to the number until it is a prime number.
     * After the appropriate table size is found, all items from the old tables is
     * added to the new tables.
     *
     */
    private void tableResize() {
        int newSize = bucket.length;
        newSize *= 2;
        newSize += 1;
        while (!isPrime(newSize)) {
            newSize += 2;
        }

        CreditCardBuckets[] bucket2 = new CreditCardBuckets[newSize];

        long[] key2 = new long[newSize];

        for (int i = 0; i < bucket.length; i++) {
            bucket2[i] = bucket[i];
            key2[i] = key[i];
        }

        bucket = bucket2;
        key = key2;

    }

    /**
     * This method determines if num is a prime number or not. False is returned if
     * number is not prime while true is returned if the number is prime.
     *
     * @param num the number to be evaluated(prime or not)
     * @return a boolean is returned where representing
     */
    boolean isPrime(int num) {
        for (int i = 2; i < Math.sqrt(num); i++) {
            if (num % i == 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * This method uses quadratic probing to move through the table.
     *
     * @param accountNumber a long representing the account Number to be searched
     *                      for.
     * @return index an integer representing the index the accountNumber should be
     *         added(if the bucket at that index is null), where it is or -1 if the
     *         whole table is searched as the table is full and account does not
     *         exist.
     */
    private int search(long accountNumber) {
        int hashValue = hashFunction(accountNumber);
        int counter = 0;
        int arrayLength = bucket.length;
        int index = 0;
        boolean done = false;

        while (!done) {

            index = (hashValue + (counter * counter)) % key.length;

            if (bucket[index] == null) {
                done = true;

            } else if (bucket[index] != null && (key[index] == accountNumber)) {
                done = true;
            }

            counter++;

            if (((counter / arrayLength) * 100) > 60) {
                return -1;
            }

        }

        return index;

    }

    @Override
    public boolean deleteAccount(long accountNumber) {
        int index = search(accountNumber);
        if (index != -1) {
            if (bucket[index] != null && (key[index] == accountNumber)) {
                bucket[index].setDeleted(true);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean adjustCreditLimit(long accountNumber, double newLimit) {
        int index = search(accountNumber);

        if (index != -1) {
            if (bucket[index] != null && (key[index] == accountNumber)) {
                CreditCardAccount subAccount = bucket[index].getAccount();
                subAccount.setCreditCardLimit(newLimit);
                bucket[index].setAccount(subAccount);
                return true;
            }
        }
        return false;
    }

    @Override
    public String getAccount(long accountNumber) {
        // TODO Auto-generated method stub
        int index = search(accountNumber);
        if (index != -1) {

            if (bucket[index] != null && (key[index] == accountNumber)) {
                bucket[index].setDeleted(true);

                CreditCardAccount subAccount = bucket[index].getAccount();

                return Long.toString(subAccount.getCreditCardNumber()) + "\n" + subAccount.getCardHolderName() + "\n"
                        + subAccount.getCardHolderAddress() + "\n" + Double.toString(subAccount.getCreditCardLimit())
                        + "\n" + Double.toString(subAccount.getCreditCardBalanceOwing()) + "\n";
            }
        }
        return null;
    }

    @Override
    public boolean makePurchase(long accountNumber, double price) throws Exception {
        int index = search(accountNumber);

        if (bucket[index] != null && (key[index] == accountNumber)) {
            CreditCardAccount subAccount = bucket[index].getAccount();

            double creditLimit = subAccount.getCreditCardLimit();
            double currentBalance = subAccount.getCreditCardBalanceOwing();
            double totalFunds = currentBalance + price;

            if (totalFunds > creditLimit) {
                throw new Exception("insufficient fund");
            } else {
                double newLimit = creditLimit - totalFunds;
                adjustCreditLimit(accountNumber, newLimit);
            }

            return true;
        }
        return false;
    }

    /**
     * The following method takes a 16 digit credit card number(the key) and splits
     * it into four components, each four digit long in order to map to the hash
     * value. The four components are named c1(value of the first four
     * digits),c2(value of the second four digits),c3(value of the third four
     * digits), and c4(value of the fourth four digits). Then the following folding
     * operation is used to obtain the hash value(folded key):
     *
     * 17c1 + (17^2)c2 + (17^3)c3 + (17^4)c4
     *
     * @param accountNumber a long representing the credit card number
     * @return an integer representing the hash value (the folded key)
     */
    public int hashFunction(long accountNumber) {

        String longAsString = Long.toString(accountNumber);

        String firstShort;
        firstShort = longAsString.substring(0, 3);
        String secondShort;
        secondShort = longAsString.substring(4, 7);
        String thirdShort;
        thirdShort = longAsString.substring(8, 11);
        String forthShort;
        forthShort = longAsString.substring(12, 15);

        short c1;
        c1 = Short.parseShort(firstShort);
        short c2;
        c2 = Short.parseShort(secondShort);
        short c3;
        c3 = Short.parseShort(thirdShort);
        short c4;
        c4 = Short.parseShort(forthShort);

        long seventeen;
        seventeen = 17;
        long seventeen2;
        seventeen2 = (long) Math.pow(17, 2);
        long seventeen3;
        seventeen3 = (long) Math.pow(17, 3);
        long seventeen4;
        seventeen4 = (long) Math.pow(17, 4);

        seventeen = seventeen * c1;
        seventeen2 = seventeen * c2;
        seventeen3 = seventeen * c3;
        seventeen4 = seventeen * c4;

        long sum = seventeen + seventeen2 + seventeen3 + seventeen4;

        int hashValue = (int) sum;

        return hashValue;

    }

}
