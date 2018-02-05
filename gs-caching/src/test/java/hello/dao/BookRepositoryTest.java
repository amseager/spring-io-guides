package hello.dao;

import hello.Application;
import hello.domain.Book;
import org.assertj.core.api.SoftAssertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class BookRepositoryTest {

    @Autowired
    private BookRepository repository;

    @Test
    public void findByIsbn_WithIsbnParam_ReturnsCorrespondingBook() {
        SoftAssertions softly = new SoftAssertions();

        Book book1 = repository.findByIsbn("isbn-1234");
        Book book2 = repository.findByIsbn("isbn-5678");
        Book book3 = repository.findByIsbn("isbn-1234");
        Book book4 = repository.findByIsbn("isbn-5678");
        Book book5 = repository.findByIsbn("isbn-1234");
        Book book6 = repository.findByIsbn("isbn-1234");

        softly.assertThat(book1.getIsbn()).isEqualTo("isbn-1234");
        softly.assertThat(book2.getIsbn()).isEqualTo("isbn-5678");
        softly.assertThat(book3.getIsbn()).isEqualTo("isbn-1234");
        softly.assertThat(book4.getIsbn()).isEqualTo("isbn-5678");
        softly.assertThat(book5.getIsbn()).isEqualTo("isbn-1234");
        softly.assertThat(book6.getIsbn()).isEqualTo("isbn-1234");
    }

}
