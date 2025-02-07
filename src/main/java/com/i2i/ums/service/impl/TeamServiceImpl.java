package com.i2i.ums.service.impl;

import java.util.List;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.i2i.ums.dto.IdNameDto;
import com.i2i.ums.dto.TeamDto;
import com.i2i.ums.dto.TeamSummaryDto;
import com.i2i.ums.exception.TeamNotFoundException;
import com.i2i.ums.mapper.Mapper;
import com.i2i.ums.mapper.TeamMapper;
import com.i2i.ums.model.Team;
import com.i2i.ums.repository.TeamRepository;
import com.i2i.ums.service.TeamService;

@Service
@Slf4j
public class TeamServiceImpl implements TeamService {

    private static final Logger log = LoggerFactory.getLogger(TeamServiceImpl.class);
    private final TeamRepository teamRepository;
    private final Mapper mapper;

    @Autowired
    public TeamServiceImpl(TeamRepository teamRepository,
                           Mapper mapper) {
        this.teamRepository = teamRepository;
        this.mapper = mapper;
    }

    @Override
    public IdNameDto createTeam(String name) {
        Team team = new Team();
        team.setName(name.toUpperCase());
        team = teamRepository.save(team);
        log.info("New Team({}) created Successfully", team.getName());
        return new IdNameDto(team.getId(), team.getName());
    }

    @Override
    public boolean isTeamWithNameActive(String name) {
        return teamRepository.existsByName(name);
    }

    @Override
    public Team getTeamByName(String name) {
        Team team = teamRepository.findByNameIgnoreCase(name.toUpperCase());
        if (team.isNotActive()) {
            log.error("Cannot fetch a Non-Active team");
            throw new TeamNotFoundException("The requested team is no longer active");
        }
        log.info("Team: {}, fetched Successfully", team.getName());
        return team;
    }

    @Override
    public TeamDto getTeamDtoByName(String name) {
        Team team = teamRepository.findByNameIgnoreCase(name);
        if(ObjectUtils.isEmpty(team)){
            log.error("Cannot fetch a Non-Existing team");
            throw new TeamNotFoundException("No Team Found with the Given name");
        }else if(team.isNotActive()) {
            log.error("Cannot fetch the DTO of Non-Active team");
            throw new TeamNotFoundException("The requested team is no longer active");
        }
        log.info("DTO of Team: {} fetched successfully", team.getName());
        return mapper.map(team, TeamDto.class);
    }

    @Override
    public List<TeamSummaryDto> getAllTeams() {
        List<Team> teams = teamRepository.findAll();
        log.info("List of all Teams fetched Successfully");
        return teams.stream()
                .map(team -> mapper.map(team, TeamSummaryDto.class))
                .toList();
    }

    @Override
    public void deleteTeamByName(String teamName) {
        Team team = getTeamByName(teamName);
        team.setActive(false);
        teamRepository.save(team);
        log.info("Team: {} - Marked as Inactive", team.getName());
    }
}

//    public Team getTeamById(String id) {
//        Optional<Team> team = teamRepository.findById(id);
//        if(team.isEmpty()) {
//            throw new TeamNotFoundException("No Team Found with ID: " + id);
//        }
//        return team.get();
//    }
