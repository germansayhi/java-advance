package entity;

import convertor.DepartmentTypeConverter;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.JdbcType;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.type.descriptor.jdbc.CharJdbcType;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@ToString
@Entity
@Table(name = "department")
public class Department {
    @Id
    @Column(name = "id")
    @GenericGenerator(
            name ="department_id_gerator",
            strategy = "generator.DepartmentIdGenerator"
    )
    @GeneratedValue (generator = "department_id_gerator")
    private String id;

    @Column(name = "name", length = 50, unique = true, nullable = false)
    private String name;

    @Column(name = "type", nullable = false)
    @Convert(converter = DepartmentTypeConverter.class)
    private Type type;

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        System.out.println("Trước khi thêm vào database");
    }

    @PostPersist
    public void postPersist() {
        System.out.println("Sau khi thêm vào database");
    }

    public enum Type {
        DEVELOPER, TESTER, SCRUM_MASTER, PROJECT_MANAGER
    }
}



