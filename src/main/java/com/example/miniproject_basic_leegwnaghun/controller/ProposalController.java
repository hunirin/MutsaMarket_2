package com.example.miniproject_basic_leegwnaghun.controller;


import com.example.miniproject_basic_leegwnaghun.dto.CommentDto;
import com.example.miniproject_basic_leegwnaghun.dto.ProposalDto;
import com.example.miniproject_basic_leegwnaghun.dto.ResponseDto;
import com.example.miniproject_basic_leegwnaghun.service.ProposalService;
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
            @PathVariable("id") Long id,
            @RequestBody ProposalDto proposalDto
    ) {
        ResponseDto response = new ResponseDto();
        service.createProposal(id, proposalDto);
        response.setMessage("구매 제안이 등록되었습니다.");
        return response;
    }

    // GET
    @GetMapping
    public Page<ProposalDto> readAll (
            @RequestParam("writer") String writer,
            @RequestParam("password") String password,
            @RequestParam(value = "pageNum", defaultValue = "0") Integer pageNum,
            @RequestParam(value = "pageSize", defaultValue = "20") Integer pageSize
    ) {
        return service.readProposalPaged(writer, password, pageNum, pageSize);
    }

    // PUT
    @PutMapping("/{proposalId}/updatePrice")
    public ResponseDto updatePrice(
            @PathVariable("proposalId") Long proposalId,
            @RequestBody ProposalDto proposalDto
    ) {
        service.updateProposal(proposalId, proposalDto.getWriter(), proposalDto.getPassword(), proposalDto);
        ResponseDto response = new ResponseDto();
        response.setMessage("제안이 수정되었습니다.");
        return response;
    }

    // DELETE
    @DeleteMapping("/{proposalId}")
    public ResponseDto delete(
            @PathVariable("proposalId") Long proposalId,
            @RequestBody ProposalDto proposalDto
    ) {
        service.deleteProposal(proposalDto.getWriter(), proposalDto.getPassword(), proposalId);
        ResponseDto response = new ResponseDto();
        response.setMessage("물품을 삭제했습니다.");
        return response;
    }

    // PUT
    // 물품 등록자 -> 수락 or 거절
    @PutMapping("/{proposalId}/updateStatus")
    public ResponseDto updateStatus(
            @PathVariable("proposalId") Long proposalId,
            @RequestBody ProposalDto proposalDto
    ) {
        service.updateProposalStatus(proposalId, proposalDto.getWriter(), proposalDto.getPassword(), proposalDto);
        ResponseDto response = new ResponseDto();
        response.setMessage("제안의 상태가 변경되었습니다.");
        return response;
    }

    // PUT
    // 제안자 -> 수락 -> 확정 / 수락아닐시 -> 실패
    @PutMapping("/{proposalId}/updateConfirm")
    public ResponseDto updateConfirm(
            @PathVariable("proposalId") Long proposalId,
            @RequestBody ProposalDto proposalDto
    ) {
        service.updateConfirm(proposalId, proposalDto.getWriter(), proposalDto.getPassword(), proposalDto);
        ResponseDto response = new ResponseDto();
        response.setMessage("구매가 확정되었습니다");

        return response;
    }
}
