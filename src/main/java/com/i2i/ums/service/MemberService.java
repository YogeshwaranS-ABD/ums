package com.i2i.ums.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;

import com.i2i.ums.dto.*;

public interface MemberService {
    IdNameDto createMember(MemberDto dto);
    MemberDto getMemberDtoByUsername(String username);
    List<MemberSummaryDto> getAllMembers();
    UpdatedDto addMemberToTeamByName(String teamName, String memberName);
    UpdatedDto updateMemberRoles(String name, RolesDto dto);
    UpdatedDto updateMemberContacts(String name, ContactsUpdateDto dto);
    UpdatedDto updateAddressOfMember(String memberName, AddressDto dto);
    UserDetails getUserCredentials(String username);
}
