package ua.foxminded.pskn.universitycms.repository.university;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ua.foxminded.pskn.universitycms.model.university.StudentGroup;

@Repository
public interface StudentGroupRepository extends JpaRepository<StudentGroup, Long> {

    @Modifying
    @Query("UPDATE StudentGroup sg SET sg.groupName = :name WHERE sg.groupId = :id")
    void updateStudentGroupName(@Param("id") Long id, @Param("name") String name);
}
