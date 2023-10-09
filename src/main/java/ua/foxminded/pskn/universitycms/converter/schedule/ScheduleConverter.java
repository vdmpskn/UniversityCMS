package ua.foxminded.pskn.universitycms.converter.schedule;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

import ua.foxminded.pskn.universitycms.dto.ScheduleDTO;
import ua.foxminded.pskn.universitycms.model.university.Schedule;


@RequiredArgsConstructor
@Component
public class ScheduleConverter {

    private final ModelMapper modelMapper;

    public ScheduleDTO convertToDTO(Schedule schedule) {
        return modelMapper.map(schedule, ScheduleDTO.class);
    }

    public Schedule convertToEntity(ScheduleDTO scheduleDTO) {
        return modelMapper.map(scheduleDTO, Schedule.class);
    }
}
