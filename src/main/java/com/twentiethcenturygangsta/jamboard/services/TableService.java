package com.twentiethcenturygangsta.jamboard.services;

import com.twentiethcenturygangsta.jamboard.dto.Table;
import com.twentiethcenturygangsta.jamboard.repository.ListRepository;
import com.twentiethcenturygangsta.jamboard.site.JamBoardClient;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.sql.SQLException;

@Service
@RequiredArgsConstructor
public class TableService {
    private final JamBoardClient jamBoardClient;
    private final ListRepository listRepository;

    public Table getTableData(String tableName) throws SQLException {
        return listRepository.findAll(tableName, jamBoardClient.getConnection());
    }
}
