package com.qnaBoard.question.entity;

import com.qnaBoard.answer.entity.Answer;
import com.qnaBoard.entity.BaseEntity;
import com.qnaBoard.like.entity.Like;
import com.qnaBoard.member.entity.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Sort;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class Question extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long questionId;
    @Column(nullable = false, length = 40)
    private String title;
    @Column(nullable = false, length = 60)
    private String content;
    @Enumerated(EnumType.STRING)
    private QuestionStatus questionStatus = QuestionStatus.QUESTION_REGISTRATION;
    @Enumerated(EnumType.STRING)
    private Access access = Access.PUBLIC;
    @Column(nullable = false)
    private Integer view = 0;
    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "ANSWER_ID")
    private Answer answer;
    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;
    @OneToMany(mappedBy = "question", cascade = CascadeType.PERSIST)
    private List<Like> likes = new ArrayList<>();

    public void addMember(Member member) {
        if (this.member != member) {
            this.member = member;
        }
    }

    public enum QuestionStatus {
        QUESTION_REGISTRATION("질문 등록"),
        QUESTION_ANSWERED("답변 완료"),
        QUESTION_DELETE("질문 삭제");
        @Getter
        private final String status;

        QuestionStatus(String status) {
            this.status = status;
        }
    }

    public enum Access {
        PUBLIC("공개"),
        SECRET("비공개");
        @Getter
        private final String status;

        Access(String status) {
            this.status = status;
        }
    }
    @Getter
    public enum SortType {
        NEWEST("createAt", Sort.Direction.DESC),
        OLDEST("createAt", Sort.Direction.ASC),
        MOST_LIKE("likes", Sort.Direction.DESC),
        LESS_LIKE("likes", Sort.Direction.ASC),
        MOST_VIEW("view", Sort.Direction.DESC),
        LESS_VIEW("view", Sort.Direction.ASC);

        SortType(String column, Sort.Direction direction) {
            this.column = column;
            this.direction = direction;
        }

        private final String column;
        private final Sort.Direction direction;
    }
}
