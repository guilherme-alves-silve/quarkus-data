package br.com.guilhermealvessilve.repository;

import io.agroal.api.AgroalDataSource;
import io.quarkus.agroal.DataSource;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class NumberRepository {

    private static final Logger LOGGER = Logger.getLogger(NumberRepository.class.getSimpleName());

    private final AgroalDataSource dataSourceH2;

    @Inject
    public NumberRepository(@DataSource("h2") AgroalDataSource dataSourceH2) {
        this.dataSourceH2 = dataSourceH2;
        try (final var connection = dataSourceH2.getConnection();
            final var statement = connection.createStatement()) {
            statement.execute("CREATE TABLE numbers (value INT);");
            statement.execute("INSERT INTO numbers (value) VALUES (1), (2), (3), (4);");
        } catch (SQLException ex) {
            LOGGER.error(ex);
        }
    }

    public List<Integer> getAll() {

        try (final var connection = dataSourceH2.getConnection();
             final var statement = connection.prepareStatement("SELECT * FROM numbers;");
             final var resultSet = statement.executeQuery()) {

            final var numbers = new ArrayList<Integer>();
            while (resultSet.next()) {
                numbers.add(resultSet.getInt(1));
            }

            return numbers;
        } catch (SQLException ex) {
            LOGGER.error(ex);
            return List.of();
        }
    }
}
