package ru.gavrilov.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.core.dao.UserDaoException;
import ru.otus.jdbc.DbExecutor;
import ru.otus.jdbc.mapper.EntityClassMetaData;
import ru.otus.jdbc.mapper.EntitySQLMetaData;
import ru.otus.jdbc.mapper.JdbcMapper;
import ru.otus.jdbc.sessionmanager.SessionManagerJdbc;
import ru.gavrilov.utils.ReflectionUtils;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * @author gavrilov-sv
 * created on 24.06.2020
 */
public class JdbcMapperImpl<T> implements JdbcMapper<T> {

    private static final Logger logger = LoggerFactory.getLogger(JdbcMapperImpl.class);

    private final EntitySQLMetaData entitySQLMetaData;
    private final EntityClassMetaData<T> entityClassMetaData;
    private final Class<T> clazz;
    private final SessionManagerJdbc sessionManager;
    private final DbExecutor<T> dbExecutor;

    public JdbcMapperImpl(Class<T> clazz, SessionManagerJdbc sessionManager, DbExecutor<T> dbExecutor) {
        this.clazz = clazz;
        this.sessionManager = sessionManager;
        this.dbExecutor = dbExecutor;
        this.entityClassMetaData = new EntityClassMetaDataImpl<>(clazz);
        this.entitySQLMetaData = new EntitySQLMetaDataImpl(entityClassMetaData);
    }

    @Override
    public void insert(T objectData) {
        insertOrUpdate(objectData);
    }

    @Override
    public void update(T objectData) {
        insertOrUpdate(objectData);
    }

    @Override
    public void insertOrUpdate(T objectData) {
        final String sql;
        final List<Object> params = getParams(entityClassMetaData.getFieldsWithoutId(), objectData);

        final Field idField = entityClassMetaData.getIdField();
        final Object idValue = ReflectionUtils.getFieldValue(idField, objectData);

        if (idValue != null && (long) idValue > 0L) {
            sql = entitySQLMetaData.getUpdateSql();
            params.add(idValue);
        } else {
            sql = entitySQLMetaData.getInsertSql();
        }

        try {
            final long idFromDb = dbExecutor.executeInsert(getConnection(), sql, params);
            ReflectionUtils.setFieldValue(objectData, idField, idFromDb);
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new UserDaoException(e);
        }
    }

    @Override
    public T findById(long id, Class<T> clazz) {
        try {
            final Function<ResultSet, T> mapper = getRowMapper(clazz);
            return dbExecutor.executeSelect(getConnection(), entitySQLMetaData.getSelectByIdSql(), id, mapper)
                    .orElse(null);
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    private Function<ResultSet, T> getRowMapper(Class<T> clazz) {
        return resultSet -> {
            try {
                if (resultSet.next()) {
                    final T instance = ReflectionUtils.instantiate(clazz.getConstructor());
                    for (Field field : clazz.getDeclaredFields()) {
                        final Class<?> type = ReflectionUtils.getWrapperType(field.getType());
                        final Object value = resultSet.getObject(field.getName(), type);
                        ReflectionUtils.setFieldValue(instance, field, value);
                    }
                    return instance;
                }
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
            return null;
        };
    }

    private List<Object> getParams(List<Field> fields, T objectData) {
        final List<Object> params = new ArrayList<>();
        for (Field field : fields) {
            params.add(ReflectionUtils.getFieldValue(field, objectData));
        }
        return params;
    }

    private Connection getConnection() {
        return sessionManager.getCurrentSession().getConnection();
    }

    //as a keepsake. one more example rowMapper
    private Function<ResultSet, T> getRowMapper(EntityClassMetaData<T> classMetaData) {
        return resultSet -> {
            try {
                if (resultSet.next()) {
                    final T instance = ReflectionUtils.instantiate(classMetaData.getConstructor());
                    for (Field field : classMetaData.getAllFields()) {
                        final Class<?> type = ReflectionUtils.getWrapperType(field.getType());
                        final Object value = resultSet.getObject(field.getName(), type);
                        ReflectionUtils.setFieldValue(instance, field, value);
                    }
                    return instance;
                }
            } catch (SQLException e) {
                logger.error(e.getMessage(), e);
            }
            return null;
        };
    }
}
