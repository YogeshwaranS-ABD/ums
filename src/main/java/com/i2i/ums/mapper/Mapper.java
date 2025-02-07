package com.i2i.ums.mapper;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

import com.i2i.ums.dto.TeamSummaryDto;
import com.i2i.ums.dto.MemberDto;
import com.i2i.ums.dto.MemberSummaryDto;
import com.i2i.ums.model.Member;
import com.i2i.ums.model.Team;

@Component
public class Mapper extends ModelMapper{

    public Mapper() {
        super();
        this.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        this.typeMap(Team.class, TeamSummaryDto.class)
                .addMapping(Team::getMemberCount, TeamSummaryDto::setMemberCount);
        this.typeMap(Member.class, MemberDto.class)
                .addMappings(mapper -> {
                    mapper.map(src -> src.getTeam().getName(), MemberDto::setTeamName);
                    mapper.skip(MemberDto::setPassword);
                });
        this.typeMap(MemberDto.class, Member.class)
                .addMappings(mapper -> {
                    mapper.skip(Member::setRoles);
                });
        this.typeMap(Member.class, MemberSummaryDto.class)
                .addMappings(mapper -> {
                    mapper.map(src -> src.getTeam().getName(), MemberSummaryDto::setTeamName);
                });
    }
}
