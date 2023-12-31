package com.example.project.service;

import com.example.project.dto.ProposalDto;
import com.example.project.entity.ProposalEntity;
import com.example.project.entity.UserEntity;
import com.example.project.exceptions.IncorrectPasswordException;
import com.example.project.repository.CommentRepository;
import com.example.project.repository.ItemRepository;
import com.example.project.repository.ProposalRepository;
import com.example.project.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProposalService {
    private final ItemRepository itemRepository;
    private final CommentRepository commentRepository;
    private final ProposalRepository proposalRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // POST
    public ProposalDto createProposal(Long itemId, String username, String password, ProposalDto proposalDto) {
        Optional<UserEntity> user = userRepository.findByUsername(username);

        if (user.isPresent()) {
            UserEntity userEntity = user.get();
            String storedPw = userEntity.getPassword();
            String storedId = userEntity.getUsername();
            log.info(storedId);
            log.info(storedPw);

            if (passwordEncoder.matches(password, storedPw)) {

                if (!itemRepository.existsById(itemId))
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND);

                ProposalEntity newProposal = new ProposalEntity();
                newProposal.setUsername(storedId);
                newProposal.setSuggestedPrice(proposalDto.getSuggestedPrice());
                newProposal.setStatus("제안");
                newProposal = proposalRepository.save(newProposal);
                return ProposalDto.fromEntity(newProposal);
            } else {
                throw new IncorrectPasswordException();
            }
        } else {
            throw new UsernameNotFoundException(username);
        }
    }

    // GET
    // Page 단위로 조회
    public Page<ProposalDto> readProposalPaged(
             Integer pageNum, Integer pageSize
    ) {
            Pageable pageable = PageRequest.of(
                    pageNum, pageSize, Sort.by("proposalId").ascending());
            Page<ProposalEntity> proposalEntityPage = proposalRepository.findAll(pageable);
            Page<ProposalDto> proposalDtoPage = proposalEntityPage.map(ProposalDto::fromEntity);
            return proposalDtoPage;
    }

    // PUT
    // 제안 수정
    public ProposalDto updateProposal(
            Long proposalId,
            String username,
            String password,
            ProposalDto proposalDto
    ) {
        Optional<ProposalEntity> optionalProposal
                = proposalRepository.findById(proposalId);

        ProposalEntity proposal = optionalProposal.get();

        Optional<UserEntity> user = userRepository.findByUsername(username);

        if (user.isPresent()) {
            UserEntity userEntity = user.get();
            String storedPw = userEntity.getPassword();
            String storedId = userEntity.getUsername();
            log.info(storedId);
            log.info(storedPw);

            if (passwordEncoder.matches(password, storedPw)) {
                if (optionalProposal.isEmpty())
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND);

                proposal.setSuggestedPrice(proposalDto.getSuggestedPrice());
                return ProposalDto.fromEntity(proposalRepository.save(proposal));
            } else {
                throw new IncorrectPasswordException();
            }
        } else {
            throw new UsernameNotFoundException(username);
        }
    }

    // DELETE
    public void deleteProposal(Long proposalId, String username, String password) {
        Optional<ProposalEntity> optionalProposal
                = proposalRepository.findById(proposalId);

        if (optionalProposal.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        Optional<UserEntity> user = userRepository.findByUsername(username);

        if (user.isPresent()) {
            UserEntity userEntity = user.get();
            String storedPw = userEntity.getPassword();
            String storedId = userEntity.getUsername();
            log.info(storedId);
            log.info(storedPw);

            if (passwordEncoder.matches(password, storedPw)) {
                proposalRepository.deleteById(proposalId);
            } else {
                throw new IncorrectPasswordException();
            }
        } else {
            throw new UsernameNotFoundException(username);
        }
    }

    // PUT
    // 물품 등록자 -> 수락 or 거절
    public ProposalDto updateProposalStatus (
            Long proposalId,
            String username,
            String password,
            ProposalDto proposalDto

    ) {
        Optional<ProposalEntity> optionalProposal
                = proposalRepository.findById(proposalId);
        ProposalEntity proposal = optionalProposal.get();

        Optional<UserEntity> user = userRepository.findByUsername(username);

        if (user.isPresent()) {
            UserEntity userEntity = user.get();
            String storedPw = userEntity.getPassword();
            String storedId = userEntity.getUsername();
            log.info(storedId);
            log.info(storedPw);

            if (passwordEncoder.matches(password, storedPw)) {

                if (optionalProposal.isEmpty())
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND);

                proposal.setStatus(proposalDto.getStatus());
                return ProposalDto.fromEntity(proposalRepository.save(proposal));
            } else {
                throw new IncorrectPasswordException();
            }
        } else {
            throw new UsernameNotFoundException(username);
        }
    }

    // PUT
    // 제안자의 구매확정
    public ProposalDto updateConfirm(
            Long proposalId,
            String username,
            String password,
            ProposalDto proposalDto
    ) {
        Optional<ProposalEntity> optionalProposal
                = proposalRepository.findById(proposalId);
        ProposalEntity proposal = optionalProposal.get();

        Optional<UserEntity> user = userRepository.findByUsername(username);

        if (user.isPresent()) {
            UserEntity userEntity = user.get();
            String storedPw = userEntity.getPassword();
            String storedId = userEntity.getUsername();
            log.info(storedId);
            log.info(storedPw);

            if (passwordEncoder.matches(password, storedPw)) {
                if (optionalProposal.isEmpty())
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND);
                if (proposal.getStatus().equals("수락")) {
                    proposal.setStatus("판매 완료");
                    return ProposalDto.fromEntity(proposalRepository.save(proposal));
                } else throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            } else {
                throw new IncorrectPasswordException();
            }
        } else {
            throw new UsernameNotFoundException(username);
        }
    }
}
