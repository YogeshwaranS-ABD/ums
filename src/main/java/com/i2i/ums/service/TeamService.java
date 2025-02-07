package com.i2i.ums.service;

import java.util.List;

import com.i2i.ums.dto.IdNameDto;
import com.i2i.ums.dto.TeamDto;
import com.i2i.ums.dto.TeamSummaryDto;
import com.i2i.ums.model.Team;

public interface TeamService {
    IdNameDto createTeam(String name);
    boolean isTeamWithNameActive(String name);
    Team getTeamByName(String name);
    TeamDto getTeamDtoByName(String name);
    List<TeamSummaryDto> getAllTeams();
    void deleteTeamByName(String name);
}
