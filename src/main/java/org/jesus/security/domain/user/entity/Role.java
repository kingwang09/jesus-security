package org.jesus.security.domain.user.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.jesus.security.support.AbstractDateAuditingEntity;
import org.jesus.security.domain.user.constant.UserRole;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="user_roles", uniqueConstraints = @UniqueConstraint(name="user_roles_uk_userid_role", columnNames = {"user_id", "role"}))
public class Role extends AbstractDateAuditingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    public void setUser(User user){
        this.user = user;
        if(this.user != null){
            this.user.addRoles(this);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Role role = (Role) o;
        return getId().equals(role.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
