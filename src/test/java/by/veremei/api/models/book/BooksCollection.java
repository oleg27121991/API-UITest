package by.veremei.api.models.book;

import java.util.List;

public record BooksCollection(String userId, List<Book> collectionOfIsbns) {
}
