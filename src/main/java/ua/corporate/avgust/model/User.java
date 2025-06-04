package ua.corporate.avgust.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Immutable;
import ua.corporate.avgust.enums.Role;

import java.time.LocalDateTime;
import java.util.List;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "username", unique = true, nullable = false)
    private String username;
    @Column(name = "password", nullable = false)
    private String password;
    @OneToOne(mappedBy = "user")
    private Employee employee;
    @Column(name = "role")
    @Enumerated(value = EnumType.STRING)
    private Role role;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt; //Auto-generated
    @ManyToOne
    @JoinColumn(name = "created_by")
    private User createdBy; //Auto-generated
    @ManyToOne
    @JoinColumn(name = "updated_by")
    private User updatedBy;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "createdBy")
    List<Department> createdDepartments;

}
