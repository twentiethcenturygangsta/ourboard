package com.twentiethcenturygangsta.ourboard.services;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.twentiethcenturygangsta.ourboard.config.EncryptionConfig;
import com.twentiethcenturygangsta.ourboard.dto.Entity;
import com.twentiethcenturygangsta.ourboard.dto.FieldInfo;
import com.twentiethcenturygangsta.ourboard.dto.TablesInfo;
import com.twentiethcenturygangsta.ourboard.site.DatabaseClient;
import com.twentiethcenturygangsta.ourboard.annoatation.OurBoardEntity;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.*;
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
    private final ApplicationContext appContext;

    public Object getObjects(String tableName) {
        return getRepository(tableName).findAll();
    }

    public Optional<Object> getObject(String entity, Long id) {
        return getRepository(entity).findById(id);
    }

    public Object createObject(HashMap<String, Object> data, String tableName) throws Exception {
        Class<?> entity = getEntity(tableName);
        ObjectMapper mapper = new ObjectMapper();
        if (tableName.equals("OUR_BOARD_MEMBER")) {
            data = getOurBoardMemberData(data);
        }
        Object object = mapper.convertValue(data, entity);
        JpaRepository jpaRepository = getRepository(tableName);
        Object instance = jpaRepository.save(object);
        return instance;
    }

    public void deleteObjects(String tableName, HashMap<String, List<Long>> data) {
        JpaRepository jpaRepository = getRepository(tableName);
        jpaRepository.deleteAllByIdInBatch(data.get("ids"));
    }

    public Object updateObject(HashMap<String, Object> data, String tableName, Long id) throws Exception {
        Class<?> entity = getEntity(tableName);
        ObjectMapper mapper = new ObjectMapper();
        if (tableName.equals("OUR_BOARD_MEMBER")) {
            data = getOurBoardMemberData(data);
        }

        Object object = mapper.convertValue(data, entity);
        JpaRepository jpaRepository = getRepository(tableName);
        Object instance = jpaRepository.save(object);
        return instance;
    }

    public Page<Object> searchObjects(String searchKeyword, String searchType, String tableName, Pageable pageable) {

        Entity entity = databaseClient.getEntities().get(tableName);

        int page = (pageable.getPageNumber() == 0) ? 0 : (pageable.getPageNumber() - 1);
        pageable= PageRequest.of(page,2, Sort.by("id").descending());


        JpaRepository jpaRepository = getRepository(tableName);
        if (searchType.equals("ALL")) {
            return jpaRepository.findAll(pageable);
        }

        ObjectMapper mapper = new ObjectMapper();

        HashMap<String, Object> data = new HashMap();
        data.put(searchType, searchKeyword);
        Object object = mapper.convertValue(data, entity.getEntityClass());
        ExampleMatcher matcher = ExampleMatcher
                .matchingAll()
                .withMatcher(searchType, ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase());

        return jpaRepository.findAll(Example.of(object, matcher), pageable);
    }

    public LinkedHashMap<String, FieldInfo> getFields(String tableName) {
        return databaseClient.getFields(tableName);
    }

    public HashMap<String, ArrayList<TablesInfo>> getTableSimpleNames() throws SQLException {
        HashMap<String, ArrayList<TablesInfo>> dict = new HashMap<>();
        HashMap<String, Entity> entities = databaseClient.getEntities();

        for (Map.Entry<String, Entity> entity : entities.entrySet()) {

            for (Annotation annotation : entity.getValue().getEntityClass().getAnnotations()) {
                if (annotation instanceof OurBoardEntity myAnnotation) {
                    if (dict.containsKey(myAnnotation.group())) {
                        dict.get(myAnnotation.group()).add(
                                TablesInfo.builder()
                                        .tableName(entity.getKey())
                                        .entityClassName(entity.getValue().getEntityClass().getSimpleName())
                                        .description(myAnnotation.description())
                                        .build()
                        );
                    } else {
                        dict.put(myAnnotation.group(), new ArrayList<>());
                        dict.get(myAnnotation.group()).add(
                                TablesInfo.builder()
                                        .tableName(entity.getKey())
                                        .entityClassName(entity.getValue().getEntityClass().getSimpleName())
                                        .description(myAnnotation.description())
                                        .build()
                        );
                    }
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
        HashMap<String, Entity> entities = databaseClient.getEntities();
        for (Map.Entry<String, Entity> entity : entities.entrySet()) {
            if (entityName.equals(entity.getKey())) {
                Repositories repositories = new Repositories(appContext);
                repo = (JpaRepository) repositories.
                        getRepositoryFor(entity.getValue().getEntityClass()).get();
            }
        }
        return repo;
    }

    private Class<?> getEntity(String tableName) {
        return databaseClient.getEntities().get(tableName).getEntityClass();
    }

    private HashMap<String, Object> getOurBoardMemberData(HashMap<String, Object> data) throws Exception {
        data.replace("password", EncryptionConfig.encrypt(String.valueOf(data.get("password"))));
        return data;
    }
}
