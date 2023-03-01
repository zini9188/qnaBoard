package com.qnaBoard.post.entity;

import com.qnaBoard.member.entity.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class PostLike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long likeId;
    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;
    @ManyToOne
    @JoinColumn(name = "POST_ID")
    private Post post;
    private LocalDateTime createdAt = LocalDateTime.now();

    public void addMember(Member member) {
        if (this.member != member) {
            this.member = member;
        }
    }

    public void addPost(Post post) {
        if (this.post != post) {
            this.post = post;
        }
    }
}
