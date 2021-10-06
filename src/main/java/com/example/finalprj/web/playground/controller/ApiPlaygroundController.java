package com.example.finalprj.web.playground.controller;

import com.example.finalprj.db.playground.service.PlaygroundService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.sql.SQLException;

@RestController
@RequestMapping("/playground")
@RequiredArgsConstructor
public class ApiPlaygroundController {
    private final PlaygroundService playgroundService;

    @GetMapping("/geo/set")
    public String setGeo() throws IOException, ParserConfigurationException, SAXException, SQLException, ClassNotFoundException {
        return playgroundService.playgroundInsert() +"건 실행 완료 !";
    }
    @GetMapping("/geo/get")
    public String getGeo() {
        return playgroundService.json();
    }

    @GetMapping("/geo/delete")
    public String deleteGeo() {
        return playgroundService.deleteAll() +"건 실행 완료 !";
    }
}
