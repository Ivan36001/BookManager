package net.proselyte.bookmanager.dao;

import net.proselyte.bookmanager.model.Book;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository// Классы, отвечающие за хранение и извлечение данных часто называют Repository или  DAO (Data Access Object)
//  — это объект, основная задача которого сохранять данные  в базу данных, а также извлекать их из неё.

public class BookDaoImpl implements BookDao {
    private static final Logger logger = LoggerFactory.getLogger(BookDaoImpl.class);//создаем объект logger для обеспе-
    // чения логирования

    private SessionFactory sessionFactory;//создание ссылки бин создается в mvc-dispatcher-servlet.xml

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    /*

    transient object. Объекты в данном статусе — это заполненные экземпляры классов-сущностей. Могут быть сохранены в БД.
     Не присоединены к сессии. Поле Id не должно быть заполнено, иначе объект имеет статус detached ;

    persistent object. Объект в данном статусе — так называемая хранимая сущность, которая присоединена к конкретной
    сессии. Только в этом статусе объект взаимодействует с базой данных. При работе с объектом данного типа в рамках
    транзакции все изменения объекта записываются в базу;

    detached object. Объект в данном статусе — это объект, отсоединённый от сессии, может существовать или не
     существовать в БД.

     */

    @Override
    public void addBook(Book book) {
        Session session = this.sessionFactory.getCurrentSession();//создаем сессию которая просит фабрику сессий
        // получить текущую сессию
        session.persist(book);//сессия сохранияет объект который мы ей передаем. persist(Object) — преобразует объект
        // из transient в persistent, то есть присоединяет к сессии и сохраняет в БД
        logger.info("Book successfully saved. Book details: " + book);//добавляем информацию в лог
    }

    @Override
    public void updateBook(Book book) {
        Session session = this.sessionFactory.getCurrentSession();
        session.update(book);//update(Object) — обновляет объект в БД, преобразуя его в persistent (Object в статусе
        // detached)
        logger.info("Book successfully update. Book details: " + book);//добавляем информацию в лог
    }

    @Override
    public void removeBook(int id) {
        Session session = this.sessionFactory.getCurrentSession();
        Book book = (Book) session.load(Book.class, new Integer(id));//Вместо метода session.get() можно использовать
        // session.load(). Метод session.load() возвращает так называемый proxy-object. Proxy-object — это
        // объект-посредник, через который мы можем взаимодействовать с реальным объектом в БД. Он расширяет функционал
        // объекта-сущности. Взаимодействие с proxy-object полностью аналогично взаимодействию с объектом-сущностью.
        // Proxy-object отличается от объекта-сущности тем, что при создании proxy-object не выполняется ни одного
        // запроса к БД, т. е. Hibernate просто верит нам, что объект с данным Id существует в БД. Однако первый
        // вызванный get или set у proxy-object сразу инициирует запрос select, и если объекта с данным Id нет в базе,
        // то мы получим ObjectNotFoundException.

        if(book!=null){
            session.delete(book);//delete(Object) — удаляет объект из БД, иными словами, преобразует persistent в
            // transient
        }
        logger.info("Book successfully removed. Book details: " + book);//добавляем информацию в лог
    }

    @Override
    public Book getBookById(int id) {//метод для получения книги по ее id
        Session session =this.sessionFactory.getCurrentSession();
        Book book = (Book) session.load(Book.class, new Integer(id));
        logger.info("Book successfully loaded. Book details: " + book);

        return book;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Book> listBooks() {//метод для вывод списка книг
        Session session = this.sessionFactory.getCurrentSession();
        List<Book> bookList = session.createQuery("from Book").list();//HQL запрос где: оператор FROM используется
        // для загрузки (чтения) набора объектов

        for(Book book: bookList){
            logger.info("Book list: " + book);
        }

        return bookList;
    }
}
