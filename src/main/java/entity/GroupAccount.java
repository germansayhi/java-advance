package entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@ToString
@Entity
@Table(name = "group_account")
@IdClass(value = GroupAccount.PrimaryKey.class)

public class GroupAccount {
    @Id
    private int groupId;

    @Id
    private int accountId;

    @Column(name = "join_date", nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDate joinDate;

    @Embeddable
    @Setter
    @Getter
    @ToString
    public static class PrimaryKey implements Serializable {
        @Column(name = "group_id", nullable = false)
        private int groupId;

        @Column(name = "account_id", nullable = false)
        private int accountId;
    }
}
