package worksheet1.exercise;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import worksheet1.model.Letters;

public class Ex22 {
    private static Logger LOGGER = Logger.getLogger(Ex22.class.getName());

    private ConnectionSource connectionSource;
    private Dao<Letters, Integer> departmentDao;

    public void runDemonstration() {
        // creates connection to a MariaDB installed on localhost
        boolean connected = this.connectToDB("jdbc:mariadb://bilbao.informatik.uni-stuttgart.de/pe2-db-a1",
                "pe2-nutzer", "esJLtFm6ksCT4mCyOS");

        if (connected) {
            try {
                // instantiates the Database Access Object (DAO) for handling DB operations using the DaoManager
                this.departmentDao = DaoManager.createDao(this.connectionSource, Letters.class);

                // retrieves and logs the content od the table
                this.retrieveAllEntries();

                // retrieves and logs the searched ids of the table
                this.retrieveSearchedIds();

                // retrieves and logs the searched sum and average of the table
                this.retrieveSearchedNumbers();

                // closes connection to the database
                this.closeConnectionToDB();

            } catch (SQLException exception) {
                this.logSQLException(exception);
            }
        }
    }

    /*
     * Connects to a database
     */
    private boolean connectToDB(String connectionString, String user, String password) {
        try {
            this.connectionSource = new JdbcConnectionSource(connectionString, user, password);
            return true;
        } catch (SQLException exception) {
            this.logSQLException(exception);
        }
        return false;
    }

    /*
     * Closes connection to the database
     */
    private void closeConnectionToDB() {
        try {
            this.connectionSource.close();
        } catch (Exception exception) {
            LOGGER.log(Level.SEVERE, "Error message: " + exception.getMessage());
        }
    }



    /*
     * Shows examples retrieving the content of the table.
     */
    private void retrieveAllEntries() {
        try {
            //gets all entries of the letters table
           List<Letters> lettersList = departmentDao.queryForAll();
            for (Letters letter : lettersList) {
                String letterName = letter.getLetter();
                LOGGER.log(Level.INFO, "Letter: " + letterName);
            }

            //displays the searched Word in the table
            int[] arrayIndexes = { 72, 48, 71, 37, 48, 11, 69, 5, 20, 44, 50, 13, 17, 33, 41, 68,
                    77, 44, 29, 65, 65 };
            for(int i = 0; i < arrayIndexes.length; i++){
                Letters letter = departmentDao.queryForId(arrayIndexes[i]);
                String letterName = letter.getLetter();
                LOGGER.log(Level.INFO, "Letter: " + letterName);
            }


        } catch (SQLException exception) {
            this.logSQLException(exception);
        }
    }

    /*
     * Shows id examples retrieving the content of the table.
     */
    private void retrieveSearchedIds() {
        try {
            //displays the searched Ids of the letters in the table
            String[] searchedStrings = {"w", "a", "s"};
            for(int i = 0; i < searchedStrings.length; i++) {
                List<Letters> letters = departmentDao.queryForEq("letter", searchedStrings[i]);
                for (Letters letter : letters) {
                    String letterName = letter.getLetter();
                    String letterId = letter.getId().toString();
                    LOGGER.log(Level.INFO, "Letter:"+ letterName + " Letter Id: " + letterId);
                }
            }
        } catch (SQLException exception) {
            this.logSQLException(exception);
        }
    }

    /*
     * Shows number examples retrieving the content of the table.
     */
    private void retrieveSearchedNumbers() {
        try {

            //gets all entries of the letters table and adds their ids to a list
            List<Letters> lettersList1 = departmentDao.queryForAll();
            List<Integer> Ids = new ArrayList<>();
            for (Letters letter : lettersList1) {
                Ids.add(letter.getId());
            }

            //displays the sum and the average of Ids of the letters in the table
            int sum = Ids.stream().mapToInt(Integer::intValue).sum();
            LOGGER.log(Level.INFO, "Sum of Ids: " + sum);
            double average = Ids.stream().mapToInt(Integer::intValue).average().getAsDouble();
            LOGGER.log(Level.INFO, "Average of Ids: " + average);

        } catch (SQLException exception) {
            this.logSQLException(exception);
        }
    }

    private void logSQLException(SQLException exception) {
        LOGGER.log(Level.SEVERE, "Error code: " + exception.getErrorCode());
        LOGGER.log(Level.SEVERE, "Error message: " + exception.getMessage());
    }
}
