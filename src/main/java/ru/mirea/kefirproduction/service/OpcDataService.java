package ru.mirea.kefirproduction.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.mirea.kefirproduction.model.OpcData;
import ru.mirea.kefirproduction.repository.OpcDataRepository;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class OpcDataService {
    private final OpcDataRepository opcDataRepository;

    public void saveOpcData(String nodeId, Object value) {
        OpcData opcData = OpcData.builder()
                .nodeId(nodeId)
                .value(value.toString())
                .timestamp(LocalDateTime.now())
                .build();
        opcDataRepository.save(opcData);
    }
}
