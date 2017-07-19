package com.epam.study.snet.dao.db.mysql;

import com.epam.study.snet.dao.StatusMessageDao;
import com.epam.study.snet.entity.StatusMessage;
import com.epam.study.snet.entity.User;
import com.epam.study.snet.validators.ProfileValidator;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.*;

public class MySqlStatusMessageDaoTest extends MySqlDaoTests{
    private ProfileValidator userProfie = ProfileValidator.builder()
            .username("user1")
            .password("123456")
            .confirmPassword("123456")
            .gender("MALE")
            .country("US")
            .birthday("2017-01-01").build();
    @Test
    public void createStatusMessage() throws Exception {
        User user = daoFactory.getUserDao().create(userProfie);
        StatusMessage statusMessage=daoFactory.getStatusMessageDao(daoFactory.getUserDao()).create(user,"Text");

        assertTrue(statusMessage!=null);
    }

    @Test
    @Ignore
    public void getLastStatusMessage() throws Exception {
        User user=daoFactory.getUserDao().create(userProfie);
        StatusMessageDao statusMessageDao = daoFactory.getStatusMessageDao(daoFactory.getUserDao());
        StatusMessage statusMessage1= statusMessageDao.create(user,"Text1");
        StatusMessage statusMessage2= statusMessageDao.create(user,"Text2");

        StatusMessage statusMessage= statusMessageDao.getByUser(user);

        System.out.println(statusMessage2);
        System.out.println(statusMessage);
        assertEquals(statusMessage2.getBody(),statusMessage.getBody());
    }

}