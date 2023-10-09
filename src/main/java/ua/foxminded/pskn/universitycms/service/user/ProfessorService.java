package ua.foxminded.pskn.universitycms.service.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import ua.foxminded.pskn.universitycms.converter.professor.ProfessorConverter;
import ua.foxminded.pskn.universitycms.dto.ProfessorDTO;
import ua.foxminded.pskn.universitycms.model.user.Professor;
import ua.foxminded.pskn.universitycms.repository.user.ProfessorRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class ProfessorService {
    private final ProfessorRepository professorRepository;

    private final ProfessorConverter professorConverter;

    public Optional<Professor> getProfessorByUserId(Long userId) {
        log.debug("Getting professor by user ID: {}", userId);
        return professorRepository.getProfessorByUserId(userId);
    }

    public List<ProfessorDTO> getAllProfessors() {
        log.debug("Retrieving all professors");

        return professorRepository.findAll()
            .stream()
            .map(professorConverter::convertToDTO)
            .collect(Collectors.toList());
    }

    public Page<ProfessorDTO> getAllProfessors(Pageable pageable) {
        log.debug("Retrieving all professors with page number: {} and page size: {}", pageable.getPageNumber(), pageable.getPageSize());
        Page<Professor> professorPage = professorRepository.findAll(pageable);
        return professorPage.map(professorConverter::convertToDTO);
    }
}
