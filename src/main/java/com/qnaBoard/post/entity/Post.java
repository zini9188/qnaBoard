package com.qnaBoard.post.entity;

import com.qnaBoard.answer.entity.Answer;
import com.qnaBoard.member.entity.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;
    @Column(nullable = false, length = 40)
    private String title;
    @Column(nullable = false, length = 60)
    private String content;
    @Enumerated(EnumType.STRING)
    private PostStatus postStatus = PostStatus.QUESTION_REGISTRATION;
    @Enumerated(EnumType.STRING)
    private Access access = Access.PUBLIC;
    @Column(nullable = false)
    private Integer view = 0;
    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ANSWER_ID")
    private Answer answer;
    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    public enum PostStatus {
        QUESTION_REGISTRATION("질문 등록"),
        QUESTION_ANSWERED("답변 완료"),
        QUESTION_DELETE("질문 삭제");
        private String status;

        PostStatus(String status) {
            this.status = status;
        }
    }

    public enum Access {
        PUBLIC("공개"),
        SECRET("비공개");
        private final String status;

        Access(String status) {
            this.status = status;
        }
    }
}
