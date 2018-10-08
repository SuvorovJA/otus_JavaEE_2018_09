package ru.otus.ee.L03.entityes;

import lombok.Data;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import javax.xml.bind.annotation.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "departs")
@XmlRootElement(name = "departament")
@XmlAccessorType(XmlAccessType.FIELD)
public class DepartmentEntity implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "depart_id")
    @XmlAttribute(required = true)
    private long id;

    @Basic
    @Column(nullable = false, unique = true)
    @NaturalId
    @XmlElement(required = true)
    private String name;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{ id:").append(id).append("; name:").append(name).append("}");
        return sb.toString();
    }
}
