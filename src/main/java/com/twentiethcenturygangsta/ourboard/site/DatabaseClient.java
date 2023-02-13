package com.twentiethcenturygangsta.ourboard.site;

import com.twentiethcenturygangsta.ourboard.dto.DatabaseSchema;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Configuration
@Slf4j
@RequiredArgsConstructor
public class DatabaseClient {
    private final OurBoardClient ourBoardClient;
    private List<DatabaseSchema> databaseSchemas;

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
}
