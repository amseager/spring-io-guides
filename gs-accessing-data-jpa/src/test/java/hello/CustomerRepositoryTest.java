package hello;

import hello.dao.CustomerRepository;
import hello.domain.Customer;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@AutoConfigureTestDatabase
@Transactional
public class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository repository;

    @Before
    public void setUp() {
        repository.save(new Customer("Jack", "Bauer"));
        repository.save(new Customer("Chloe", "O'Brian"));
        repository.save(new Customer("Kim", "Bauer"));
        repository.save(new Customer("David", "Palmer"));
        repository.save(new Customer("Michelle", "Dessler"));
    }

    @Test
    public void customerRepository_FindAll_ReturnsAllRecords() {
        List<Customer> customers = repository.findAll();

        assertThat(customers.size()).isEqualTo(5);
    }

    @Test
    public void customerRepository_FindByLastName_ReturnsRecordsWithSpecificLastName() {
        List<Customer> customers = repository.findByLastName("Bauer");

        assertThat(customers.size()).isEqualTo(2);
    }
}
