package net.proselyte.bookmanager.service;

import net.proselyte.bookmanager.dao.BookDao;
import net.proselyte.bookmanager.model.Book;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service//(Сервис-слой приложения) Аннотация, объявляющая, что этот класс представляет собой сервис – компонент
// сервис-слоя (который определяет границу между приложением и слоем сервисов, который образует набор доступных операций
// и управляет ответом приложения в каждой операции,  определяет для приложения границу и набор допустимых операций с
// точки зрения взаимодействующих с ним клиентских. Он инкапсулирует бизнес-логику приложения, управляя транзакциями и
// управляя ответами в реализации этих операций).
public class BookServiceImpl implements BookService {
    private BookDao bookDao;//создание ссылки бин создается в mvc-dispatcher-servlet.xml

    public void setBookDao(BookDao bookDao) {
        this.bookDao = bookDao;
    }

    @Override
    @Transactional//При входе в метод автоматически создаётся транзакция и открывается соединение с базой данных.
    // При выходе из метода транзакция автоматически подтверждается commit()). Если метод
    // кидает RuntimeError или его наследника (или настроенное исключение), транзакция автоматически откатывается.
    // В любом случае, после выхода из метода соединение с базой закрывается.
    public void addBook(Book book) {
        this.bookDao.addBook(book);
    }

    @Override
    @Transactional
    public void updateBook(Book book) {
        this.bookDao.updateBook(book);
    }

    @Override
    @Transactional
    public void removeBook(int id) {
        this.bookDao.removeBook(id);
    }

    @Override
    @Transactional
    public Book getBookById(int id) {
        return this.bookDao.getBookById(id);
    }

    @Override
    @Transactional
    public List<Book> listBooks() {
        return this.bookDao.listBooks();
    }
}
