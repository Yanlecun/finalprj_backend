package com.example.finalprj.db.repository;

import com.example.finalprj.db.domain.Entry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface EntryRepository extends JpaRepository<Entry, Long> {

    @Query("select userId from Entry where status = :status and playgroundId = :playgroundId order by createdAt desc")
    List<Long> findReservation(int status, long playgroundId);

    @Query("delete from Entry where status = :status and playgroundId = :playgroundId and userId = :userId")
    @Transactional
    @Modifying(clearAutomatically = true)
    void deleteByUserIdStatusEqual(long userId, int status, long playgroundId);

    @Query("select e from Entry as e where e.status = :status and e.playgroundId = :playgroundId and e.userId = :userId")
    Optional<Entry> findByUserIdStatusEqual(long userId, int status, long playgroundId);


    @Query("update Entry e SET e.status = 2 where e.status = 1 and e.playgroundId = :playgroundId and e.userId = :userId")
    @Modifying
    @Transactional
    void updateStatusRevToUsing(long userId, long playgroundId);
}
