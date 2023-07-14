package ua.foxminded.pskn.universitycms.service.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ua.foxminded.pskn.universitycms.model.user.Professor;
import ua.foxminded.pskn.universitycms.repository.user.ProfessorRepository;

@Slf4j
@RequiredArgsConstructor
@Service
public class ProfessorService {
    private final ProfessorRepository professorRepository;

    public Professor getProfessorByUserId(Long userId) {
        log.debug("Getting professor by user ID: {}", userId);
        return professorRepository.getProfessorByUserId(userId);
    }
}
