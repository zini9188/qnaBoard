package com.qnaBoard.question.entity;

import com.qnaBoard.member.entity.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity(name = "LIKES")
@Getter
@Setter
@NoArgsConstructor
public class Like {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long likeId;
    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;
    @ManyToOne
    @JoinColumn(name = "QUESTION_ID")
    private Question question;
    private LocalDateTime createdAt = LocalDateTime.now();

    public void addMember(Member member) {
        if (this.member != member) {
            this.member = member;
        }
    }

    public void addQuestion(Question question) {
        if (this.question != question) {
            this.question = question;
        }
    }
}
