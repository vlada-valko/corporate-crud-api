package ua.corporate.avgust.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.corporate.avgust.model.Department;
import ua.corporate.avgust.model.Employee;
import ua.corporate.avgust.model.Position;

import java.util.Optional;

@Repository
public interface PositionRepository extends JpaRepository<Position, Long> {
    public Optional<Position> findByName(String name);
}
