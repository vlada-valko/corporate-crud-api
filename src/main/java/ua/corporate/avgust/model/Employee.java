package ua.corporate.avgust.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import ua.corporate.avgust.enums.Gender;
import ua.corporate.avgust.enums.Role;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Entity
@Table(name = "employee")
public class Employee {
    //region IDENTITY AND AUDIT FIELDS
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
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
    //endregion
    //region NAME
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "middle_name")
    private String middleName;
    //endregion
    //region WORK_INFO
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id")
    private Department department;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manager_id")
    private Employee manager;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "position_id", nullable = true)
    private Position position;
    @Column(name = "employment_start_date")
    private LocalDate employmentStartDate;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "workplace_type_id")
    private WorkplaceType workplaceType;
    //endregion
    //region CONTACTS
    @Column(name = "corporate_mobile")
    private String corporateMobile;
    @Column(name = "personal_mobile")
    private String personalMobile;
    @Column(name = "internal_phone")
    private String internalPhone;
    @Column(name = "personal_email")
    private String personalEmail;
    @Column(name = "work_email")
    private String workEmail;
    @Column(name = "residential_address")
    private String residentialAddress;
    //endregion

    @Column(name = "gender")
    @Enumerated(value = EnumType.STRING)
    private Gender gender;
    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;
    @Column(name = "photo")
    private byte[] photo;

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", user=" + user.getUsername() +
                ", createdBy=" + createdBy.getUsername() +
                ", createdAt=" + createdAt +
                ", updatedBy=" + updatedBy.getUsername() +
                ", updatedAt=" + updatedAt +
                ", lastName='" + lastName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", department=" + department.getName() +
                ", manager=" + String.format("%s %s %s",
                manager.getFirstName(), manager.getLastName(), manager.getMiddleName()) +
                ", position=" + position.getName() +
                ", employmentStartDate=" + employmentStartDate +
                ", workplaceType=" + workplaceType.getName() +
                ", corporateMobile='" + corporateMobile + '\'' +
                ", personalMobile='" + personalMobile + '\'' +
                ", internalPhone='" + internalPhone + '\'' +
                ", personalEmail='" + personalEmail + '\'' +
                ", workEmail='" + workEmail + '\'' +
                ", residentialAddress='" + residentialAddress + '\'' +
                ", gender=" + gender +
                ", dateOfBirth=" + dateOfBirth +
                ", photo=" + Arrays.toString(photo) +
                '}';
    }
}
