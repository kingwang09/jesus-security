package org.jesus.security.domain.user.entity;

import lombok.*;
import org.jesus.security.support.AbstractDateAuditingEntity;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "users", uniqueConstraints = {@UniqueConstraint(name="user_uk", columnNames = "userName")})
public class User extends AbstractDateAuditingEntity {

    @Builder
    public User(String userName, String password){
        this.userName = userName;
        this.password = password;
        this.isDeleted = false;
        this.roles = new LinkedList<>();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userName;

    private String password;

    private Boolean isDeleted;

    @OneToMany(mappedBy = "user")
    private List<Role> roles;

    public void addRoles(Role role) {
        if(this.roles != null && !this.roles.contains(role)){
            this.roles.add(role);
        }
    }

    public void removeRoles(Role role){
        if(this.roles != null && this.roles.contains(role)){
            this.roles.remove(role);
        }
    }
}
