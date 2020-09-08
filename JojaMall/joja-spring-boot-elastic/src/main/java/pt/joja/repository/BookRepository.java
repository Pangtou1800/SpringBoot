package pt.joja.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import pt.joja.entity.Book;

public interface BookRepository extends ElasticsearchRepository<Book, Integer> {
}
