package priyopujonggo.restful.entity;
import java.time.LocalDateTime;

import jakarta.persistence.Table;
import jakarta.persistence.Id;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "logs")
public class Log {
    @Id
    private String id;
    
    private String email;

    @Column(name = "verse")
    private String ayahSurah;

    @Column(name = "timestamp")
    private LocalDateTime datetime;


}
