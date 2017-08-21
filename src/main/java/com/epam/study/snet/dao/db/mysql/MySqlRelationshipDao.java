package com.epam.study.snet.dao.db.mysql;

import com.epam.study.snet.dao.DaoException;
import com.epam.study.snet.dao.RelationshipDao;
import com.epam.study.snet.entity.Country;
import com.epam.study.snet.enums.Relation;
import com.epam.study.snet.model.RelationManager;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class MySqlRelationshipDao implements RelationshipDao {
    private DataSource dataSource;

    MySqlRelationshipDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Relation getRelation(Country country1, Country country2) throws DaoException {
        Relation relation = selectRelation(country1.getCode(), country2.getCode());
        if (country1.equals(country2)) return Relation.SAME;
        if (relation == null) return Relation.NEUTRAL;
        return relation;
    }

    @Override
    public void setRelation(Country country1, Country country2, Relation relation) throws DaoException {
        String code1 = country1.getCode();
        String code2 = country2.getCode();

        if (relation == Relation.NEUTRAL) deleteRelation(code1, code2);
        else if (selectRelation(code1, code2) == null)
            insertRelation(code1, code2, relation);
        else
            updateRelation(code1, code2, relation);
    }

    @Override
    public Map<Country, Relation> getRelations(Country country) throws DaoException {
        Map<Country, Relation> relations = new HashMap<>();
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM snet.relationship " +
                            "WHERE country1=? OR country2=?");
            statement.setString(1, country.getCode());
            statement.setString(2, country.getCode());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String relationString = resultSet.getString("relation");
                String country1 = resultSet.getString("country1");
                String country2 = resultSet.getString("country2");
                //FUCK!! FUCK!!!!!!!!! FUCK!!!
                Country otherCountry=country1.equals(country.getCode())
                        ? new Country(country2)
                        : new Country(country1);
                Relation relation = relationString.equals(Relation.BAD.toString())
                        ? Relation.BAD
                        : Relation.GOOD;
                relations.put(otherCountry,relation);
            }
        } catch (SQLException e) {
            throw new DaoException("Can't get relations", e);
        }
        return relations;
    }

    @Override
    public RelationManager getRelationManager() throws DaoException {
        List<RelationManager.Relationship> relations=new ArrayList<>();
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM snet.relationship");
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()) {
                Country country1=new Country(resultSet.getString("country1"));
                Country country2=new Country(resultSet.getString("country2"));
                Relation relation = (resultSet.getString("relation").equals(Relation.BAD.toString()))
                        ? Relation.BAD
                        : Relation.GOOD;
                RelationManager.Relationship relationship =
                        new RelationManager.Relationship(country1, country2, relation);
                relations.add(relationship);
            }
        } catch (SQLException e) {
            throw new DaoException("Can't get RelationManager", e);
        }
        return new RelationManager(relations);
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
                relation = (relationString.equals(Relation.BAD.toString()))
                        ? Relation.BAD
                        : Relation.GOOD;
            }
        } catch (SQLException e) {
            throw new DaoException("Can't getByUser relation", e);
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
