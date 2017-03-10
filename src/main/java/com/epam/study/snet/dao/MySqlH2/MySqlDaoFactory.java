package com.epam.study.snet.dao.MySqlH2;

import com.epam.study.snet.dao.DaoFactory;
import com.epam.study.snet.dao.MessageDao;
import com.epam.study.snet.dao.UserDao;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import javax.sql.DataSource;

public class MySqlDaoFactory implements DaoFactory {
    private final DataSource dataSource;

    public MySqlDaoFactory() {
        MysqlDataSource mysqlDataSource = new MysqlDataSource();
        mysqlDataSource.setUrl("jdbc:mysql://localhost:3306");
        mysqlDataSource.setUser("root");
        mysqlDataSource.setPassword("admin");
        dataSource = mysqlDataSource;
    }

    @Override
    public UserDao getUserDao() {
        return new MySqlH2UserDao(dataSource);
    }

    @Override
    public MessageDao getMessageDao() {
        return new MySqlH2MessageDao(dataSource);
    }
}
