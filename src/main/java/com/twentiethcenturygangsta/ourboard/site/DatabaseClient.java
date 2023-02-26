package com.twentiethcenturygangsta.ourboard.site;

import com.twentiethcenturygangsta.ourboard.annoatation.OurBoardColumn;
import com.twentiethcenturygangsta.ourboard.annoatation.OurBoardEntity;
import com.twentiethcenturygangsta.ourboard.config.ShardConfigurationReference;
import com.twentiethcenturygangsta.ourboard.dto.DatabaseColumn;
import com.twentiethcenturygangsta.ourboard.dto.DatabaseSchema;
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
import java.sql.Statement;
import java.util.*;


@Configuration
@Slf4j
@RequiredArgsConstructor
public class DatabaseClient {
    private static final String ourBoardBasePackage = "com.twentiethcenturygangsta.ourboard";
    private final OurBoardClient ourBoardClient;
    private final ShardConfigurationReference shardConfigurationReference;
    private LinkedHashMap<String, List<DatabaseColumn>> databaseSchemas;
    private HashMap<String, Class<?>> tables;

    @Bean
    public void registerTables() {
        HashMap<String, Class<?>> tables = new HashMap<>();
        Set<Class<?>> baseClasses = new Reflections(ourBoardBasePackage).getTypesAnnotatedWith(OurBoardEntity.class);
        baseClasses.addAll(new Reflections(shardConfigurationReference.registerBasePackage()).getTypesAnnotatedWith(OurBoardEntity.class));
        for(Class<?> object : baseClasses) {
            tables.put(object.getSimpleName(), object);
        }
        this.tables = tables;
    }
    @Bean
    public void registerDatabaseSchema() throws SQLException {
        LinkedHashMap<String, List<DatabaseColumn>> databaseTableSchemas = new LinkedHashMap<>();
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
        tables
        for (Class<?> table : tables) {
            if (tableName.equals(DatabaseUtils.getSnakeNameForDatabase(table.getSimpleName()))) {
                List<DatabaseColumn> columns = databaseSchemas.get(tableName);
                for(Field field : table.getDeclaredFields()) {
                    String name = getFieldName(field);
                    String description = "";

                    if(field.isAnnotationPresent(OurBoardColumn.class)) {
                        description = field.getAnnotation(OurBoardColumn.class).description();
                    }

                    if (field.isAnnotationPresent(JoinColumn.class)) {
                        name = DatabaseUtils.getSnakeNameForDatabase(field.getAnnotation(JoinColumn.class).name());
                    }
                    for (DatabaseColumn databaseColumn : columns) {

                        if(databaseColumn.getName().equals(name)) {
                            fields.put(
                                    name,
                                    FieldInfo.builder()
                                            .description(description)
                                            .type(field.getType())
                                            .databaseFieldName(DatabaseUtils.getSnakeNameForDatabase(table.getSimpleName()))
                                            .databaseRelationType(getDatabaseRelationType(field))
                                            .databaseColumn(databaseColumn)
                                            .build()
                            );
                        }
                    }
                    if (getDatabaseRelationType(field) == DatabaseRelationType.ONE_TO_MANY){

                        for (Class<?> searchedTable : tables) {
                            for (Field searchedField : searchedTable.getDeclaredFields()) {
                                if (searchedField.getName().equals(field.getAnnotation(OneToMany.class).mappedBy())) {
                                    name = DatabaseUtils.getSnakeNameForDatabase(searchedTable.getSimpleName()) + "_ID";
                                }
                            }
                        }
                        fields.put(
                                name,
                                FieldInfo.builder()
                                        .description(description)
                                        .type(field.getType())
                                        .databaseFieldName(DatabaseUtils.getSnakeNameForDatabase(table.getSimpleName()))
                                        .databaseRelationType(getDatabaseRelationType(field))
                                        .build()
                        );
                    }
                }
            }
        }
        return fields;
    }

    public LinkedHashMap<String, List<DatabaseColumn>> getDatabaseSchemas() {
        return databaseSchemas;
    }

    public Set<Class<?>> getTables() {
        return tables;
    }

    private List<DatabaseColumn> getColumnsInfo(ResultSet resultSet) throws SQLException {
        List<DatabaseColumn> columns = new ArrayList<>();

        while(resultSet.next()){
            columns.add(
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
        return DatabaseUtils.getSnakeNameForDatabase(field.getName());
    }

    private String getJoinColumnName(Field field) {
        if(field.isAnnotationPresent(JoinColumn.class)) {
            return DatabaseUtils.getSnakeNameForDatabase(field.getAnnotation(JoinColumn.class).name());
        }
        return DatabaseUtils.getSnakeNameForDatabase(field.getName());
    }
}
