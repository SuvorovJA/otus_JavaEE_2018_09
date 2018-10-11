package ru.otus.ee.L04.entityes;

import lombok.Data;

import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.*;
import javax.xml.bind.annotation.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "credentials")
@XmlRootElement(name = "credential")
@XmlAccessorType(XmlAccessType.FIELD)
public class CredentialEntity implements Serializable {

    @Id
    @GeneratedValue
    @XmlAttribute(required = true)
    private long id;

    @OneToOne(optional = false, mappedBy = "credentials")
    @XmlTransient  // http://blog.bdoughan.com/2010/07/jpa-entities-to-xml-bidirectional.html
    @JsonbTransient
    private EmployeEntity employe;

    @Basic
    @XmlElement(required = true)
    private String login;

    @Basic
    @XmlElement(required = true)
    private String passhash;

    @Override
    public String toString() {
        return "{ id:" + id +
                "; login:" + login +
                "; passhash:*" +
                "}";
    }

}
