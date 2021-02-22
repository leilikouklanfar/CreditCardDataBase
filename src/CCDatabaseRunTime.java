import java.io.File;
import java.io.FileNotFoundException;
import java.time.Duration;
import java.time.Instant;
import java.util.Scanner;

/**
 * The CCDatabaseRunTime program creates two versions of a data structure
 * required for the back end for a small credit card accounting system. In the
 * first version a sorted array is used while a hash table is used in the second
 * version, both interfaces will conform to a specific interface. The run time
 * is measured in order to compare their relative speed. The operations are real
 * from a text file that contains database operation instructions each on
 * subsequent lines where the name of the text file is read in at run time.
 *
 * @author leilikouklanfar
 *
 */
public class CCDatabaseRunTime {
    /**
     * The main method calls a new instance of CCDatabaseRunTime to enable us to
     * call the non static method "run()" in order to run the program. The name of
     * the text file is passed in through the parameter.
     *
     * @param args An array of Strings containing the text file name.
     */
    public static void main(String[] args) {

        new CCDatabaseRunTime().run(args[0]);

    }

    /**
     * This method calls other higher level methods to create credit card accounts
     * and time the run time.
     *
     * @param textFileName A String representing the text file name.
     */
    private void run(String textFileName) {

        File textFile = new File(textFileName);
        System.out.println("Sorted start");
        CCDatabase sorted = testDatabase(new CreditCardDataBaseSorted(), textFile);
        System.out.println("Sorted mid");
        long sortedTime = testingTime(sorted, textFile);
        System.out.println("Sorted Array Time :" + sortedTime);
        System.out.println("Sorted End");

        System.out.println("hash start");
        CCDatabase hash = testDatabase(new CreditCardDataBaseHash(), textFile);
        System.out.println("Hash mid");
        long hashTime = testingTime(hash, textFile);
        System.out.println("Hash Time :" + hashTime);
        System.out.println("hash end");

    }

    /**
     * This method extracts the data from the text file, extracting the required
     * information for account creation and creates the new accounts that are to be
     * created before "start".
     *
     * @param db       - the credit card account to be created
     * @param textFile - the text file which is being read in from
     * @return db - the credit card data base containing the new accounts
     */
    private CCDatabase testDatabase(CCDatabase db, File textFile) {

        int initialCreation = 0;
        try {
            Scanner sc = new Scanner(textFile);

            if (sc.hasNextLine()) {
                String line = sc.nextLine();

                while (!line.equals("start") && sc.hasNextLine()) {
                    line = sc.nextLine();

                    while (!line.equals("cre") && !line.equals("start")) {

                        long accountNum;
                        accountNum = Long.parseLong(line);
                        line = sc.nextLine();
                        String name;
                        name = line;
                        line = sc.nextLine();

                        String address = line;
                        line = sc.nextLine();
                        Double creditLimit = Double.parseDouble(line);

                        line = sc.nextLine();
                        Double balance = Double.parseDouble(line);
                        db.createAccount(accountNum, name, address, creditLimit, balance);
                        // System.out.println("in step 1" + db.getAccount(accountNum));
                        line = sc.nextLine();
                        initialCreation++;

                    }
                }
            }
            sc.close();
        } catch (FileNotFoundException exception) {

            exception.printStackTrace();
        }

        System.out.println("initialCreation" + initialCreation);
        return db;

    }

    /**
     * This method modifies the credit card data base previously created by adding
     * new accounts, deleting accounts, adjusting the credit limit or making a
     * purchase on the credit card.
     *
     * @param db       - the credit card data base to be modified
     * @param textFile - the text file which is being read in from
     * @return db - the credit card data base with possible modifications
     */

    private long testingTime(CCDatabase db, File textFile) {

        long timeTook = 0;
        try {
            Scanner sc = new Scanner(textFile);

            if (sc.hasNextLine()) {
                String line = sc.nextLine();
                while (!line.equals("start") && sc.hasNextLine()) {
                    line = sc.nextLine();
                }
            }

            Instant first = Instant.now();

            while (sc.hasNextLine()) {

                String line = sc.nextLine();
                while (!line.equals("stop")) {
                    if (line.equals("cre")) {
                        line = sc.nextLine();
                        long accountNum;
                        accountNum = Long.parseLong(line);
                        line = sc.nextLine();
                        String name;
                        name = line;
                        line = sc.nextLine();
                        String address = line;
                        line = sc.nextLine();
                        Double creditLimit = Double.parseDouble(line);
                        line = sc.nextLine();
                        Double balance = Double.parseDouble(line);
                        db.createAccount(accountNum, name, address, creditLimit, balance);

                    }
                    if (line.equals("del")) {
                        line = sc.nextLine();
                        long accountNum = Long.parseLong(line);
                        db.deleteAccount(accountNum);
                    }
                    if (line.equals("lim")) {
                        line = sc.nextLine();
                        long accountNum = Long.parseLong(line);
                        line = sc.nextLine();
                        Double newLimit = Double.parseDouble(line);
                        db.adjustCreditLimit(accountNum, newLimit);

                    }
                    if (line.equals("pur")) {
                        line = sc.nextLine();
                        long accountNum = Long.parseLong(line);
                        line = sc.nextLine();
                        Double price = Double.parseDouble(line);

                        try {
                            db.makePurchase(accountNum, price);
                        } catch (Exception exception) {
                            // do nothing
                        }
                    }

                    line = sc.nextLine();

                }
                Instant second = Instant.now();
                Duration duration = Duration.between(first, second);
                timeTook = duration.toNanos();
            }

            sc.close();
        } catch (FileNotFoundException exception) {

            exception.printStackTrace();
        }

        return timeTook;
    }

}
