package com.epam.study.snet.dao.db.mysql;

import com.epam.study.snet.dao.DaoFactory;
import com.epam.study.snet.dao.MessageDao;
import com.epam.study.snet.dao.RelationshipDao;
import com.epam.study.snet.dao.UserDao;
import org.h2.jdbcx.JdbcDataSource;
import org.junit.AfterClass;
import org.junit.BeforeClass;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public abstract class MySqlDaoTests {

   protected static JdbcDataSource dataSource;
   protected static DaoFactory daoFactory;

    @BeforeClass
    public static void createH2Schema() throws Exception {
        String createScriptPath = "src\\main\\resources\\db\\mysql\\createSchema.sql";

        dataSource = new JdbcDataSource();
        dataSource.setUrl("jdbc:h2:mem:test;MODE=MYSQL;DB_CLOSE_DELAY=-1");

        try (Connection connection = dataSource.getConnection()) {
            executeScript(connection, createScriptPath);
        }
        daoFactory = new MySqlDaoFactory(dataSource);
    }

    @AfterClass
    public static void dropH2Schema() throws Exception {
        String dropScriptPath = "src\\main\\resources\\db\\mysql\\dropSchema.sql";
        try (Connection connection = dataSource.getConnection()) {
            executeScript(connection, dropScriptPath);
        }
    }

    static void executeScript(Connection connection, String scriptPath) throws FileNotFoundException, SQLException {
        try (Scanner scanner = new Scanner(new File(scriptPath)).useDelimiter(";")) {
            connection.setAutoCommit(false);
            while (scanner.hasNext()) {
                connection.createStatement().execute(scanner.next());
            }
            connection.commit();
            connection.setAutoCommit(true);
        }
    }
}
