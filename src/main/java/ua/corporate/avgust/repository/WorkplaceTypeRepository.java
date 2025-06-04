package ua.corporate.avgust.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.corporate.avgust.model.WorkplaceType;

import java.util.Optional;

@Repository
public interface WorkplaceTypeRepository extends JpaRepository<WorkplaceType, Long> {
    public Optional <WorkplaceType> findByName(String name);

}
