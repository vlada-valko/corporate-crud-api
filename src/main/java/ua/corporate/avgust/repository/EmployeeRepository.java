package ua.corporate.avgust.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ua.corporate.avgust.model.Employee;
import ua.corporate.avgust.model.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Optional<Employee> findByFirstNameAndMiddleNameAndLastName(String firstName,
                                      String middleName,
                                      String lastName);

    Optional<Employee> findByUser(User user);

    @Query("SELECT e FROM Employee e WHERE e.position.name LIKE '%керівник%'")
    List<Employee> findManagers();
}
