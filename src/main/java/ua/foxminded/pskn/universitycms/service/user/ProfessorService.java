package ua.foxminded.pskn.universitycms.service.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ua.foxminded.pskn.universitycms.model.user.Professor;
import ua.foxminded.pskn.universitycms.repository.user.ProfessorRepository;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class ProfessorService {
    private final ProfessorRepository professorRepository;

    public Professor getProfessorByUserId(Long userId) {
        log.debug("Getting professor by user ID: {}", userId);
        return professorRepository.getProfessorByUserId(userId);
    }

    public List<Professor> getAllProfessors(){
        log.debug("Retrieving all professors");
        return professorRepository.findAll();
    }

    public Page<Professor> getAllProfessors(Pageable pageable){
        log.debug("Retrieving all professors with page number: {} and page size: {}", pageable.getPageNumber(), pageable.getPageSize());
        return professorRepository.findAll(pageable);
    }
}
