package com.example.finalprj.db.faq.repository;

import com.example.finalprj.db.faq.domain.Faq;
import com.example.finalprj.db.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FaqRepository extends JpaRepository<Faq, Long> {

}
