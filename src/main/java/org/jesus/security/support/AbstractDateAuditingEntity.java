package org.jesus.security.support;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class AbstractDateAuditingEntity implements Serializable {

  private static final long serialVersionUID = 1L;

  @CreatedDate
  @Column(nullable = false, updatable = false)
  private LocalDateTime registered = LocalDateTime.now();

  @LastModifiedDate
  private LocalDateTime updated;

}
