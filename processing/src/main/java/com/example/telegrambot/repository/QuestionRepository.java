package com.example.telegrambot.repository;

import com.example.telegrambot.model.question.CheckList;
import com.example.telegrambot.model.question.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface QuestionRepository extends JpaRepository<Question, Integer> {

    Optional<Question> getQuestionById(int id);

    Optional<Question> getFirstByCheckListAndOrderIsAfter(CheckList checkList, int oeder);

    @Query(value = "select * from question q inner join check_list ch on q.check_list_id=ch.id where ch.type = :type order by q.id limit 1", nativeQuery = true)
    Optional<Question> getFirstByCheckListType(String type);
}
