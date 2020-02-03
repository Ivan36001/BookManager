package net.proselyte.bookmanager.dao;

import net.proselyte.bookmanager.model.Book;

import java.util.List;
//Вершиной иерархии DAO является абстрактный класс или интерфейс с описанием общих методов, которые будут использоваться
// при взаимодействии с базой данных
public interface BookDao {
    //описываем методы которые необходимо реализовать в классе BookDaoImpl:

    public void addBook(Book book);//метод добавления книги

    public void updateBook(Book book);//метод изменения книги

    public void removeBook(int id);//метод удаления книги по id

    public Book getBookById(int id);//получение книги по id

    public List<Book> listBooks();//метод котрый выводит список всех книг нашей таблицы
}
