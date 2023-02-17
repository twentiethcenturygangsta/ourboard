package com.twentiethcenturygangsta.ourboard.services;

import com.twentiethcenturygangsta.ourboard.dto.Table;
import com.twentiethcenturygangsta.ourboard.dto.TablesInfo;
import com.twentiethcenturygangsta.ourboard.repository.ListRepository;
import com.twentiethcenturygangsta.ourboard.site.DatabaseClient;
import com.twentiethcenturygangsta.ourboard.site.OurBoardClient;
import com.twentiethcenturygangsta.ourboard.annoatation.OurBoardEntity;
import com.twentiethcenturygangsta.ourboard.trace.Trace;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.support.Repositories;
import org.springframework.stereotype.Service;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.*;


@Service
@Slf4j
@RequiredArgsConstructor
public class TableService {
    private final DatabaseClient databaseClient;
    private final OurBoardClient ourBoardClient;
    private final ListRepository listRepository;
    private final ApplicationContext appContext;

    public Page<Object> getObjects(String entity, int page) {
        return getRepository(entity).findAll(PageRequest.of(page, 10, Sort.by("id").descending()));
    }

    public List<String> getFields(String tableName) {
        List<String> fields = new ArrayList();
        for (Class<?> table : databaseClient.getTables()) {
            if (tableName.equals(camelToSnakeDatabaseTableName(table.getSimpleName()))) {
                for(Field field : table.getDeclaredFields()) {
                    fields.add(field.getName());
                }
            }
        }
        log.info("fields = {}", fields);
        return fields;
    }

    @Trace
    @Deprecated
    public Table getTableData(String tableName) throws SQLException {
        return listRepository.findAll(tableName, ourBoardClient.getConnection());
    }

    public HashMap<String, ArrayList<TablesInfo>> getTableSimpleNames() throws SQLException {
        HashMap<String, ArrayList<TablesInfo>> dict = new HashMap<>();

        for (Class<?> table : databaseClient.getTables()) {

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

    public Object getFieldValue( Object root, String fieldName ) {
        try {
            Field field = root.getClass().getDeclaredField( fieldName );
            Method getter = root.getClass().getDeclaredMethod(
                    (field.getType().equals( boolean.class ) ? "is" : "get")
                            + field.getName().substring(0, 1).toUpperCase( Locale.ROOT)
                            + field.getName().substring(1)
            );

            return getter.invoke(root);
        } catch (Exception e) {
            // log exception
        }
       return null;
    }

    private JpaRepository getRepository(String entity) {
        JpaRepository repo = null;

        for (Class<?> table : databaseClient.getTables()) {
            if (entity.equals(camelToSnakeDatabaseTableName(table.getSimpleName()))) {
                Repositories repositories = new Repositories(appContext);
                repo = (JpaRepository) repositories.
                        getRepositoryFor(table).get();
            }
        }
        return repo;
    }

    private String camelToSnakeDatabaseTableName(String camel) {
        String tableName = "";

        for(int i = 0; i < camel.length(); i++) {
            if(i == 0) {
                tableName = tableName + Character.toUpperCase(camel.charAt(0));
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
