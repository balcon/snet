package com.epam.study.snet.model.dao.db.mysql;

import com.epam.study.snet.model.dao.DaoException;
import com.epam.study.snet.model.dao.ImageDao;
import com.epam.study.snet.view.Image;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MySqlImageDao implements ImageDao {
    private DataSource dataSource;

    MySqlImageDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Image create(InputStream imageStream) throws DaoException {
        Image image = null;
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO snet.images(image) VALUES (?)");
            statement.setBlob(1, imageStream);
            statement.execute();
            long imageId = 0;
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next())
                imageId = generatedKeys.getLong(1);
            image = Image.builder().id(imageId).build();
        } catch (SQLException e) {
            throw new DaoException("Can't create image", e);
        }
        return image;
    }

    @Override
    public byte[] getById(long id) throws DaoException {
        byte[] imageBytes = null;
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT image FROM snet.images WHERE imageId=?");
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                imageBytes = resultSet.getBytes("image");
            }
        } catch (SQLException e) {
            throw new DaoException("Can't getByUser image", e);
        }
        return imageBytes;
    }

    @Override
    public void removeById(long id) throws DaoException {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(
                    "DELETE FROM snet.images WHERE imageId=?");
            statement.setLong(1, id);
            statement.execute();
        } catch (SQLException e) {
            throw new DaoException("Can't remove image", e);
        }
    }
}
