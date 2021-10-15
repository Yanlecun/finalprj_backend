package com.example.finalprj.api.controller;

import com.example.finalprj.api.dao.PhotoDao;
import com.example.finalprj.api.dao.TrashDao;
import com.example.finalprj.api.dto.Photo;
import com.example.finalprj.api.dto.Trash;
import com.example.finalprj.db.service.PlaygroundService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.sql.SQLException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ApiController {
    private final PlaygroundService playgroundService;

    @PostMapping("/photo")
    public void insert(@RequestBody Photo photo) {
        PhotoDao photoDao = new PhotoDao();
        photoDao.photoInsert(photo);
    }

    @GetMapping("/photo")
    public String getList() {
        PhotoDao photoDao = new PhotoDao();
        return photoDao.json();
    }

    @GetMapping("/trash/get")
    public String getTrash() {
        TrashDao dao = new TrashDao();

        return dao.json();
    }

    @GetMapping("/trash/put")
    public void updateTrash(@RequestParam Integer status, @RequestParam Long id) {
        Trash trash = Trash.builder()
                .id(id)
                .status(status)
                .build();

        TrashDao dao = new TrashDao();
        dao.updateTrash(trash);
    }


    @GetMapping("/playground/get")
    public String getGeo() {
        return playgroundService.json();
    }

    @GetMapping("/playground/put")
    public String setGeo() throws IOException, ParserConfigurationException, SAXException, SQLException, ClassNotFoundException {
        return playgroundService.playgroundInsert() +"건 실행 완료 !";
    }

    @GetMapping("/playground/delete")
    public String deleteGeo() {
        return playgroundService.deleteAll() +"건 실행 완료 !";
    }


}
