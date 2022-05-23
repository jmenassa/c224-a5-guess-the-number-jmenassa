package com.sg.guessthenumber.controller;

import com.sg.guessthenumber.dao.RoundDao;
import com.sg.guessthenumber.entity.Game;
import com.sg.guessthenumber.entity.Round;
import com.sg.guessthenumber.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class GameController {

   @Autowired
   private GameService service;

   @RequestMapping("/api/begin")
   @ResponseStatus(HttpStatus.CREATED)
   public int newGame(){
      return service.newGame().getId();
   }

   @RequestMapping("/api/game")
   public List<Game> showAllGames(){
      return service.getAllGames();
   }

   @RequestMapping("/api/game/{gameId}")
   public Game showGame(@PathVariable int gameId){
      return service.getGame(gameId);
   }

   @RequestMapping("/api/rounds/{gameId}")
   public List<Round> showAllRounds(@PathVariable int gameId){
      return service.getAllRounds(gameId);
   }

   @RequestMapping("/api/guess")
   public Round guess(@RequestBody Round round){
      return service.round(round.getGuess(), round.getGameId());
   }

}
