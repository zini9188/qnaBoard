package com.qnaBoard.answer.entity;

import com.qnaBoard.post.entity.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long answerId;
    @Column(nullable = false)
    private String content;
    private Post.Access access;
    private LocalDateTime createdAt = LocalDateTime.now();
    @OneToOne(mappedBy = "answer", cascade = CascadeType.ALL)
    private Post post;
}
