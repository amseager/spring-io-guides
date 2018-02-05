package hello.dao.impl;

import hello.dao.BookRepository;
import hello.domain.Book;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

@Repository
public class SimpleBookRepository implements BookRepository {

    @Override
    @Cacheable("books")
    public Book findByIsbn(String isbn) {
        simulateSlowService();
        return new Book(isbn, "Some book");
    }

    private void simulateSlowService() {
        try {
            long time = 3000L;
            Thread.sleep(time);
        } catch (InterruptedException e) {
            throw new IllegalStateException(e);
        }
    }
}
