package com.example.riotapi.controllers;

import com.example.riotapi.models.Champion;
import com.example.riotapi.models.Match;
import com.example.riotapi.repositories.MatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class Matches {

    @Autowired
    MatchRepository matches;

    @GetMapping("/matches")
    public Iterable<Match> getMatches(){
        return matches.findAll();
    }

    @GetMapping("/matches/{id}")
    public Match getMatchesById(@PathVariable Long id){
        return matches.findById(id).get();
    }

    @PostMapping("/matches")
    public Match addMatches(@RequestBody Match newMatch){
        newMatch.setId(null);
        return matches.save(newMatch);
    }

    @PutMapping("/matches/{id}")
    public String updateMatchById(@PathVariable Long id, @RequestBody Match matchToUpdateWith){
        if (matches.existsById(id)) {
            matchToUpdateWith.setId(id);
            matches.save(matchToUpdateWith);
            return "Match was created";
        } else {
            return "Match not found";
        }
    }

    @PatchMapping("/matches/{id}")
    public String patchMatchesById(@PathVariable Long id, @RequestBody Match matchToUpdateWith) {
        return matches.findById(id).map(foundMatch -> {
            if (matchToUpdateWith.getGameType() != null) foundMatch.setGameType(matchToUpdateWith.getGameType());
            if (matchToUpdateWith.getDuration() != 0) foundMatch.setDuration(matchToUpdateWith.getDuration());
            if (matchToUpdateWith.getStartDate() != null) foundMatch.setStartDate(matchToUpdateWith.getStartDate());
            if (matchToUpdateWith.getEndDate() != null) foundMatch.setEndDate(matchToUpdateWith.getEndDate());



            // champions.save(foundChampion);
            return "Match updated";
        }).orElse("Match not found");
    }


    @DeleteMapping("/matches/{id}")
    public void deleteMatchById(@PathVariable Long id){
        matches.deleteById(id);
    }
}
