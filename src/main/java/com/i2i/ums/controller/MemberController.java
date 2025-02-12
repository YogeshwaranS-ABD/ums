package com.i2i.ums.controller;

import java.util.ArrayList;
import java.util.List;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.i2i.ums.annotations.CustomCheckGroups;
import com.i2i.ums.annotations.ValidContact;
import com.i2i.ums.dto.*;
import com.i2i.ums.service.MemberService;

@RestController
@RequestMapping(value = "/users", produces = "application/json")
@Slf4j
public class MemberController {

    private static final Logger log = LoggerFactory.getLogger(MemberController.class);
    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping()
    public ResponseEntity<List<MemberSummaryDto>> getAllUsers() {
        log.debug("GET Request for list all users");
        return new ResponseEntity<>(memberService.getAllMembers(), HttpStatus.OK);
    }

    @PostMapping()
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<IdNameDto> createUser(@Valid @Validated(CustomCheckGroups.class) @RequestBody MemberDto dto) {
        log.debug("POST Request to create a new Member");
        return new ResponseEntity<>(memberService.createMember(dto), HttpStatus.CREATED);
    }

    @PostMapping("/file")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<IdNameDto>> createUsersWithUploadedFile(@RequestParam("file")MultipartFile file) {
        log.debug("POST Request with a file to create set of users");

        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.CREATED);
    }

    @GetMapping("/me")
    public ResponseEntity<MemberDto> getMyProfile(@RequestAttribute("username") String username) {
        log.debug("GET Request to view the user's details");
        return new ResponseEntity<>(memberService.getProfileByUsername(username), HttpStatus.OK);
    }

    @GetMapping("/{username}")
    public ResponseEntity<MemberDto> getMemberByUsername(@PathVariable("username") String username) {
        log.debug("GET Request to view the Member: {}'s details", username);
        return new ResponseEntity<>(memberService.getMemberDtoByUsername(username), HttpStatus.OK);
    }

    @PatchMapping("/{username}/team")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<UpdatedDto> transferUserToTeam(@PathVariable("username") String username,
                                                         @RequestBody IdNameDto dto) {
        log.debug("PATCH Request to change Member({}) from one team to another", username);
        return new ResponseEntity<>(memberService.addMemberToTeamByName(dto.getName(), username), HttpStatus.OK);
    }

    @PatchMapping("/{username}/roles")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<UpdatedDto> updateUserRoles(@PathVariable("username") String username,
                                                      @RequestBody RolesDto dto) {
        log.debug("PATCH Request to update Member({}) Roles", username);
        return new ResponseEntity<>(memberService.updateMemberRoles(username, dto), HttpStatus.OK);
    }

    @PatchMapping("/me/contacts")
    public ResponseEntity<UpdatedDto> updateUserContacts(@RequestAttribute("username") String username,
                                                         @RequestBody ContactsUpdateDto dto) {
        log.debug("PATCH Request to update Member({}) Contacts", username);
        return new ResponseEntity<>(memberService.updateMemberContacts(username, dto), HttpStatus.OK);
    }

    @PutMapping("/me/address")
    @ResponseStatus(HttpStatus.OK)
    public UpdatedDto updateUserAddress(@RequestAttribute("username") String username,
                                        @RequestBody AddressDto addressDto) {
        log.debug("PUT Request to update Member({}) address", username);
        return memberService.updateAddressOfMember(username, addressDto);
    }

    @PostMapping("/csv-file")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public List<IdNameDto> createUsersWithFile(@RequestParam("file") MultipartFile file) {
        log.debug("POST request to create a set of user with given csv file");
        return memberService.createMemberWithFile(file);
    }
}
