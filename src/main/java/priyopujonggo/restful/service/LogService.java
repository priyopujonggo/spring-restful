package priyopujonggo.restful.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import priyopujonggo.restful.entity.Log;
import priyopujonggo.restful.model.CreateLogRequest;
import priyopujonggo.restful.model.LogResponse;
import priyopujonggo.restful.repository.LogRepository;

@Service
public class LogService {

    @Autowired
    private LogRepository logRepository;

    @Autowired
    private ValidationService validationService;

    @Transactional
    public LogResponse create(CreateLogRequest request){
        validationService.validate(request);

        Log logg = new Log();
        logg.setId(UUID.randomUUID().toString());
        logg.setEmail(request.getEmail());
        logg.setAyahSurah(request.getAyahSurah());
        logg.setDatetime(request.getDatetime());
        logRepository.save(logg);

        return toLogResponse(logg);

    }

    private LogResponse toLogResponse(Log logg) {
        return LogResponse.builder()
                .id(logg.getId())
                .email(logg.getEmail())
                .ayahSurah(logg.getAyahSurah())
                .datetime(logg.getDatetime())
                .build();
    }

    public List<Log> getAllLogs() {
        return logRepository.findAll();
    }

    
}
