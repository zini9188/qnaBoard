package com.qnaBoard.like.entity;

import com.qnaBoard.entity.BaseEntity;
import com.qnaBoard.member.entity.Member;
import com.qnaBoard.question.entity.Question;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity(name = "LIKES")
@Getter
@Setter
@NoArgsConstructor
public class Like extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long likeId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "QUESTION_ID")
    private Question question;

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
