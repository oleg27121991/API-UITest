package by.veremei.api.models.book;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
@Data
@AllArgsConstructor
public class BooksCollection {
    private String userId;
    private List<Book> collectionOfIsbns;
}
