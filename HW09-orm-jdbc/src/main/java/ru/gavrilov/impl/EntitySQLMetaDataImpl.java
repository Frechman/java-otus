package ru.gavrilov.impl;

import ru.gavrilov.model.User;
import ru.gavrilov.utils.ReflectionUtils;
import ru.otus.jdbc.mapper.EntityClassMetaData;
import ru.otus.jdbc.mapper.EntitySQLMetaData;

import java.lang.annotation.ElementType;
import java.lang.reflect.Field;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author gavrilov-sv
 * created on 23.06.2020
 */
public class EntitySQLMetaDataImpl implements EntitySQLMetaData {

    private static final String SELECT_QUERY_FORMAT = "SELECT %s FROM %s WHERE %s";
    private static final String INSERT_QUERY_FORMAT = "INSERT INTO %s (%s) VALUES (%s)";
    private static final String UPDATE_QUERY_FORMAT = "UPDATE %s SET %s WHERE %s";
    private static final String CONDITION_ALL = "1 = 1";
    private static final String CONDITION_BY_FIELD = " = ?";

    private final EntityClassMetaData<?> entityClassMetaData;

    private final String tableName;
    private final String allParameters;
    private final String parametersWithoutId;

    public EntitySQLMetaDataImpl(EntityClassMetaData<?> classMetaData) {
        this.entityClassMetaData = classMetaData;
        this.tableName = classMetaData.getName().toLowerCase();
        this.allParameters = buildStringQueryParameters(classMetaData.getAllFields(), Function.identity());
        this.parametersWithoutId = buildStringQueryParameters(classMetaData.getFieldsWithoutId(), Function.identity());
    }

    @Override
    public String getSelectAllSql() {
        return String.format(SELECT_QUERY_FORMAT, allParameters, tableName, CONDITION_ALL);
    }

    @Override
    public String getSelectByIdSql() {
        String conditionById = entityClassMetaData.getIdField().getName() + CONDITION_BY_FIELD;
        return String.format(SELECT_QUERY_FORMAT, allParameters, tableName, conditionById);
    }

    @Override
    public String getInsertSql() {
        return String.format(
                INSERT_QUERY_FORMAT, tableName, parametersWithoutId,
                getQuestionsString(entityClassMetaData.getFieldsWithoutId().size()));
    }

    @Override
    public String getUpdateSql() {
        String conditionById = entityClassMetaData.getIdField().getName() + CONDITION_BY_FIELD;
        final String updatingParameters =
                buildStringQueryParameters(entityClassMetaData.getFieldsWithoutId(), name -> name + CONDITION_BY_FIELD);
        return String.format(UPDATE_QUERY_FORMAT, tableName, updatingParameters, conditionById);
    }

    private String buildStringQueryParameters(List<Field> fieldList,
                                              Function<? super String, ? extends String> mapper) {
        return fieldList.stream().map(Field::getName).map(mapper).collect(Collectors.joining(", "));
    }

    private String getQuestionsString(int countQuestions) {
        if (countQuestions < 1) {
            return "";
        }
        return "?, ".repeat(countQuestions).substring(0, countQuestions * 3 - 2);
    }
}
