package net.proselyte.bookmanager.controller;

import net.proselyte.bookmanager.model.Book;
import net.proselyte.bookmanager.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller//создаем класс контроллера
public class BookController {
    private BookService bookService;//создание ссылки бин создается в mvc-dispatcher-servlet.xml

    @Autowired(required = true)//внедрение зависимости
    @Qualifier(value = "bookService")//определяем какой бин будет внедряться
    public void setBookService(BookService bookService) {
        this.bookService = bookService;
    }

    @RequestMapping(value = "books", method = RequestMethod.GET)//при таком GET запросе выходим на главную страницу
    public String listBooks(Model model){
        model.addAttribute("book", new Book());// В наш шаблонизатор мы передаем model в которой находятся данные
        // необходимые для отображения на веб-страничке. model.addAttribute("book", new Book()); это добавление в
        // передаваемую модель данных, в вашем случае new Book(), которые потом будут доступны по ключу который мы
        // указываем как параметр String, в вашем случае это "book"


        model.addAttribute("listBooks", this.bookService.listBooks());//добавляем в model атрибут под названием
        //"listBooks" и значением полученным от работы метода listBooks() на объекте bookService

        return "books";//возвращаем ссылку на главную страницу books
    }

    @RequestMapping(value = "/books/add", method = RequestMethod.POST)//при POST запросе происходит изменение данных
    // (добавление книги) на странице /books/add
    public String addBook(@ModelAttribute("book") Book book){//@ ModelAttribute - При использовании в качестве аргумента
        // метода она указывает, что аргумент должен быть получен из модели

        if(book.getId() == 0){//если книга не существует (id у существующей книги не может быть равен нулю)
            this.bookService.addBook(book);//добавляем книгу
        }else {//иначе
            this.bookService.updateBook(book);//обновляем информацию о книге
        }

        return "redirect:/books";//возвращаемся на главную страницу (с страницы добавления)
    }

    @RequestMapping("/remove/{id}")//Здесь в аннотации @RequestMapping в адресе использована конструкция {id},
    // определяющая место параметра в адресной строке. В параметрах метода использована дополнительная аннотация
    // @PathVariable, указывающая на то, что данный параметр получается из адресной строки.
    public String removeBook(@PathVariable("id") int id){
        this.bookService.removeBook(id);

        return "redirect:/books";
    }

    @RequestMapping("edit/{id}")
    public String editBook(@PathVariable("id") int id, Model model){
        model.addAttribute("book", this.bookService.getBookById(id));
        model.addAttribute("listBooks", this.bookService.listBooks());

        return "books";
    }

    @RequestMapping("bookdata/{id}")
    public String bookData(@PathVariable("id") int id, Model model){
        model.addAttribute("book", this.bookService.getBookById(id));

        return "bookdata";
    }
}
