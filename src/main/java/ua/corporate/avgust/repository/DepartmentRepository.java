package ua.corporate.avgust.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ua.corporate.avgust.model.Department;
import ua.corporate.avgust.model.Employee;

import java.util.List;
import java.util.Optional;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {
    Optional <Department> findByName(String name);

    @Query("SELECT e FROM Employee e WHERE e.department.id = :departmentId")
    List<Employee> getDepartmentEmployees(@Param("departmentId") Long departmentId);


}