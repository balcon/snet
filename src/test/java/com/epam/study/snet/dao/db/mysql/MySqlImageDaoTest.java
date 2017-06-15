package com.epam.study.snet.dao.db.mysql;

import com.epam.study.snet.dao.ImageDao;
import com.epam.study.snet.model.Image;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class MySqlImageDaoTest extends MySqlDaoTests {

private ImageDao imageDao=daoFactory.getImageDao();

    @Test
    public void createImage() throws Exception {
        File file=new File("src\\test\\resources\\db\\cat3.jpg");
        InputStream inputStream = new FileInputStream(file);
        Image image = imageDao.create(inputStream);

        assertTrue(image.getId()!=0);
    }

    @Test
    public void readImage() throws Exception {
        Path path= Paths.get("src\\test\\resources\\db\\cat3.jpg");
        byte[] bArray1 = Files.readAllBytes(path);
        InputStream inputStream = new ByteArrayInputStream(bArray1);
        Image image = imageDao.create(inputStream);

        byte[] bArray2 = imageDao.getById(image.getId());

        assertTrue(Arrays.equals(bArray1,bArray2));
    }

    @Test
    public void removeImage() throws Exception {
        File file=new File("src\\test\\resources\\db\\cat3.jpg");
        InputStream inputStream = new FileInputStream(file);


        Image image = imageDao.create(inputStream);

        imageDao.removeById(image.getId());

        byte[] bArray = imageDao.getById(image.getId());

        assertEquals(null,bArray);
    }
}