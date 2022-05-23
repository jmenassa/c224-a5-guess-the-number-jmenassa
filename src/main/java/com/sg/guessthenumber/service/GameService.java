package com.sg.guessthenumber.service;

import com.sg.guessthenumber.dao.GameDao;
import com.sg.guessthenumber.dao.RoundDao;
import com.sg.guessthenumber.entity.Game;
import com.sg.guessthenumber.entity.Round;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

@Service
public class GameService {

    @Autowired
    private GameDao gameDao;
    @Autowired
    private RoundDao roundDao;

    public Round round(String guess, int gameId){
        String resultGuess = calculateGameResult(guess, gameId);

        if(resultGuess.compareToIgnoreCase("e:4:p:0") == 0){
            Game finishedGame = gameDao.findById(gameId);
            finishedGame.setStatus("finished");
            gameDao.update(finishedGame);
        }

        Round round = new Round();
        round.setTime(LocalDateTime.now());
        round.setGuess(guess);
        round.setGameId(gameId);
        round.setResultOfGuess(resultGuess);

        return roundDao.guess(round);
    }

    private String calculateGameResult(String guess, int gameId){
        int e = 0, p = 0;
        Game game = gameDao.findById(gameId);
        int[] guessF = getAnswerFormat(guess);
        int[] answer = getAnswerFormat(game.getAnswer());

        for(int i = 0; i < answer.length; i++){
            if(answer[i] == guessF[i]){
                e++;
            }else{
                for(int j = 0; j < answer.length; j++){
                    if(answer[i] == guessF[j]) {
                        p++;
                        break;
                    }
                }
            }
        }
        return "e:" + e + ":p:" + p;
    }

    private int[] getAnswerFormat(String answer) {
        int[] formattedAnswer = new int[4];
        int tabIndex = 0;
        for(int i = 0; i < answer.length(); i++){
            if(Character.isDigit(answer.charAt(i))){
                formattedAnswer[tabIndex] = answer.charAt(i);
                tabIndex++;
            }
        }
        return formattedAnswer;
    }

    public List<Round> getAllRounds(int gameId){
        return roundDao.getAllRounds(gameId);
    }

    public Game getGame(int id){
        Game game = gameDao.findById(id);
        if(game.getStatus().compareToIgnoreCase("in progress") == 0){
            game.setAnswer("*********");
        }
        return game;
    }

    public List<Game> getAllGames(){
        List<Game> filteredList = gameDao.getAll();

        for (Game game : filteredList) {
            if (game.getStatus().compareToIgnoreCase("in progress") == 0) {
                game.setAnswer("*********");
            }
        }
        return filteredList;
    }

    public Game newGame(){
        Game game = new Game();
        Set<Integer> answer = new LinkedHashSet<>();
        Random randNum = new Random();

        while(answer.size() < 4){
            answer.add(randNum.nextInt(9)+1);
        }

        game.setAnswer(answer.toString());
        game.setStatus("in progress");

        return gameDao.add(game);
    }

}
