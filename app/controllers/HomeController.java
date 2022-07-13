package controllers;

import models.Employee;
import org.apache.commons.mail.EmailAttachment;
import play.data.FormFactory;
import play.db.Database;
import play.libs.mailer.Email;
import play.libs.mailer.MailerClient;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;

import javax.inject.Inject;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static java.sql.Statement.RETURN_GENERATED_KEYS;

/**
 * This controller contains an action to handle HTTP requests
 * to the application's home page.
 */
public class HomeController extends Controller {
    private final MailerClient mailerClient;
    private final Database db;
    private final FormFactory formFactory;

    private static final String FIND_ALL = "SELECT * FROM employee";
    private static final String GET_EMPLOYEE = "SELECT * FROM employee WHERE email = ? AND password = ?";
    private static final String ADD_EMPLOYEE = "INSERT INTO employee (email, password, status) VALUES (?, ?, ?)";

    @Inject
    public HomeController(Database db, FormFactory formFactory, MailerClient mailerClient) {
        this.db = db;
        this.formFactory = formFactory;
        this.mailerClient = mailerClient;
    }

    public Result index() {
        return ok(views.html.index.render());
    }

    public Result login(Http.Request request) {
        Employee employee = formFactory.form(Employee.class).bindFromRequest(request).get();
        employee.setStatus("Не подтвержден");

        try (Connection connection = db.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_EMPLOYEE)) {

            preparedStatement.setObject(1, employee.getEmail());
            preparedStatement.setObject(2, employee.getPassword());

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return ok(views.html.login.render(new Employee(
                    resultSet.getLong("id"),
                    resultSet.getString("email"),
                    resultSet.getString("password"),
                    resultSet.getString("status")))
                );
            } else {
                return badRequest(views.html.exception.render("Такого пользователя не существует или введены некорректные данные"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Result registration(Http.Request request) {
        Employee employee = formFactory.form(Employee.class).bindFromRequest(request).get();
        employee.setStatus("Не подтвержден");

        try (Connection connection = db.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(ADD_EMPLOYEE, RETURN_GENERATED_KEYS)) {

            preparedStatement.setObject(1, employee.getEmail());
            preparedStatement.setObject(2, employee.getPassword());
            preparedStatement.setObject(3, employee.getStatus());

            preparedStatement.executeUpdate();

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            generatedKeys.next();
            employee.setId(generatedKeys.getObject("id", Long.class));

//            sendEmail();

            return ok(views.html.login.render(employee)
            );
        } catch (SQLException e) {
            return badRequest(views.html.exception.render("Такой пользователь уже существует"));
        }
    }

    private void sendEmail() {
        try {
            String cid = "1234";
            Email email = new Email()
                .setSubject("Simple email")
                .setFrom("<my@mail.ru>")
                .addTo("<kerimovikh@mail.ru>")
                .setBodyText("A text message")
                .setBodyHtml("<html><body><p>An <b>html</b> message with cid <img src=\"cid:" + cid + "\"></p></body></html>");
            mailerClient.send(email);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Result getAllEmployees() {
        try (Connection connection = db.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL)) {

            ResultSet resultSet = preparedStatement.executeQuery();

            List<Employee> employees = getAll(resultSet);

            return ok(views.html.allEmployees.render(employees));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private List<Employee> getAll(ResultSet resultSet) throws SQLException {
        List<Employee> employees = new ArrayList<>();
        while (resultSet.next()) {
            employees.add(new Employee(
                resultSet.getLong("id"),
                resultSet.getString("email"),
                resultSet.getString("password"),
                resultSet.getString("status"))
            );
        }
        return employees;
    }
}
