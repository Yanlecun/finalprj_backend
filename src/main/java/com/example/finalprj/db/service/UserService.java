package com.example.finalprj.db.service;

import com.example.finalprj.db.domain.Playground;
import com.example.finalprj.db.repository.PlaygroundRepository;
import com.example.finalprj.db.domain.Authority;
import com.example.finalprj.db.domain.User;
import com.example.finalprj.db.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final PlaygroundRepository playgroundRepository;

    public User save(User user) {
        return userRepository.save(user);
    }

    public void addAuthority(Long userId, String authority){
        userRepository.findById(userId).ifPresent(user-> {
            Authority newRole = new Authority(user.getId(), authority);
            if (user.getAuthorities() == null) {
                HashSet<Authority> authorities = new HashSet<>();
                authorities.add(newRole);
                user.setAuthorities(authorities);
                save(user);
            };
        });
    }

    public Optional<Playground> findPlaygroundByPgName(String name) {
        return playgroundRepository.findPlaygroundByPgName(name);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public List<User> findAllManager() {
        return userRepository.findAllByPlaygroundIsNotNull(); // 놀이터를 관리 id가 있으면
    }

    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }
}
