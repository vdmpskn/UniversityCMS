package ua.foxminded.pskn.universitycms.service.university;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.foxminded.pskn.universitycms.model.university.Schedule;
import ua.foxminded.pskn.universitycms.repository.university.ScheduleRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;

    public List<Schedule> getScheduleById(Long groupId){
        return scheduleRepository.findScheduleByGroupId(groupId);
    }

    public List<Schedule> getScheduleByProfessorId(int professorId){
        return scheduleRepository.findScheduleByProfessorId(professorId);
    }

    public List<Schedule> findAll(){
        return scheduleRepository.findAll();
    }
}
