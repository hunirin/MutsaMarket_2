package com.example.project.service;

import com.example.project.dto.ProposalDto;
import com.example.project.entity.CommentEntity;
import com.example.project.entity.ItemEntity;
import com.example.project.entity.ProposalEntity;
import com.example.project.exceptions.IncorrectPasswordException;
import com.example.project.repository.CommentRepository;
import com.example.project.repository.ItemRepository;
import com.example.project.repository.ProposalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProposalService {
    private final ItemRepository itemRepository;
    private final CommentRepository commentRepository;
    private final ProposalRepository proposalRepository;

    // POST
    public ProposalDto createProposal(Long itemId, ProposalDto proposalDto) {
        if (!itemRepository.existsById(itemId))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        ProposalEntity newProposal = new ProposalEntity();
        newProposal.setWriter(proposalDto.getWriter());
        newProposal.setPassword(proposalDto.getPassword());
        newProposal.setSuggestedPrice(proposalDto.getSuggestedPrice());
        newProposal.setStatus("제안");
        newProposal = proposalRepository.save(newProposal);
        return ProposalDto.fromEntity(newProposal);
    }

    // GET
    // Page 단위로 조회
    public Page<ProposalDto> readProposalPaged(
            String writer, String password, Integer pageNum, Integer pageSize
    ) {
        ItemEntity itemEntity = itemRepository.findByWriterAndAndPassword(writer, password);
        CommentEntity commentEntity = commentRepository.findByWriterAndAndPassword(writer, password);

        if ((itemEntity != null && itemEntity.getUser().getPassword().equals(password))
                || (commentEntity != null && commentEntity.getPassword().equals(password))) {

            Pageable pageable = PageRequest.of(
                    pageNum, pageSize, Sort.by("proposalId").ascending());
            Page<ProposalEntity> proposalEntityPage = proposalRepository.findAll(pageable);
            Page<ProposalDto> proposalDtoPage = proposalEntityPage.map(ProposalDto::fromEntity);
            return proposalDtoPage;
        } else throw new IncorrectPasswordException();
    }

    // PUT
    // 제안 수정
    public ProposalDto updateProposal(
            Long proposalId,
            String writer,
            String password,
            ProposalDto proposalDto
    ) {
        Optional<ProposalEntity> optionalProposal
                = proposalRepository.findById(proposalId);
        ProposalEntity proposal = optionalProposal.get();
        String storedWr = proposal.getWriter();
        String storedPw = proposal.getPassword();

        if (password.equals(storedPw) && writer.equals(storedWr)) {
            if (optionalProposal.isEmpty())
                throw new ResponseStatusException(HttpStatus.NOT_FOUND);

            proposal.setSuggestedPrice(proposalDto.getSuggestedPrice());
            return ProposalDto.fromEntity(proposalRepository.save(proposal));
        } else throw new IncorrectPasswordException();
    }

    // DELETE
    public void deleteProposal(String writer, String password, Long proposalId) {
        Optional<ProposalEntity> optionalProposal
                = proposalRepository.findById(proposalId);
        ProposalEntity proposal = optionalProposal.get();
        String storedWr = proposal.getWriter();
        String storedPw = proposal.getPassword();

        if (password.equals(storedPw) && writer.equals(storedWr)) {
            if (optionalProposal.isEmpty())
                throw new ResponseStatusException(HttpStatus.NOT_FOUND);

            proposalRepository.deleteById(proposalId);
        } else throw new IncorrectPasswordException();
    }

    // PUT
    // 물품 등록자 -> 수락 or 거절
    public ProposalDto updateProposalStatus (
            Long proposalId,
            String writer,
            String password,
            ProposalDto proposalDto

    ) {
        Optional<ProposalEntity> optionalProposal
                = proposalRepository.findById(proposalId);
        ProposalEntity proposal = optionalProposal.get();

        ItemEntity itemEntity = itemRepository.findByWriterAndAndPassword(writer, password);

        if (itemEntity != null && itemEntity.getUser().getPassword().equals(password)) {
            if (optionalProposal.isEmpty())
                throw new ResponseStatusException(HttpStatus.NOT_FOUND);

            proposal.setStatus(proposalDto.getStatus());
            return ProposalDto.fromEntity(proposalRepository.save(proposal));
        } else throw new IncorrectPasswordException();
    }

    // PUT
    // 제안자의 구매확정
    public ProposalDto updateConfirm(
            Long proposalId,
            String writer,
            String password,
            ProposalDto proposalDto
    ) {
        Optional<ProposalEntity> optionalProposal
                = proposalRepository.findById(proposalId);
        ProposalEntity proposal = optionalProposal.get();
        String storedWr = proposal.getWriter();
        String storedPw = proposal.getPassword();

        if (password.equals(storedPw) && writer.equals(storedWr)) {
            if (optionalProposal.isEmpty())
                throw new ResponseStatusException(HttpStatus.NOT_FOUND);
            if (proposal.getStatus().equals("수락")) {
                proposal.setStatus("판매 완료");
                return ProposalDto.fromEntity(proposalRepository.save(proposal));
            } else throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        } else throw new IncorrectPasswordException();
    }
}
