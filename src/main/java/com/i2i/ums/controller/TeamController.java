package com.i2i.ums.controller;

import java.util.List;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.i2i.ums.dto.IdNameDto;
import com.i2i.ums.dto.TeamDto;
import com.i2i.ums.dto.TeamSummaryDto;
import com.i2i.ums.service.TeamService;
import com.i2i.ums.service.impl.TeamServiceImpl;

@RestController
@RequestMapping(value = "/teams", produces = "application/json")
@Slf4j
public class TeamController {

    private static final Logger log = LoggerFactory.getLogger(TeamController.class);
    private final TeamService teamService;

    @Autowired
    public TeamController(TeamServiceImpl teamService) {
        this.teamService = teamService;
    }

    @GetMapping()
    public ResponseEntity<List<TeamSummaryDto>> getAllTeams() {
        log.debug("GET Request for list of teams");
        return new ResponseEntity<>(teamService.getAllTeams(), HttpStatus.OK);
    }

    @GetMapping("/{name}")
    public ResponseEntity<TeamDto> getTeamByName(@PathVariable("name") String name) {
        log.debug("GET Request to view a team with name: {}", name);
        return new ResponseEntity<>(teamService.getTeamDtoByName(name), HttpStatus.OK);
    }


    @PostMapping()
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<IdNameDto> createTeam(@RequestBody IdNameDto dto) {
        log.debug("POST Request to create a team with name: {}", dto.getName());
        return new ResponseEntity<>(teamService.createTeam(dto.getName()), HttpStatus.CREATED);
    }
}
