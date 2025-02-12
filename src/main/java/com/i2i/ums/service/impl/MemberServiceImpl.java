package com.i2i.ums.service.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.opencsv.bean.CsvToBeanBuilder;
import jakarta.validation.*;
import jakarta.validation.groups.Default;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.multipart.MultipartFile;

import com.i2i.ums.annotations.CustomCheckGroups;
import com.i2i.ums.dto.*;
import com.i2i.ums.exception.ConstrainViolationException;
import com.i2i.ums.exception.InvalidContactException;
import com.i2i.ums.exception.UmsException;
import com.i2i.ums.exception.UserNotFoundException;
import com.i2i.ums.mapper.Mapper;
import com.i2i.ums.model.*;
import com.i2i.ums.repository.MemberRepository;
import com.i2i.ums.service.ContactService;
import com.i2i.ums.service.MemberService;
import com.i2i.ums.service.RoleService;
import com.i2i.ums.service.TeamService;
import com.i2i.ums.utils.Util;

@Service
public class MemberServiceImpl implements MemberService {

    private static final Logger log = LoggerFactory.getLogger(MemberServiceImpl.class);
    private final MemberRepository memberRepository;
    private final RoleService roleService;
    private final TeamService teamService;
    private final ContactService contactService;
    private final Mapper mapper;


    @Autowired
    public MemberServiceImpl(MemberRepository memberRepository,
                             RoleService roleService,
                             TeamService teamService,
                             ContactService contactService,
                             Mapper mapper) {
        this.memberRepository = memberRepository;
        this.roleService = roleService;
        this.teamService = teamService;
        this.contactService = contactService;
        this.mapper = mapper;
    }

    @Override
    public IdNameDto createMember(MemberDto dto) {
//        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
//        Set<ConstraintViolation<MemberDto>> constraintViolations = validator.validate(dto, CustomCheckGroups.class);
//        if (!constraintViolations.isEmpty()) {
//            List<String> violations = new ArrayList<>();
//            constraintViolations.forEach(constraintViolation -> {
//                violations.add(constraintViolation.getMessage());
//            });
//            throw new UmsException(violations.toString());
//        }
        Member member = mapper.map(dto, Member.class);
        Member finalMember = member;
        member.getAddress().setMember(member);
        member.getContacts().forEach(contact -> contact.setMember(finalMember));
        log.debug("DTO Converted to POJO Successfully");
        member.setTeam(teamService.getTeamByName(dto.getTeamName()));
        Set<Role> roles = dto.getRoles()
                .stream()
                .map(roleService::getRoleByName)
                .collect(Collectors.toSet());
        roles.forEach(role -> role.getMembers().add(finalMember));
        member.setRoles(roles);
        member = memberRepository.save(member);
        log.info("New Member created with given Roles Successfully");
        return new IdNameDto(member.getId(), member.getName());
    }

    @Override
    public MemberDto getMemberDtoByUsername(String username) {
        Member member = getMemberByUsername(username);
        MemberDto dto = mapper.map(member, MemberDto.class);
        dto.setAddress(null);
        log.info("DTO of the member with Username fetched successfully");
        return dto;
    }

    @Override
    public MemberDto getProfileByUsername(String username) {
        Member member = getMemberByUsername(username);
        MemberDto dto = mapper.map(member, MemberDto.class);
        log.info("Profile of the member with Username fetched successfully");
        return dto;
    }

    @Override
    public List<MemberSummaryDto> getAllMembers() {
        log.info("List of all members displayed successfully");
        return memberRepository.findAll()
                .stream()
                .map(dto -> mapper.map(dto, MemberSummaryDto.class))
                .toList();
    }

    @Override
    public UpdatedDto addMemberToTeamByName(String teamName, String memberName) {
        Team team = teamService.getTeamByName(teamName);
        Member member = getMemberByUsername(memberName);
        member.setTeam(team);
        memberRepository.save(member);
        log.info("Member with username: {}, transferred to another team successfully.", teamName);
        return new UpdatedDto("Success", "Member added to team.");
    }

