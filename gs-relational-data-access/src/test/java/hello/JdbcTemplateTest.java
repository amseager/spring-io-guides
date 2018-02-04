package hello;

import hello.domain.Customer;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@AutoConfigureTestDatabase
@Transactional
public class JdbcTemplateTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Before
    public void setUp() {
        jdbcTemplate.execute("DROP TABLE customers IF EXISTS");
        jdbcTemplate.execute("CREATE TABLE customers(" +
                "id SERIAL, first_name VARCHAR(255), last_name VARCHAR(255))");

        List<Object[]> splitUpNames = Stream.of("John Woo", "Jeff Dean", "Josh Bloch", "Josh Long")
                .map(name -> name.split(" "))
                .collect(Collectors.toList());

        jdbcTemplate.batchUpdate("INSERT INTO customers(first_name, last_name) VALUES (?,?)", splitUpNames);
    }

    @Test
    public void jdbcTemplateQuery_WithFirstNameParam_ReturnsCustomersWithSpecificFirstName() {
        List<Customer> customers = jdbcTemplate.query(
                "SELECT id, first_name, last_name FROM customers WHERE first_name = ?"
                , new Object[]{"Josh"}
                , (rs, rowNum) -> new Customer(rs.getLong("id"), rs.getString("first_name"), rs.getString("last_name"))
        );

        assertThat(customers.size()).isEqualTo(2);
    }
}
