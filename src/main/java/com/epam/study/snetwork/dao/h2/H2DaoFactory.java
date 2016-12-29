package com.epam.study.snetwork.dao.h2;

import com.epam.study.snetwork.dao.DaoFactory;
import com.epam.study.snetwork.dao.UserDao;
import org.h2.jdbcx.JdbcDataSource;

import javax.sql.DataSource;

public class H2DaoFactory implements DaoFactory {
    
    private final DataSource dataSource;

    public H2DaoFactory() {
        JdbcDataSource jdbcDataSource=new JdbcDataSource();
        jdbcDataSource.setUrl("jdbc:h2:mem:test;MODE=MYSQL;DB_CLOSE_DELAY=-1");
        dataSource=jdbcDataSource;
    }

    public UserDao getUserDao() {
        return new H2UserDao(dataSource);
    }
}
