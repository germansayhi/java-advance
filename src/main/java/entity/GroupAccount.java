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
public class GroupAccount {
    @EmbeddedId
    private PrimaryKey pk;

    @ManyToOne
    @JoinColumn(name = "group_id",referencedColumnName = "id", nullable = false)
    @MapsId("group_id")
    private Group group;

    @ManyToOne
    @JoinColumn(name = "account_id",referencedColumnName = "id", nullable = false)
    @MapsId("account_id")
    private Account account;

    @Column(name = "joined_at",nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDate joinedAt;

    @Embeddable
    @Setter
    @Getter
    public static class PrimaryKey implements Serializable {
        @Column(name = "group_id", nullable = false)
        private int groupId;

        @Column(name = "account_id", nullable = false)
        private int accountId;
    }
}
