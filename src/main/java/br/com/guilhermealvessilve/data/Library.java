package br.com.guilhermealvessilve.data;

import org.infinispan.protostream.annotations.ProtoFactory;
import org.infinispan.protostream.annotations.ProtoField;

import javax.json.bind.annotation.JsonbCreator;

public class Library {

    private final String id;

    private final String address;

    private final String name;

    private final String foundation;

    @JsonbCreator
    @ProtoFactory
    public Library(String id, String address, String name, String foundation) {
        this.id = id;
        this.address = address;
        this.name = name;
        this.foundation = foundation;
    }

    @ProtoField(number = 1)
    public String getId() {
        return id;
    }

    @ProtoField(number = 2)
    public String getAddress() {
        return address;
    }

    @ProtoField(number = 3)
    public String getName() {
        return name;
    }

    @ProtoField(number = 4)
    public String getFoundation() {
        return foundation;
    }
}
