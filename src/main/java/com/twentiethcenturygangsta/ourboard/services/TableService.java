package com.twentiethcenturygangsta.ourboard.services;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.twentiethcenturygangsta.ourboard.config.EncryptionConfig;
import com.twentiethcenturygangsta.ourboard.dto.FieldInfo;
import com.twentiethcenturygangsta.ourboard.dto.Table;
import com.twentiethcenturygangsta.ourboard.dto.TablesInfo;
import com.twentiethcenturygangsta.ourboard.repository.ListRepository;
import com.twentiethcenturygangsta.ourboard.site.DatabaseClient;
import com.twentiethcenturygangsta.ourboard.site.OurBoardClient;
import com.twentiethcenturygangsta.ourboard.annoatation.OurBoardEntity;
import com.twentiethcenturygangsta.ourboard.trace.Trace;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    public Page<Object> getObjects(String entity, Pageable pageable) {
        int page = (pageable.getPageNumber() == 0) ? 0 : (pageable.getPageNumber() - 1);
        pageable= PageRequest.of(page,2, Sort.by("id").descending());
        return getRepository(entity).findAll(pageable);
    }

    public Optional<Object> getObject(String entity, Long id) {
        return getRepository(entity).findById(id);
    }

    public Object createObject(HashMap<String, Object> data, String tableName) throws Exception {
        Class<?> entity = databaseClient.getEntities().get(tableName);
        ObjectMapper mapper = new ObjectMapper();
        if (tableName.equals("OUR_BOARD_MEMBER")) {
            data = getOurBoardMemberData(data);
        }
        Object object = mapper.convertValue(data, entity);
        log.info("object = {}", object);
        JpaRepository jpaRepository = getRepository(tableName);
        Object instance = jpaRepository.save(object);
        return instance;
    }

    public Object updateObject(HashMap<String, Object> data, String tableName, Long id) throws Exception {
        Class<?> entity = databaseClient.getEntities().get(tableName);
        ObjectMapper mapper = new ObjectMapper();
        if (tableName.equals("OUR_BOARD_MEMBER")) {
            data = getOurBoardMemberData(data);
        }
        Object object = mapper.convertValue(data, entity);
        log.info("object = {}", object);
        JpaRepository jpaRepository = getRepository(tableName);
        Object instance = jpaRepository.save(object);
        return instance;
    }

    @Trace
    @Deprecated
    public Table getTableData(String tableName) throws SQLException {
        return listRepository.findAll(tableName, ourBoardClient.getConnection());
    }

    public LinkedHashMap<String, FieldInfo> getFields(String tableName) {
        return databaseClient.getFields(tableName);
    }

    public HashMap<String, ArrayList<TablesInfo>> getTableSimpleNames() throws SQLException {
        HashMap<String, ArrayList<TablesInfo>> dict = new HashMap<>();
        HashMap<String, Class<?>> entities = databaseClient.getEntities();

        for (Map.Entry<String, Class<?>> entity : entities.entrySet()) {

            for (Annotation annotation : entity.getValue().getAnnotations()) {
                if (annotation instanceof OurBoardEntity myAnnotation) {
                    if (dict.containsKey(myAnnotation.group())) {
                        dict.get(myAnnotation.group()).add(
                                TablesInfo.builder()
                                        .tableName(entity.getKey())
                                        .entityClassName(entity.getValue().getSimpleName())
                                        .description(myAnnotation.description())
                                        .build()
                        );
                    } else {
                        dict.put(myAnnotation.group(), new ArrayList<>());
                        dict.get(myAnnotation.group()).add(
                                TablesInfo.builder()
                                        .tableName(entity.getKey())
                                        .entityClassName(entity.getValue().getSimpleName())
                                        .description(myAnnotation.description())
                                        .build()
                        );
                    }
                    log.info("name: {}" + myAnnotation.group());
                    log.info("value: {}" + myAnnotation.description());
                }
            }
        }
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

    private JpaRepository getRepository(String entityName) {
        JpaRepository repo = null;
        HashMap<String, Class<?>> entities = databaseClient.getEntities();
        for (Map.Entry<String, Class<?>> entity : entities.entrySet()) {
            if (entityName.equals(entity.getKey())) {
                Repositories repositories = new Repositories(appContext);
                repo = (JpaRepository) repositories.
                        getRepositoryFor(entity.getValue()).get();
            }
        }
        return repo;
    }

    private HashMap<String, Object> getOurBoardMemberData(HashMap<String, Object> data) throws Exception {
        data.replace("password", EncryptionConfig.encrypt(String.valueOf(data.get("password"))));
        return data;
    }
}
