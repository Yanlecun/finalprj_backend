package com.example.finalprj.db.user.service;

import com.example.finalprj.db.playground.domain.Playground;
import com.example.finalprj.db.playground.repository.PlaygroundRepository;
import com.example.finalprj.db.user.domain.Authority;
import com.example.finalprj.db.user.domain.User;
import com.example.finalprj.db.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
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
}
