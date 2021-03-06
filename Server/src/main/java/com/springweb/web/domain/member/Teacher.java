package com.springweb.web.domain.member;

import com.springweb.web.domain.evaluation.Evaluation;
import com.springweb.web.domain.lesson.Lesson;
import com.springweb.web.domain.report.WarningConst;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
//@AttributeOverride(name = "id", column = @Column(name = "TEACHER_ID"))
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorValue("T")
public class Teacher extends Member{

    private String career;//경력

    @OneToMany(mappedBy = "teacher", cascade = CascadeType.ALL, orphanRemoval = true)//선생이 탈퇴하면 선생에 대한 평가도 사라지는게 맞다.
    private List<Evaluation> evaluationList = new ArrayList<>();//선생님에 대한 평가, 별점 + 평가내용

    private double starPoint; //선생님이 받은 별점의 평균



    private boolean isBlack =false; //블랙리스트에 등록되었는지
    private int warningCount;//경고 횟수



    @OneToMany(mappedBy = "teacher", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Lesson> lessonList = new ArrayList<>();//선생님이 탈퇴하면 연관된 강의도 모두 삭제












    @Builder
    public Teacher(Long id, String username, String password, String name, int age, String profileImgPath, Role role, Long kakaoId, boolean isKakaoMember, boolean activated, String career) {
        super(id, username, password, name, age, profileImgPath, role, kakaoId, isKakaoMember, activated);
        this.career = career;
    }









    //== 정보 수정 ==//
    public void changeCareer(String career){
        this.career = career;
    }


    public void addLesson(Lesson lesson){
        lessonList.add(lesson);
    }

    public void addEvaluation(Evaluation evaluation){
        evaluationList.add(evaluation);

        DecimalFormat df = new DecimalFormat("0.0");
        starPoint = Double.parseDouble(df.format((evaluation.getStarPoint()+(starPoint*evaluationList.size()-1))/evaluationList.size()));
    }




    //== 블랙리스트 기능 ==//
    public void makeBlack(){
        this.isBlack = true;
    }

    public void makeWhite(){
        this.isBlack = false;
        this.warningCount=0;
    }

    public void addWarning(){
        this.warningCount ++;
        if(warningCount >= WarningConst.MAX_WARNING_COUNT){
            makeBlack();
        }
    }
}
