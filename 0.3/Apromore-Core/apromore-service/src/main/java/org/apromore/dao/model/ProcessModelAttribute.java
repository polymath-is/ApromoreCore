package org.apromore.dao.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.beans.factory.annotation.Configurable;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * ProcessModelAttribute generated by hbm2java
 */
@Entity
@Table(name = "process_model_attribute")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Configurable("processModelAttribute")
public class ProcessModelAttribute implements java.io.Serializable {


    private Integer id;
    private ProcessModelVersion processModelVersion;
    private String name;
    private String value;


    public ProcessModelAttribute() {
    }


    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "process_model_version_id")
    public ProcessModelVersion getProcessModelVersion() {
        return this.processModelVersion;
    }

    public void setProcessModelVersion(ProcessModelVersion processModelVersion) {
        this.processModelVersion = processModelVersion;
    }


    @Column(name = "name")
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Column(name = "value")
    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }


}


