package com.sg.guessthenumber.dao;

import com.sg.guessthenumber.entity.Game;

import java.util.List;

public interface GameDao {

    Game add(Game game);

    List<Game> getAll();

    Game findById(int id);

    void update(Game finishedGame);
}
