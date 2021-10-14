package com.example.finalprj.db.service;

import com.example.finalprj.db.repository.EntryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EntryService {
    private final EntryRepository entryRepository;

    public List<Long> findAllById(int status, Long playground_id) {
        return entryRepository.findReservation(status, playground_id);
    }

    public void deleteUserIdStatusEquals(long userId, int i, long playgroundId) {
        try {
            entryRepository.deleteByUserIdStatusEqual(userId, i, playgroundId);
        } catch (Exception e) {
            System.out.println("아직 없음");
        }
        ;
    }

    public void entryUser(long userId, long playgroundId) {
        entryRepository.updateStatusRevToUsing(userId, playgroundId);
    }
}
