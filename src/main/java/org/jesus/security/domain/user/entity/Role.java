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

    /**
     * HashSet 중복 제거를 위해 만들었는데
     *  - Role을 먼저 검사해야 신규 권한 추가할 때 NPE가 발생하지 않는다.
     *  - (신규 Role은 Id가 null이기 때문)
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Role role1 = (Role) o;
        return  getRole() == role1.getRole() && Objects.equals(getId(), role1.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getRole());
    }
}
