package com.korea.test.Note;

import com.korea.test.post.Post;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
public class Note {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private LocalDateTime createDate;

    @OneToMany(mappedBy = "note", cascade = CascadeType.REMOVE)
    private List<Post> postList;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.REMOVE)
    private List<Note> childList;

    @ManyToOne
    private Note parent;
}
