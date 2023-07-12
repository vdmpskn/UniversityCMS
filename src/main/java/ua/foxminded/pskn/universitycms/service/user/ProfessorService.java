package ua.foxminded.pskn.universitycms.service.user;

import org.springframework.stereotype.Service;
import ua.foxminded.pskn.universitycms.model.user.Professor;
import ua.foxminded.pskn.universitycms.repository.user.ProfessorRepository;

@Service
public class ProfessorService {
    private final ProfessorRepository professorRepository;

    public ProfessorService(ProfessorRepository professorRepository) {
        this.professorRepository = professorRepository;
    }

    public Professor getProfessorByUserId(Long userId){
        return professorRepository.getProfessorByUserId(userId);
    }

}
