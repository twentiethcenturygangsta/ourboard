package com.twentiethcenturygangsta.ourboard.site;

import com.twentiethcenturygangsta.ourboard.annoatation.OurBoardEntity;
import com.twentiethcenturygangsta.ourboard.config.ShardConfigurationReference;
import com.twentiethcenturygangsta.ourboard.dto.DatabaseColumn;
import com.twentiethcenturygangsta.ourboard.dto.DatabaseSchema;
import com.twentiethcenturygangsta.ourboard.dto.FieldInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.reflections.Reflections;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
    private Set<Class<?>> tables;

    @Bean
    public Set<Class<?>> registerTables() {
        Set<Class<?>> baseClasses = new Reflections(ourBoardBasePackage).getTypesAnnotatedWith(OurBoardEntity.class);
        baseClasses.addAll(new Reflections(shardConfigurationReference.registerBasePackage()).getTypesAnnotatedWith(OurBoardEntity.class));
        this.tables = baseClasses;
        return baseClasses;
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
        log.info("databaseMetaData = {}", databaseMetaData);
        databaseSchemas = databaseTableSchemas;
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
}
