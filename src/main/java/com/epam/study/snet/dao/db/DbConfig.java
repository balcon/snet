package com.epam.study.snet.dao.db;

import com.epam.study.snet.dao.DaoFactory;
import com.epam.study.snet.dao.db.mysql.MySqlDaoFactory;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class DbConfig {

    private static DataSource getDataSource() {
        //TODO: config Tomcat's connection pool

        String dbUrl = "";
        String dbUser = "";
        String dbPass = "";
        try {
            FileInputStream configFile = new FileInputStream("\\src\\main\\resources\\db\\mysql\\dbConfig.properties");
            Properties properties = new Properties();
            properties.load(configFile);
            dbUrl = properties.getProperty("db_url");
            dbUser = properties.getProperty("db_user");
            dbPass = properties.getProperty("db_pass");
        } catch (IOException e) {
            e.printStackTrace();
        }
        MysqlDataSource dataSource = new MysqlDataSource(); //TODO: make config file read
        dataSource.setUrl("jdbc:mysql://localhost:3306");
     //   dataSource.setUrl("jdbc:h2:mem:test;MODE=MYSQL;DB_CLOSE_DELAY=-1");
        dataSource.setUser("root");
        dataSource.setPassword("admin");
        dataSource.setUseUnicode(true);
        dataSource.setCharacterEncoding("utf8");
        return dataSource;
    }

    public static DaoFactory daoFactory = new MySqlDaoFactory(getDataSource());
}


