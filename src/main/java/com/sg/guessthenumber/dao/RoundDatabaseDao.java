package com.sg.guessthenumber.dao;

import com.sg.guessthenumber.entity.Round;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

@Repository
@Profile("database")
public class RoundDatabaseDao implements RoundDao {

    @Autowired
    JdbcTemplate jdbc;

    @Override
    public Round guess(Round round) {
        final String INSERT_ROUND = "INSERT INTO round(guess, resultOfGuess, time," +
                " gameId) VALUES(?,?,?,?)";
        jdbc.update(INSERT_ROUND,
                round.getGuess(),
                round.getResultOfGuess(),
                Timestamp.valueOf(round.getTime()),
                round.getGameId());
        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        round.setId(newId);

        return round;
    }

    @Override
    public List<Round> getAllRounds(int gameId) {
        final String SELECT_ALL_ROUNDS = "SELECT * FROM round WHERE round.gameID = " + gameId;
        List<Round> rounds = jdbc.query(SELECT_ALL_ROUNDS, new RoundMapper());

        return rounds;
    }

    public static final class RoundMapper implements RowMapper<Round> {

        @Override
        public Round mapRow(ResultSet rs, int index) throws SQLException {
            Round r = new Round();
            r.setId(rs.getInt("id"));
            r.setGuess(rs.getString("guess"));
            r.setResultOfGuess(rs.getString("resultOfGuess"));
            r.setTime(rs.getTimestamp("time").toLocalDateTime());
            r.setGameId(rs.getInt("gameId"));
            return r;
        }
    }
}
