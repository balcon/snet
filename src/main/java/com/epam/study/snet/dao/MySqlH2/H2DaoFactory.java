package com.epam.study.snet.dao.MySqlH2;

import com.epam.study.snet.dao.DaoFactory;
import com.epam.study.snet.dao.UserDao;
import com.epam.study.snet.dao.MessageDao;
import org.h2.jdbcx.JdbcDataSource;

import javax.sql.DataSource;

public class H2DaoFactory implements DaoFactory {

    private final DataSource dataSource;

    public H2DaoFactory() {
        JdbcDataSource jdbcDataSource = new JdbcDataSource();
        jdbcDataSource.setUrl("jdbc:h2:mem:test;MODE=MYSQL;DB_CLOSE_DELAY=-1");
        dataSource = jdbcDataSource;
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

