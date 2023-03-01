package com.qnaBoard.member.entity;

import com.qnaBoard.post.entity.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;
    @Column(length = 30, nullable = false, unique = true, updatable = false)
    private String email;
    @Column(length = 15, nullable = false, unique = true)
    private String nickname;
    @Column(length = 12, nullable = false)
    private String username;
    @Column(length = 30, nullable = false)
    private String password;
    @Column(nullable = false)
    private MemberStatus memberStatus = MemberStatus.MEMBER_ACTIVE;
    @OneToMany(mappedBy = "member")
    private List<Post> posts = new ArrayList<>();
    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    public void addPost(Post post) {
        if (!posts.contains(post)) {
            posts.add(post);
        }
    }

    private enum MemberStatus {
        MEMBER_ACTIVE("ACTIVE"),
        MEMBER_DISABLE("DISABLE");
        private final String status;

        MemberStatus(String status) {
            this.status = status;
        }
    }
}
