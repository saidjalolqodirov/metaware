package uz.qodirov.generic;

import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Data
public abstract class GenericAuditingEntity<T> implements GenericEntity<T> {

    @CreatedBy
    @Column(name = "created_by", length = 50, updatable = false)
    protected String createdBy;

    @LastModifiedBy
    @Column(name = "modified_by", length = 50)
    protected String modifiedBy;

    @CreatedDate
    @Column(name = "created_date")
    protected Long createdDate;

    @LastModifiedDate
    @Column(name = "modified_date")
    protected Long modifiedDate;

    @Column(name = "deleted", columnDefinition = " boolean DEFAULT false")
    protected boolean deleted = false;

}
