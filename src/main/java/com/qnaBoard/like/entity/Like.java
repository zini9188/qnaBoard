package com.qnaBoard.like.entity;

import com.qnaBoard.member.entity.Member;
import com.qnaBoard.question.entity.Question;
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
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "QUESTION_ID")
    private Question question;
    private LocalDateTime createdAt = LocalDateTime.now();

    public static Like of(Member member, Question question) {
        Like like = new Like();
        like.addMember(member);
        like.addQuestion(question);
        return like;
    }

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