    @Override
    public UpdatedDto updateMemberRoles(String username, RolesDto dto) {
        Member member = getMemberByUsername(username);
        if (dto.getAction().equalsIgnoreCase("add")) {
            dto.getRoles().stream()
                    .map(roleService::getRoleByName)
                    .forEach(member::addRole);
            log.info("The given roles were added to the member: {}", username);
        } else {
            dto.getRoles().stream()
                    .map(roleService::getRoleByName)
                    .forEach(member::removeRole);
            log.info("The given roles were removed from the member: {}", username);
        }
        memberRepository.save(member);
        return new UpdatedDto("Success", "Roles updated for member successfully");
    }

    @Override
    public UpdatedDto updateMemberContacts(String name, ContactsUpdateDto dto) {
        Member member = getMemberByUsername(name);
        if (dto.getAction().equalsIgnoreCase("add")) {
            dto.getContacts().stream()
                    .map(contactDto -> mapper.map(contactDto, Contact.class))
                    .forEach(member::addContact);
            memberRepository.save(member);
            log.info("The given contacts were added to the member: {} successfully", name);
        } else {
            dto.getContacts().stream()
                    .map(ContactDto::getValue)
                    .forEach(contactService::removeContactByValue);
            log.info("The given contacts were removed from the member: {} successfully", name);
        }
        return new UpdatedDto("Success", "Contacts updated for Member successfully");
    }

    @Override
    public UpdatedDto updateAddressOfMember(String memberName, AddressDto dto) {
        Member member = getMemberByUsername(memberName);
        Address address = member.getAddress();
        Address newAddress = mapper.map(dto, Address.class);
        newAddress.setId(address.getId());
        newAddress.setMember(member);
        memberRepository.save(member);
        log.info("The address of the member: {}, updated successfully", memberName);
        return new UpdatedDto("Success", "Address Changed for Member successfully");
    }

    @Override
    public UserDetails getUserCredentials(String username) {
        Member member = getMemberByUsername(username);
        return User.builder()
                .username(member.getUsername())
                .password(member.getPassword())
                .authorities(getAuthorities(member.getRoles()))
                .build();
    }

    private Member getMemberByUsername(String username) {
        Member member = memberRepository.findByUsernameIgnoreCase(username);
        if (ObjectUtils.isEmpty(member)) {
            log.error("Couldn't Find a member with the given username: {}", username);
            throw new UserNotFoundException("Member Not found with given Username.");
        }
        log.debug("Member with username: {} fetched successfully", username);
        return member;
    }

    private List<SimpleGrantedAuthority> getAuthorities(Set<Role> roles) {
        return roles.stream()
                .map(Role::getName)
                .map(SimpleGrantedAuthority::new)
                .toList();
    }

    @Override
    public List<IdNameDto> createMemberWithFile(MultipartFile file) {
        try {
            String filePath = Util.writeFile(file);
            File newFile = new File(filePath);
            List<MemberDto> dtoList = new CsvToBeanBuilder<MemberDto>(new FileReader(newFile))
                    .withType(MemberDto.class)
                    .build()
                    .parse();
            validateAfterReadFromCSV(dtoList);
            List<IdNameDto> idNameDtoList = new ArrayList<>();
            dtoList.forEach(member -> {
                idNameDtoList.add(createMember(member));
            });
            return idNameDtoList;
        } catch (FileNotFoundException e) {
            throw new UmsException("File Not Found. Please Upload again");
        }
    }


    private void validateAfterReadFromCSV(List<MemberDto> dtoList){
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            Validator validator = factory.getValidator();
            List<List<String>> violations = new ArrayList<>();
            for(MemberDto member: dtoList) {
                Set<ConstraintViolation<MemberDto>> violation = validator.validate(member, Default.class);
                if (!ObjectUtils.isEmpty(violation)) {
                    violations.add(Util.getMessages(violation));
                }
                violation = validator.validate(member, CustomCheckGroups.class);
                if (!ObjectUtils.isEmpty(violation)) {
                    violations.add(Util.getMessages(violation));
                }
            }
            if (!violations.isEmpty()) {
                throw new ConstrainViolationException(violations);
            }
        }
    }
}
