package ua.foxminded.pskn.universitycms.repository.university;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.foxminded.pskn.universitycms.model.university.Schedule;

import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    List<Schedule> findScheduleByGroupId(Long groupId);

    List<Schedule> findScheduleByProfessorId(Long professorId);
}
