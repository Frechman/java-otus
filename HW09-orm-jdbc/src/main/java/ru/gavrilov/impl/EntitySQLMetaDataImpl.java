package ru.gavrilov.impl;

import ru.otus.jdbc.mapper.EntityClassMetaData;
import ru.otus.jdbc.mapper.EntitySQLMetaData;

import java.lang.reflect.Field;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author gavrilov-sv
 * created on 23.06.2020
 */
public class EntitySQLMetaDataImpl implements EntitySQLMetaData {

    private final String selectAllQuery;
    private final String selectByIdQuery;
    private final String insertQuery;
    private final String updateQuery;

    public EntitySQLMetaDataImpl(EntityClassMetaData<?> classMetaData) {
        this.selectAllQuery = buildSelectAllQuery(classMetaData);
        this.selectByIdQuery = buildSelectByIdQuery(classMetaData);
        this.insertQuery = buildInsertQuery(classMetaData);
        this.updateQuery = buildUpdateQuery(classMetaData);
    }

    @Override
    public String getSelectAllSql() {
        return selectAllQuery;
    }

    @Override
    public String getSelectByIdSql() {
        return selectByIdQuery;
    }

    @Override
    public String getInsertSql() {
        return insertQuery;
    }

    @Override
    public String getUpdateSql() {
        return updateQuery;
    }

    private String buildSelectAllQuery(EntityClassMetaData<?> classMetaData) {
        var allParameters = buildStringQueryParameters(classMetaData.getAllFields(), Function.identity());
        return String.format("SELECT %s FROM %s", allParameters, classMetaData.getName().toLowerCase());
    }

    private String buildSelectByIdQuery(EntityClassMetaData<?> classMetaData) {
        var allParameters = buildStringQueryParameters(classMetaData.getAllFields(), Function.identity());
        return String.format("SELECT %s FROM %s WHERE %s = ?",
                allParameters, classMetaData.getName().toLowerCase(), classMetaData.getIdField().getName());
    }

    private String buildInsertQuery(EntityClassMetaData<?> classMetaData) {
        var parametersWithoutId = buildStringQueryParameters(classMetaData.getFieldsWithoutId(), Function.identity());
        var tableName = classMetaData.getName().toLowerCase();
        return String.format("INSERT INTO %s (%s) VALUES (%s)",
                tableName, parametersWithoutId, getQuestionsString(classMetaData.getFieldsWithoutId().size()));
    }

    private String buildUpdateQuery(EntityClassMetaData<?> classMetaData) {
        var updateParameters = buildStringQueryParameters(classMetaData.getFieldsWithoutId(), param -> param + " = ?");
        return String.format("UPDATE %s SET %s WHERE %s = ?",
                classMetaData.getName().toLowerCase(), updateParameters, classMetaData.getIdField().getName());
    }

    private String buildStringQueryParameters(List<Field> fields,
                                              Function<? super String, ? extends String> mapper) {
        return fields.stream().map(Field::getName).map(mapper).collect(Collectors.joining(", "));
    }

    private String getQuestionsString(int countQuestions) {
        if (countQuestions < 1) {
            return "";
        }
        return "?, ".repeat(countQuestions).substring(0, countQuestions * 3 - 2);
    }
}
