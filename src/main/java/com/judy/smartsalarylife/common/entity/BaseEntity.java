package com.judy.smartsalarylife.common.entity;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@MappedSuperclass
// 공통적으로 사용하는 필드와 매핑 정보를 부모 클래스에 정의하고 이를 상속받는 엔티티 클래스에서 재사용할 수 있도록 지원
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity {
    
    @CreatedDate
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime modifiedDate;

}
