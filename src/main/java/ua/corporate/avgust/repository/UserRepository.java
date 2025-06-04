package ua.corporate.avgust.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ua.corporate.avgust.model.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    public Optional <User> findByUsername(String username);
    @Query("SELECT u FROM User u WHERE u.employee IS NULL")
    List<User> findUsersWithoutEmployee();
}
