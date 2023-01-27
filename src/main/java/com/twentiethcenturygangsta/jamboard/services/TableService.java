package com.twentiethcenturygangsta.jamboard.services;

import com.twentiethcenturygangsta.jamboard.site.JamBoardClient;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class TableService {
    private final JamBoardClient jamBoardClient;

    public ArrayList<String> getTables() {
        ArrayList<String> tableSet = new ArrayList<>();
        for (Class table : jamBoardClient.getTables()) {
            tableSet.add(table.getSimpleName());
        }
        return tableSet;
    }
}
