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
 * ObjectRefTypeAttribute generated by hbm2java
 */
@Entity
@Table(name = "object_ref_type_attribute")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Configurable("objectRefTypeAttribute")
public class ObjectRefTypeAttribute implements java.io.Serializable {

    private Integer id;
    private String name;
    private String value;

    private ObjectRefType objectRefType;


    public ObjectRefTypeAttribute() {
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


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "object_ref_type_id")
    public ObjectRefType getObjectRefType() {
        return this.objectRefType;
    }

    public void setObjectRefType(ObjectRefType objectRefType) {
        this.objectRefType = objectRefType;
    }

}


