package hello;

import hello.domain.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringBootApplication
public class Application implements CommandLineRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(Application.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... strings) {

        LOGGER.info("Creating tables...");

        jdbcTemplate.execute("DROP TABLE CUSTOMERS IF EXISTS");
        jdbcTemplate.execute("CREATE TABLE CUSTOMERS(" +
                "id SERIAL, first_name VARCHAR(255), last_name VARCHAR(255))");

        List<Object[]> splitUpNames = Stream.of("John Woo", "Jeff Dean", "Josh Bloch", "Josh Long")
                .map(name -> name.split(" "))
                .collect(Collectors.toList());

        splitUpNames.forEach(name -> LOGGER.info(String.format("Inserting customer record for %s %s", name[0], name[1])));

        jdbcTemplate.batchUpdate("INSERT INTO CUSTOMERS(FIRST_NAME, LAST_NAME) VALUES (?,?)", splitUpNames);

        LOGGER.info("Querying for customer records where first_name = 'Josh':");
        jdbcTemplate.query(
                "SELECT ID, FIRST_NAME, LAST_NAME FROM CUSTOMERS WHERE FIRST_NAME = ?"
                , new Object[] { "Josh" }
                , (rs, rowNum) -> new Customer(rs.getLong("id"), rs.getString("first_name"), rs.getString("last_name"))
        ).forEach(customer -> LOGGER.info(customer.toString()));
    }
}
