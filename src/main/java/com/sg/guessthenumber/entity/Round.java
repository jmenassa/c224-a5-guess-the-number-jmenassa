package com.sg.guessthenumber.entity;

import java.time.LocalDateTime;

public class Round {

    private int id;
    private LocalDateTime time;
    private String guess;
    private String resultOfGuess;
    private int gameId;

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public String getGuess() {
        return guess;
    }

    public void setGuess(String guess) {
        this.guess = guess;
    }

    public String getResultOfGuess() {
        return resultOfGuess;
    }

    public void setResultOfGuess(String resultOfGuess) {
        this.resultOfGuess = resultOfGuess;
    }

}
