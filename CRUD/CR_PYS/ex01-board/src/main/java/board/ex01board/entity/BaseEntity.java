package board.ex01board.entity;

import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

// 시간 정보
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter
public class BaseEntity {
    @CreationTimestamp // 생성됐을 때 시간
    @Column(updatable = false) // 수정 시에는 관여X
    private LocalDateTime createdTime;

    @UpdateTimestamp // 업데이트 됐을 때 시간
    @Column(insertable = false) // 입력 시에는 관여X
    private LocalDateTime updatedTime;
}
