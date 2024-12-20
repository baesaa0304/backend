package com.woozuda.backend.diary.repository;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLTemplates;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.woozuda.backend.diary.dto.response.DiaryNameListResponseDto;
import com.woozuda.backend.diary.dto.response.DiaryNameResponseDto;
import com.woozuda.backend.diary.dto.response.SingleDiaryResponseDto;
import com.woozuda.backend.diary.entity.Diary;
import com.woozuda.backend.note.entity.QNote;
import jakarta.persistence.EntityManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.querydsl.core.group.GroupBy.groupBy;
import static com.querydsl.core.group.GroupBy.list;
import static com.querydsl.jpa.JPAExpressions.select;
import static com.woozuda.backend.account.entity.QUserEntity.userEntity;
import static com.woozuda.backend.diary.entity.QDiary.diary;
import static com.woozuda.backend.diary.entity.QDiaryTag.diaryTag;
import static com.woozuda.backend.note.entity.QNote.note;
import static com.woozuda.backend.tag.entity.QTag.tag;

public class CustomDiaryRepositoryImpl implements CustomDiaryRepository {

    private final JPAQueryFactory query;

    public CustomDiaryRepositoryImpl(EntityManager em) {
        this.query = new JPAQueryFactory(JPQLTemplates.DEFAULT, em);
    }

    @Override
    public List<SingleDiaryResponseDto> searchDiarySummaryList(String username) {
        // Q 클래스

        // 쿼리 작성
        List<Tuple> results = query
                .select(
                        diary.id,
                        diary.title,
                        diary.image,
                        diary.startDate,
                        diary.endDate,
                        diary.noteCount,
                        tag.name
                )
                .from(diary)
                .join(diary.user, userEntity)
                .leftJoin(diary.tagList, diaryTag)
                .leftJoin(diaryTag.tag, tag)
                .where(userEntity.username.eq(username))
                .orderBy(diary.createdAt.desc())
                .fetch();

        // 결과를 DTO로 매핑
        Map<Long, SingleDiaryResponseDto> diaryMap = new HashMap<>();

        results.forEach(tuple -> {
            Long diaryId = tuple.get(diary.id);
            SingleDiaryResponseDto dto = diaryMap.getOrDefault(diaryId, new SingleDiaryResponseDto(
                    diaryId,
                    tuple.get(diary.title),
                    new ArrayList<>(),
                    tuple.get(diary.image),
                    tuple.get(diary.startDate),
                    tuple.get(diary.endDate),
                    tuple.get(diary.noteCount)
            ));

            String tagName = tuple.get(tag.name);
            if (tagName != null) {
                dto.getSubject().add(tagName);
            }

            diaryMap.put(diaryId, dto);
        });

        return new ArrayList<>(diaryMap.values());
    }

    @Override
    public SingleDiaryResponseDto searchSingleDiarySummary(String username, Long diaryId) {
        return query
                .from(diary)
                .join(diary.user, userEntity)
                .leftJoin(diary.tagList, diaryTag)
                .leftJoin(diaryTag.tag, tag)
                .where(diary.id.eq(diaryId), userEntity.username.eq(username))
                .transform(
                        groupBy(diary.id).list(
                                Projections.constructor(SingleDiaryResponseDto.class,
                                        diary.id,
                                        diary.title,
                                        list(
                                                tag.name
                                        ),
                                        diary.image,
                                        diary.startDate,
                                        diary.endDate,
                                        diary.noteCount
                                )
                        )
                )
                .getFirst();
    }

    @Override
    public List<Long> searchDiaryIdList(String username) {
        return query
                .select(diary.id)
                .from(diary)
                .join(diary.user, userEntity)
                .where(userEntity.username.eq(username))
                .fetch();
    }

    @Override
    public Diary searchDiary(Long diaryId, String username) {
        return query
                .selectFrom(diary)
                .where(diary.user.username.eq(username), diary.id.eq(diaryId))
                .fetchFirst();
    }

    @Override
    public DiaryNameListResponseDto searchNames(String username) {
        List<DiaryNameResponseDto> nameList = query
                .select(Projections.constructor(DiaryNameResponseDto.class,
                        diary.id,
                        diary.title
                ))
                .from(diary)
                .leftJoin(diary.user, userEntity)
                .where(userEntity.username.eq(username))
                .fetch();

        return new DiaryNameListResponseDto(nameList);
    }

    @Override
    public List<Diary> searchDiariesHaving(List<Long> noteIdList) {
        QNote noteSub = new QNote("noteSub");

        return query
                .selectFrom(diary)
                .leftJoin(diary.noteList, note)
                .leftJoin(note.noteContents)
                .where(
                        diary.id.in(
                                select(noteSub.diary.id)
                                        .from(noteSub)
                                        .where(noteSub.id.in(noteIdList))
                        )
                )
                .fetch();
    }
}
