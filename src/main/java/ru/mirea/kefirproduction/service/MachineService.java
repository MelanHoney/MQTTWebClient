package ru.mirea.kefirproduction.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mirea.kefirproduction.model.Machine;
import ru.mirea.kefirproduction.model.MachineType;
import ru.mirea.kefirproduction.repository.MachineRepository;
import ru.mirea.kefirproduction.repository.MachineTypeRepository;

@Service
@RequiredArgsConstructor
public class MachineService {
    private final MachineRepository machineRepository;
    private final MachineTypeRepository machineTypeRepository;

    @Transactional(readOnly = true)
    public Machine findMachine(String name) {
        return machineRepository.findByName(name).orElse(null);
    }

    @Transactional
    public Machine save(String machineName) {
        String typeName = machineName.replaceAll("\\s*№\\d+$", "");
        MachineType type = machineTypeRepository.findByName(typeName).orElse(null);
        if (type == null) {
            type = MachineType.builder()
                    .name(typeName)
                    .build();
            machineTypeRepository.save(type);
        }
        Machine machine = Machine.builder()
                .name(machineName)
                .machineType(type)
                .build();
        return machineRepository.save(machine);
    }
}
