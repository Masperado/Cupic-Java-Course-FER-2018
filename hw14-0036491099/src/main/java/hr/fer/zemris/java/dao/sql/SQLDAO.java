package hr.fer.zemris.java.dao.sql;

import hr.fer.zemris.java.dao.DAOException;
import hr.fer.zemris.java.dao.DAO;
import hr.fer.zemris.java.model.Poll;
import hr.fer.zemris.java.model.Result;
import hr.fer.zemris.java.model.Song;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * This class represents implementation of {@link DAO} for this web application.
 */
public class SQLDAO implements DAO {

    @Override
    public List<Poll> getPolls() {
        Connection con = SQLConnectionProvider.getConnection();
        PreparedStatement pst;
        List<Poll> polls = new ArrayList<>();

        try {
            pst = con.prepareStatement("SELECT * FROM POLLS");

            ResultSet rset = pst.executeQuery();
            while (rset != null && rset.next()) {
                Poll poll = new Poll(String.valueOf(rset.getLong(1)), rset.getString(2), rset.getString(3));
                polls.add(poll);
            }
        } catch (SQLException ex) {
            throw new DAOException("Error while loading polls.", ex);
        }

        return polls;

    }

    @Override
    public List<Song> getSongs(long pollIdLong) {
        List<Song> songs = new ArrayList<>();
        Connection con = SQLConnectionProvider.getConnection();
        PreparedStatement pst;

        try {

            pst = con.prepareStatement("select id, optionTitle, optionLink from PollOptions where pollID=? order by " +
                    "id");
            pst.setLong(1, pollIdLong);
            ResultSet rs = pst.executeQuery();
            while (rs != null && rs.next()) {
                Song song = new Song(String.valueOf(rs.getLong(1)), rs.getString(2), rs.getString(3));
                songs.add(song);
            }

        } catch (SQLException ex) {
            throw new DAOException("Error while loading songs.", ex);
        }


        return songs;
    }


    @Override
    public List<Result> getResults(long pollIdLong) {
        List<Result> results = new ArrayList<>();
        Connection con = SQLConnectionProvider.getConnection();
        PreparedStatement pst;

        try {

            pst = con.prepareStatement("select id, optionTitle, optionLink,votesCount from PollOptions where pollID=?" +
                    " order by votesCount DESC ");
            pst.setLong(1, pollIdLong);
            ResultSet rs = pst.executeQuery();
            while (rs != null && rs.next()) {
                Result result = new Result(String.valueOf(rs.getLong(1)), rs.getString(2), rs.getString(3), rs.getString(4));
                results.add(result);
            }

        } catch (SQLException ex) {
            throw new DAOException("Error while loading results.", ex);
        }

        return results;
    }

    @Override
    public void voteFor(long pollIdLong, long id) {
        List<Result> results = new ArrayList<>();
        Connection con = SQLConnectionProvider.getConnection();
        PreparedStatement pst;

        try {

            pst = con.prepareStatement("select id, optionTitle, optionLink,votesCount from PollOptions where pollID=?" +
                    " and id=?");
            pst.setLong(1, pollIdLong);
            pst.setLong(2, id);
            ResultSet rs = pst.executeQuery();
            while (rs != null && rs.next()) {
                Result result = new Result(String.valueOf(rs.getLong(1)), rs.getString(2), rs.getString(3), rs.getString(4));
                results.add(result);
            }

            if (results.size() != 1) {
                throw new SQLException("Mutliple voting choices.");
            }

            Result result = results.get(0);
            pst = con.prepareStatement("UPDATE PollOptions SET votesCount=? WHERE id=?");

            pst.setLong(1, Long.valueOf(result.getResult()) + 1);
            pst.setLong(2, id);

            int numberOfAffectedRows = pst.executeUpdate();
            if (numberOfAffectedRows != 1) {
                throw new SQLException("Error while voting.");
            }


        } catch (SQLException | NumberFormatException ex) {
            throw new DAOException("Error while voting.", ex);
        }

    }


}
