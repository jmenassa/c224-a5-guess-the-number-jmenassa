package com.sg.guessthenumber.dao;
import com.sg.guessthenumber.entity.Round;

import java.util.List;

public interface RoundDao {

    Round guess(Round round);

    List<Round> getAllRounds(int gameId);
}
