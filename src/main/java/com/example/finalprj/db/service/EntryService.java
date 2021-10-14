package com.example.finalprj.db.service;

import com.example.finalprj.db.domain.Entry;
import com.example.finalprj.db.repository.EntryRepository;
import com.example.finalprj.db.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EntryService {
    private final EntryRepository entryRepository;
    private final UserRepository userRepository;

    public List<Long> findAllById(long playground_id, int status) {
        return entryRepository.findReservation(playground_id, status);
    }

    @Transactional
    public void deleteUserIdStatusEquals(long userId, long playgroundId, int status) {
        try {
            entryRepository.deleteByUserIdStatusEqual(userId, playgroundId, status);
        } catch (Exception e) {
            System.out.println("아직 없음");
        }
        ;
    }

    public void entryUser(long userId, long playgroundId) {
        entryRepository.updateStatusRevToUsing(userId, playgroundId);
    }

    public void exitUser(long userId, long playgroundId) {
        Entry entryTemp = entryRepository.findEntryWhereUserPlaygroundStatus(userId, playgroundId, 2).orElse(null);

        entryRepository.saveNative(userId, playgroundId, 3,entryTemp.getUpdatedAt());

        deleteUserIdStatusEquals(userId, playgroundId, 2);  // 삭제


    }
}
