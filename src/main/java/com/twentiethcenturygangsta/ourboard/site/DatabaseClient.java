package com.twentiethcenturygangsta.ourboard.site;

import com.twentiethcenturygangsta.ourboard.annoatation.OurBoardEntity;
import com.twentiethcenturygangsta.ourboard.config.ShardConfigurationReference;
import com.twentiethcenturygangsta.ourboard.dto.DatabaseSchema;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.reflections.Reflections;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;


@Configuration
@Slf4j
@RequiredArgsConstructor
public class DatabaseClient {
    private final String ourBoardBasePackage = "com.twentiethcenturygangsta.ourboard";
    private final OurBoardClient ourBoardClient;
    private final ShardConfigurationReference shardConfigurationReference;
    private List<DatabaseSchema> databaseSchemas;
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
        List<DatabaseSchema> databaseTableSchemas = new ArrayList<>();
        DatabaseMetaData databaseMetaData = ourBoardClient.getConnection().getMetaData();
        ResultSet resultSet = databaseMetaData.getTables(null, null, null, new String[] {"TABLE"});

        while (resultSet.next()) {
            String name = resultSet.getString("TABLE_NAME");
            String schema = resultSet.getString("TABLE_SCHEM");
            log.info("result datatables = {} {}", name, schema );
            if (Objects.equals(schema, "PUBLIC")) {
                databaseTableSchemas.add(DatabaseSchema.builder()
                        .name(name)
                        .schema(schema)
                        .build());
            }
        }
        log.info("databaseMetaData = {}", databaseMetaData);
        databaseSchemas = databaseTableSchemas;
    }

    public List<DatabaseSchema> getDatabaseSchemas() {
        return databaseSchemas;
    }

    public Set<Class<?>> getTables() {
        return tables;
    }
}
