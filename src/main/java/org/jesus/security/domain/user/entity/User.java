package org.jesus.security.domain.user.entity;

import lombok.*;
import org.jesus.security.support.AbstractDateAuditingEntity;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

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
        this.roles = new LinkedHashSet<>();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userName;

    private String password;

    private Boolean isDeleted;

    @OneToMany(mappedBy = "user")//cascade를 먹이는게 더 편할 것 같긴한데 JPA를 좀더 공부해야함.
    private Set<Role> roles;

    public void addRoles(Role role) {
        if(this.roles != null && !this.roles.contains(role)){
            this.roles.add(role);
        }
    }
    public void addRoles(Set<Role> roles) {
        if(this.roles == null){
            this.roles = new LinkedHashSet<>();
        }
        this.roles.addAll(roles);
    }

    public void removeRoles(Role role){
        if(this.roles != null && this.roles.contains(role)){
            this.roles.remove(role);
        }
    }

    public void change(String password, Set<Role> roles){
        this.password = password;
        this.roles = roles;
    }
}
