package priyopujonggo.restful.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import priyopujonggo.restful.entity.Log;
import priyopujonggo.restful.service.LogService;

@RestController
@RequestMapping("/api/logs")
public class LogController {
 
    @Autowired
    private LogService logService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Log> getAllLogs() {
        return logService.getAllLogs();
    }
}
