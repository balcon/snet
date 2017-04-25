package com.epam.study.snet.dao;

import com.epam.study.snet.dao.DaoFactory;
import com.epam.study.snet.dao.db.mysql.MySqlDaoFactory;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class DaoConfig {

    private static DataSource getDataSource() {
        //TODO How it works?
        DataSource ds=null;
        try {
            InitialContext initContext = new InitialContext();
            ds = (DataSource) initContext.lookup("java:comp/env/jdbc/appname");
        } catch (NamingException e) {
            e.printStackTrace();
        }
        return ds;
    }

    public static DaoFactory daoFactory = new MySqlDaoFactory(getDataSource());
}


