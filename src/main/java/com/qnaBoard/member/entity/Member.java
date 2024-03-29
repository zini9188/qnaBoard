package com.qnaBoard.member.entity;

import com.qnaBoard.entity.BaseEntity;
import com.qnaBoard.question.entity.Question;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;
    @Column(length = 30, nullable = false, unique = true, updatable = false)
    private String email;
    @Column(length = 15, nullable = false)
    private String nickname;
    @Column(length = 12, nullable = false)
    private String username;
    @Column(length = 255, nullable = false)
    private String password;
    @Column(nullable = false)
    private MemberStatus memberStatus = MemberStatus.MEMBER_ACTIVE;
    @OneToMany(mappedBy = "member")
    private List<Question> questions = new ArrayList<>();
    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> roles = new ArrayList<>();

    public void addQuestion(Question question) {
        if (!questions.contains(question)) {
            questions.add(question);
        }
    }

    public enum MemberStatus {
        MEMBER_ACTIVE("ACTIVE"),
        MEMBER_DISABLE("DISABLE");
        @Getter
        private final String status;

        MemberStatus(String status) {
            this.status = status;
        }
    }
}
