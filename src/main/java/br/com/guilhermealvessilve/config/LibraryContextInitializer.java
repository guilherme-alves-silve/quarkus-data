package br.com.guilhermealvessilve.config;

import br.com.guilhermealvessilve.data.Library;
import org.infinispan.protostream.SerializationContextInitializer;
import org.infinispan.protostream.annotations.AutoProtoSchemaBuilder;

@AutoProtoSchemaBuilder(
        includeClasses = { Library.class },
        schemaPackageName = "library_sample"
)
public interface LibraryContextInitializer extends SerializationContextInitializer {

}
