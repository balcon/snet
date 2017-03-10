package com.epam.study.snet;

import com.epam.study.snet.dao.DaoFactory;
import com.epam.study.snet.dao.MySqlH2.MySqlDaoFactory;

public class Static {
    public static DaoFactory daoFactory = new MySqlDaoFactory();
}


