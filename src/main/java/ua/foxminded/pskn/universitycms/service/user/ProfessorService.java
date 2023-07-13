package ua.foxminded.pskn.universitycms.service.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.foxminded.pskn.universitycms.model.user.Professor;
import ua.foxminded.pskn.universitycms.repository.user.ProfessorRepository;

@RequiredArgsConstructor
@Service
public class ProfessorService {
    private final ProfessorRepository professorRepository;

    public Professor getProfessorByUserId(Long userId){
        return professorRepository.getProfessorByUserId(userId);
    }
}
