package hello.dao;

import hello.domain.Book;

public interface BookRepository {

    Book findByIsbn(String isbn);
}
