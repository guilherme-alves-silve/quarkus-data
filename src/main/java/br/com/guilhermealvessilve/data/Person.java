package br.com.guilhermealvessilve.data;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.json.bind.annotation.JsonbProperty;
import javax.persistence.Column;
import javax.persistence.Entity;

@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = false)
@Entity(name = "person")
public class Person extends PanacheEntity {

    private String name;

    @Column(name = "last_name")
    @JsonbProperty("last_name")
    private String lastName;

    private String document;

    @Column(name = "document_type")
    @JsonbProperty("document_type")
    private String documentType;
}
