package com.twentiethcenturygangsta.ourboard.services;

import com.twentiethcenturygangsta.ourboard.dto.Table;
import com.twentiethcenturygangsta.ourboard.dto.TablesInfo;
import com.twentiethcenturygangsta.ourboard.repository.ListRepository;
import com.twentiethcenturygangsta.ourboard.site.OurBoardClient;
import com.twentiethcenturygangsta.ourboard.annoatation.OurBoardEntity;
import com.twentiethcenturygangsta.ourboard.trace.Trace;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.lang.annotation.Annotation;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

@Service
@Slf4j
@RequiredArgsConstructor
public class TableService {
    private final OurBoardClient ourBoardClient;
    private final ListRepository listRepository;

    @Trace
    public Table getTableData(String tableName) throws SQLException {
        return listRepository.findAll(tableName, ourBoardClient.getConnection());
    }

    public HashMap<String, ArrayList<TablesInfo>> getTableSimpleNames() throws SQLException {
        HashMap<String, ArrayList<TablesInfo>> dict = new HashMap<>();

        for (Class<?> table : ourBoardClient.getTables()) {

            log.info("annotation = {}", table.getAnnotation(OurBoardEntity.class));

            for (Annotation annotation : table.getAnnotations()) {
                if (annotation instanceof OurBoardEntity myAnnotation) {
                    if (dict.containsKey(myAnnotation.group())) {
                        dict.get(myAnnotation.group()).add(
                                TablesInfo.builder()
                                        .tableName(camelToSnakeDatabaseTableName(table.getSimpleName()))
                                        .entityClassName(table.getSimpleName())
                                        .description(myAnnotation.description())
                                        .build()
                        );
                    } else {
                        dict.put(myAnnotation.group(), new ArrayList<>());
                        dict.get(myAnnotation.group()).add(
                                TablesInfo.builder()
                                        .tableName(camelToSnakeDatabaseTableName(table.getSimpleName()))
                                        .entityClassName(table.getSimpleName())
                                        .description(myAnnotation.description())
                                        .build()
                        );
                    }
                    log.info("name: {}" + myAnnotation.group());
                    log.info("value: {}" + myAnnotation.description());
                }
            }
        }
        log.info("result = {}", dict);
        return dict;
    }

    private String camelToSnakeDatabaseTableName(String camel) {
        String tableName = "";

        for(int i = 0; i < camel.length(); i++) {
            if(i == 0) {
                tableName = tableName + camel.charAt(0);
            } else {
                if (Character.isUpperCase(camel.charAt(i))) {
                    tableName = tableName + "_";
                    tableName = tableName + camel.charAt(i);
                } else {
                    tableName = tableName + Character.toUpperCase(camel.charAt(i));
                }
            }
        }
        return tableName;
    }
}
