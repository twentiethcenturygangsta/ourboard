package com.twentiethcenturygangsta.ourboard.site;

import com.twentiethcenturygangsta.ourboard.annoatation.OurBoardColumn;
import com.twentiethcenturygangsta.ourboard.annoatation.OurBoardEntity;
import com.twentiethcenturygangsta.ourboard.config.ShardConfigurationReference;
import com.twentiethcenturygangsta.ourboard.dto.DatabaseColumn;
import com.twentiethcenturygangsta.ourboard.dto.FieldInfo;
import com.twentiethcenturygangsta.ourboard.entity.DatabaseRelationType;
import com.twentiethcenturygangsta.ourboard.util.DatabaseUtils;
import jakarta.persistence.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.reflections.Reflections;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Field;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;


@Configuration
@Slf4j
@RequiredArgsConstructor
public class DatabaseClient {
    private static final String ourBoardBasePackage = "com.twentiethcenturygangsta.ourboard";
    private final OurBoardClient ourBoardClient;
    private final ShardConfigurationReference shardConfigurationReference;
    private LinkedHashMap<String, LinkedHashMap<String, DatabaseColumn>> databaseSchemas;
    private HashMap<String, Class<?>> entities;

    @Bean
    public void registerEntityTables() {
        HashMap<String, Class<?>> entities = new HashMap<>();
        Set<Class<?>> baseClasses = new Reflections(ourBoardBasePackage).getTypesAnnotatedWith(OurBoardEntity.class);
        baseClasses.addAll(new Reflections(shardConfigurationReference.registerBasePackage()).getTypesAnnotatedWith(OurBoardEntity.class));
        for(Class<?> object : baseClasses) {
            entities.put(DatabaseUtils.getSnakeNameForDatabase(object.getSimpleName()), object);
        }
        this.entities = entities;
    }
    @Bean
    public void registerDatabaseSchema() throws SQLException {
        LinkedHashMap<String, LinkedHashMap<String, DatabaseColumn>> databaseTableSchemas = new LinkedHashMap<>();
        DatabaseMetaData databaseMetaData = ourBoardClient.getConnection().getMetaData();
        ResultSet resultSet = databaseMetaData.getTables(null, null, null, new String[] {"TABLE"});

        while (resultSet.next()) {
            String name = resultSet.getString("TABLE_NAME");
            String schema = resultSet.getString("TABLE_SCHEM");
            ResultSet resultSetTable = databaseMetaData.getColumns(null, null, name, null);
            if (Objects.equals(schema, "PUBLIC")) {
                databaseTableSchemas.put(name, getColumnsInfo(resultSetTable));
            }
        }
        databaseSchemas = databaseTableSchemas;
    }

    public LinkedHashMap<String, FieldInfo> getFields(String tableName) {
        LinkedHashMap<String, FieldInfo> fields = new LinkedHashMap<>();
        Class<?> entity = entities.get(tableName);
        LinkedHashMap<String, DatabaseColumn> columns = databaseSchemas.get(tableName);

        for(Field field : entity.getDeclaredFields()) {
            String fieldName = getFieldName(field);
            Boolean hasIdAnnotation = hasIdAnnotation(field);
            DatabaseRelationType relationType = getDatabaseRelationType(field);
            String description = getFieldDescription(field);

            DatabaseColumn databaseColumn = columns.get(fieldName);

            if(databaseColumn != null || relationType.equals(DatabaseRelationType.ONE_TO_MANY)) {
                if (relationType.equals(DatabaseRelationType.ONE_TO_MANY)) {
                    fieldName = getOneToManyFieldName(field);
                }
                fields.put(
                        fieldName,
                        FieldInfo.builder()
                                .entityFieldName(field.getName())
                                .description(description)
                                .type(field.getType())
                                .databaseRelationType(relationType)
                                .hasIdAnnotation(hasIdAnnotation)
                                .databaseColumn(databaseColumn)
                                .build()
                );
            }
        }
        return fields;
    }

    public HashMap<String, Class<?>> getEntities() {
        return entities;
    }

    private LinkedHashMap<String, DatabaseColumn> getColumnsInfo(ResultSet resultSet) throws SQLException {
        LinkedHashMap<String, DatabaseColumn> columns = new LinkedHashMap<>();

        while(resultSet.next()){
            columns.put(
                    resultSet.getString("COLUMN_NAME"),
                    DatabaseColumn.builder()
                            .name(resultSet.getString("COLUMN_NAME"))
                            .displaySize(resultSet.getString("COLUMN_SIZE"))
                            .typeName(resultSet.getString("TYPE_NAME"))
                            .build());
        }
        return columns;
    }

    private DatabaseRelationType getDatabaseRelationType(Field field) {
        if(field.isAnnotationPresent(OneToOne.class)) {
            return DatabaseRelationType.ONE_TO_ONE;
        }
        if(field.isAnnotationPresent(OneToMany.class)) {
            return DatabaseRelationType.ONE_TO_MANY;
        }
        if(field.isAnnotationPresent(ManyToOne.class)) {
            return DatabaseRelationType.MANY_TO_ONE;
        }
        if(field.isAnnotationPresent(ManyToMany.class)) {
            return DatabaseRelationType.MANY_TO_MANY;
        }
        return DatabaseRelationType.NON_RELATIONSHIP;
    }

    private String getFieldName(Field field) {
        if(field.isAnnotationPresent(Column.class)) {
            return DatabaseUtils.getSnakeNameForDatabase(field.getAnnotation(Column.class).name());
        }
        if (field.isAnnotationPresent(JoinColumn.class)) {
            return DatabaseUtils.getSnakeNameForDatabase(field.getAnnotation(JoinColumn.class).name());
        }
        return DatabaseUtils.getSnakeNameForDatabase(field.getName());
    }

    private String getOneToManyFieldName(Field field) {
        String fieldName = "";
        for(Map.Entry<String, Class<?>> searchedEntity: entities.entrySet()) {
            for (Field searchedField : searchedEntity.getValue().getDeclaredFields()) {
                if (searchedField.getName().equals(field.getAnnotation(OneToMany.class).mappedBy())) {
                    fieldName = DatabaseUtils.getSnakeNameForDatabase(searchedEntity.getValue().getSimpleName()) + "_ID";
                }
            }
        }
        return fieldName;
    }

    private Boolean hasIdAnnotation(Field field) {
        return field.isAnnotationPresent(Id.class);
    }

    private String getFieldDescription(Field field) {
        if(field.isAnnotationPresent(OurBoardColumn.class)) {
            return field.getAnnotation(OurBoardColumn.class).description();
        }
        return "";
    }
}
