package com.example.finalprj.db.service;

import com.example.finalprj.db.domain.Playground;
import com.example.finalprj.db.repository.PlaygroundRepository;
import com.example.finalprj.db.domain.Authority;
import com.example.finalprj.db.domain.User;
import com.example.finalprj.db.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
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

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }


    public Long findIdByEmail(String email) {
        System.out.println();
        return userRepository.findIdByEmail(email);
    }

    public String getJson(User user) {
        JSONObject json1 = new JSONObject();
        ArrayList<JSONObject> array = new ArrayList<>();

        JSONObject json = new JSONObject();
        json.put("createdAt", user.getCreatedAt().toString());
        json.put("updatedAt", user.getUpdatedAt().toString());
        json.put("id", user.getId());
        json.put("name", user.getName());
        json.put("email", user.getEmail());
        json.put("dog_num", user.getDogNum());

        array.add(json);
        json1.put("data", array);

        return json1.toString();
    }
}
