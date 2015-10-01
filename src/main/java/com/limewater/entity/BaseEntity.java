package com.limewater.entity;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

import static java.time.Instant.ofEpochMilli;
import static java.time.LocalDateTime.ofInstant;
import static java.time.ZoneId.systemDefault;
import static java.util.Date.from;
import static java.util.Objects.isNull;

@MappedSuperclass
public abstract class BaseEntity implements Serializable {

    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_date", updatable = false)
    private Date createdDate;

    @LastModifiedDate
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "last_modified_date")
    private Date lastModifiedDate;

    public LocalDateTime getCreatedDate() {
        return isNull(createdDate) ? null : ofInstant(ofEpochMilli(createdDate.getTime()), systemDefault());
    }

    public void setCreatedDate(final LocalDateTime createdDate) {
        this.createdDate = isNull(createdDate) ? null : from(createdDate.atZone(systemDefault()).toInstant());
    }

    public LocalDateTime getLastModifiedDate() {
        return isNull(lastModifiedDate) ? null : ofInstant(ofEpochMilli(lastModifiedDate.getTime()), systemDefault());
    }

    public void setLastModifiedDate(final LocalDateTime lastModifiedDate) {
        this.lastModifiedDate = isNull(lastModifiedDate) ? null : from(lastModifiedDate.atZone(systemDefault()).toInstant());
    }


}
