package ru.gavrilov;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.gavrilov.impl.JdbcMapperImpl;
import ru.gavrilov.impl.UserDaoWithJdbcMapper;
import ru.gavrilov.model.Account;
import ru.gavrilov.model.User;
import ru.otus.core.service.DbServiceUserImpl;
import ru.otus.h2.DataSourceH2;
import ru.otus.jdbc.DbExecutor;
import ru.otus.jdbc.DbExecutorImpl;
import ru.otus.jdbc.sessionmanager.SessionManagerJdbc;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Optional;

/**
 * @author gavrilov-sv
 * created on 25.06.2020
 */
public class JdbcMapperDemo {

    private static final Logger logger = LoggerFactory.getLogger(JdbcMapperDemo.class);

    private static final String CREATE_USER_TABLE =
            "CREATE TABLE user(id BIGINT NOT NULL AUTO_INCREMENT, name VARCHAR(255), age INT)";
    private static final String CREATE_ACCOUNT_TABLE =
            "CREATE TABLE account(no BIGINT NOT NULL AUTO_INCREMENT, type VARCHAR(255), rest NUMBER)";

    public static void main(String[] args) throws Exception {
        var dataSource = new DataSourceH2();
        var demo = new JdbcMapperDemo();

        demo.createTable(dataSource, CREATE_USER_TABLE);

        var sessionManager = new SessionManagerJdbc(dataSource);
        DbExecutor<User> dbExecutor = new DbExecutorImpl<>();
        var userDao = new UserDaoWithJdbcMapper(sessionManager, dbExecutor);

        var dbServiceUser = new DbServiceUserImpl(userDao);
        var id = dbServiceUser.saveUser(new User(0, "dbServiceUser"));
        Optional<User> user = dbServiceUser.getUser(id);

        user.ifPresentOrElse(
                crUser -> logger.info("created user, name:{}", crUser.getName()),
                () -> logger.info("user was not created")
        );

        demo.createTable(dataSource, CREATE_ACCOUNT_TABLE);

        var accountDbExecutor = new DbExecutorImpl<Account>();
        var accountJdbcMapper = new JdbcMapperImpl<>(Account.class, sessionManager, accountDbExecutor);

        sessionManager.beginSession();

        var account = new Account("String Type", BigDecimal.TEN);
        accountJdbcMapper.insert(account);

        final Account account2 = new Account(account.getNo(), "updateAccount", BigDecimal.ZERO);
        accountJdbcMapper.insertOrUpdate(account2);

        final Account account3 = new Account("insertAccount", BigDecimal.ONE);
        accountJdbcMapper.insertOrUpdate(account3);

        final Account account1FromDb = accountJdbcMapper.findById(account.getNo(), Account.class);
        final Account account2FromDb = accountJdbcMapper.findById(account2.getNo(), Account.class);
        final Account account3FromDb = accountJdbcMapper.findById(account3.getNo(), Account.class);
        sessionManager.commitSession();

        System.out.println(account1FromDb);
        System.out.println(account2FromDb);
        System.out.println(account3FromDb);
    }

    private void createTable(DataSource dataSource, String sql) throws SQLException {
        try (var connection = dataSource.getConnection();
             var pst = connection.prepareStatement(sql)) {
            pst.executeUpdate();
        }
        System.out.println("table created");
    }
}
