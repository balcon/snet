package com.epam.study.snet.dao.db.mysql;

import com.epam.study.snet.dao.DaoException;
import com.epam.study.snet.dao.RelationshipDao;
import com.epam.study.snet.enums.Relation;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

class MySqlRelationshipDao implements RelationshipDao {
    private DataSource dataSource;

    MySqlRelationshipDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Relation getRelation(String country1, String country2) throws DaoException {
        Relation relation = selectRelation(country1, country2);

        return relation == null ? Relation.NEUTRAL : relation;
    }

    @Override
    public void setRelation(String country1, String country2, Relation relation) throws DaoException {
        if (relation == Relation.NEUTRAL) deleteRelation(country1, country2);
        else if (selectRelation(country1, country2) == null)
            insertRelation(country1, country2, relation);
        else
            updateRelation(country1, country2, relation);
    }

    private Relation selectRelation(String country1, String country2) throws DaoException {
        Relation relation = null;
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT relation FROM snet.relationship " +
                            "WHERE (country1=? AND country2=?)" +
                            " OR (country1=? AND country2=?)");
            statement.setString(1, country1);
            statement.setString(2, country2);
            statement.setString(3, country2);
            statement.setString(4, country1);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String relationString = resultSet.getString("relation");
                relation = (relationString == Relation.BAD.toString())
                        ? Relation.BAD
                        : Relation.GOOD;
            }
        } catch (SQLException e) {
            throw new DaoException("Can't get relation", e);
        }
        return relation;
    }

    private void updateRelation(String country1, String country2, Relation relation) throws DaoException {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(
                    "UPDATE snet.relationship SET relation=? " +
                            "WHERE (country1=? AND country2=?)" +
                            "OR (country1=? AND country2=?)");
            statement.setString(1, relation.toString());
            statement.setString(2, country1);
            statement.setString(3, country2);
            statement.setString(4, country2);
            statement.setString(5, country1);
            statement.execute();
        } catch (SQLException e) {
            throw new DaoException("Can't update relation", e);
        }
    }

    private void deleteRelation(String country1, String country2) throws DaoException {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(
                    "DELETE snet.relationship WHERE (country1=? AND country2=?)" +
                            "OR (country1=? AND country2=?)");
            statement.setString(1, country1);
            statement.setString(2, country2);
            statement.setString(3, country2);
            statement.setString(4, country1);
            statement.execute();
        } catch (SQLException e) {
            throw new DaoException("Can't delete relation", e);
        }
    }

    private void insertRelation(String country1, String country2, Relation relation) throws DaoException {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO snet.relationship (country1, country2, relation) VALUES (?,?,?)");
            statement.setString(1, country1);
            statement.setString(2, country2);
            statement.setString(3, relation.toString());
            statement.execute();
        } catch (SQLException e) {
            throw new DaoException("Can't set relation", e);
        }
    }
}
