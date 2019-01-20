package com.pandora.spring.model;

import lombok.Data;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.List;

@Data
@XmlRootElement(name = "gameList")
public class GameList {

    private List<Game> game;

    @Data
    @XmlRootElement(name = "game")
    @XmlType(propOrder = {"id", "source", "path", "name", "desc", "image", "marquee", "video", "thumbnail", "rating", "releasedate", "developer", "publisher", "genre", "players"})
    public static class Game {

        private String id;
        private String source;

        private String path;
        private String name;
        private String desc;
        private String image;
        private String marquee;
        private String video;
        private String thumbnail;
        private String rating;
        private String releasedate;
        private String developer;
        private String publisher;
        private String genre;
        private String players;

        @XmlAttribute
        public String getId() {
            return id;
        }

        @XmlAttribute
        public String getSource() {
            return source;
        }

    }
}
