package com.example.project.controller;


import com.example.project.dto.ProposalDto;
import com.example.project.dto.ResponseDto;
import com.example.project.service.ProposalService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/items/{id}/proposal")
@RequiredArgsConstructor
public class ProposalController {
    private final ProposalService service;

    // POST
    @PostMapping
    public ResponseDto create(
            @PathVariable("id") Long itemId,
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            @RequestBody ProposalDto proposalDto
    ) {
        ResponseDto response = new ResponseDto();
        service.createProposal(itemId, username, password, proposalDto);
        response.setMessage("구매 제안이 등록되었습니다.");
        return response;
    }

    // GET
    @GetMapping("/read")
    public Page<ProposalDto> readAll (
            @RequestParam(value = "pageNum", defaultValue = "0") Integer pageNum,
            @RequestParam(value = "pageSize", defaultValue = "20") Integer pageSize
    ) {
        return service.readProposalPaged(pageNum, pageSize);
    }

    // PUT
    @PutMapping("/{proposalId}/updatePrice")
    public ResponseDto updatePrice(
            @PathVariable("proposalId") Long proposalId,
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            @RequestBody ProposalDto proposalDto
    ) {
        service.updateProposal(proposalId, username, password, proposalDto);
        ResponseDto response = new ResponseDto();
        response.setMessage("제안이 수정되었습니다.");
        return response;
    }

    // DELETE
    @DeleteMapping("/{proposalId}")
    public ResponseDto delete(
            @PathVariable("proposalId") Long proposalId,
            @RequestParam("username") String username,
            @RequestParam("password") String password
    ) {
        service.deleteProposal(proposalId, username, password);
        ResponseDto response = new ResponseDto();
        response.setMessage("물품을 삭제했습니다.");
        return response;
    }

    // PUT
    // 물품 등록자 -> 수락 or 거절
    @PutMapping("/{proposalId}/updateStatus")
    public ResponseDto updateStatus(
            @PathVariable("proposalId") Long proposalId,
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            @RequestBody ProposalDto proposalDto
    ) {
        service.updateProposalStatus(proposalId, username, password, proposalDto);
        ResponseDto response = new ResponseDto();
        response.setMessage("제안의 상태가 변경되었습니다.");
        return response;
    }

    // PUT
    // 제안자 -> 수락 -> 확정 / 수락아닐시 -> 실패
    @PutMapping("/{proposalId}/updateConfirm")
    public ResponseDto updateConfirm(
            @PathVariable("proposalId") Long proposalId,
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            @RequestBody ProposalDto proposalDto
    ) {
        service.updateConfirm(proposalId, username, password,  proposalDto);
        ResponseDto response = new ResponseDto();
        response.setMessage("구매가 확정되었습니다");

        return response;
    }
}
