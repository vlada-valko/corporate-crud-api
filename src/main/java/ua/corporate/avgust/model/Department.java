package ua.corporate.avgust.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.security.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Entity
@Table(name = "departments")
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", unique = true)
    private String name;

    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Employee> employees;

    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Position> positions;

    @ManyToOne
    @JoinColumn(name = "manager_id")
    private Employee manager;

    @ManyToOne
    @JoinColumn(name = "created_by")
    private User createdBy;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "updated_by")
    private User updatedBy;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Override
    public String toString() {
        return "Department{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", employees=" +
                employees.stream().map(e -> e.getFirstName()
                                + " " + e.getLastName()
                                + " " + e.getMiddleName())
                        .collect(Collectors.joining(", "))
                +
                ", positions=" + positions.stream().map(p -> p.getName())
                .collect(Collectors.joining(", ")) +
                ", manager=" + String.format("%s %s %s",
                manager.getFirstName(), manager.getLastName(), manager.getMiddleName()) +
                ", createdBy=" + createdBy.getUsername() +
                ", createdAt=" + createdAt +
                ", updatedBy=" + updatedBy.getUsername() +
                ", updatedAt=" + updatedAt +
                '}';
    }
}

