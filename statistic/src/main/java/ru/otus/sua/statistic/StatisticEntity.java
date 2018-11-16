package ru.otus.sua.statistic;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;

import javax.persistence.*;
import javax.servlet.http.Cookie;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Indexed
@Table(name = "statistic")
public class StatisticEntity {
    @Id
    @GeneratedValue
    private long id;

    @Basic
    @Field
    private String marker;

    @Basic
    @Field
    private String page;

    @Basic
    @Field
    private String ipaddress;

    @Basic
    @Field
    private String browser;

    @Lob
    private String userAgent;

    @Basic
    private long clientTimestamp;

    @Basic
    private long clientTZOffset;

    @Basic
    private long serverTimestamp;

    @Basic
    @Field
    private String login;

    @ElementCollection
    private List<Cookie> cookies;

    @Basic
    @Field
    private String session;

    @Basic
    private long prevId;

}
