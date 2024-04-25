package com.sideproject.hororok.common.fixtures;

import com.sideproject.hororok.plan.domain.enums.MatchType;
import com.sideproject.hororok.plan.dto.request.CreatePlanRequest;
import com.sideproject.hororok.plan.dto.request.SavePlanRequest;
import com.sideproject.hororok.plan.dto.request.SharePlanRequest;
import com.sideproject.hororok.plan.dto.response.CreatePlanResponse;
import com.sideproject.hororok.plan.dto.response.SavePlanResponse;
import com.sideproject.hororok.plan.dto.response.SharePlanResponse;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

import static com.sideproject.hororok.common.fixtures.CafeFixtures.*;
import static com.sideproject.hororok.common.fixtures.KeywordFixtures.카테고리_키워드_DTO;
import static com.sideproject.hororok.common.fixtures.KeywordFixtures.키워드_이름_리스트;

public class PlanFixtures {

    public static final Long 계획_ID = 1L;
    public static final String 위치_이름 = "위치 이름";
    public static final BigDecimal 계획_위도 = getRandomBigDecimal(0, 90);
    public static final BigDecimal 계획_경도 = getRandomBigDecimal(0, 180);
    public static final Integer 도보_시간 = 10;
    public static final LocalDate 날짜 = LocalDate.now();
    public static final LocalTime 시작_시간 = LocalTime.of(9, 0);
    public static final LocalTime 끝_시간 = LocalTime.of(11, 0);
    public static final String 계획_일자_시간 = "00월 00일 00시 00분";

    public static CreatePlanRequest 계획_요청() {
        return new CreatePlanRequest(
                위치_이름, 계획_위도, 계획_경도, 도보_시간, 날짜, 시작_시간, 끝_시간, 키워드_이름_리스트);
    }

    public static CreatePlanResponse 계획_응답() {
        CreatePlanResponse response = new CreatePlanResponse(
                MatchType.MISMATCH, 계획_요청(), 카테고리_키워드_DTO(), 카페_DTO_리스트());
        response.setPlanId(계획_ID);
        return response;
    }

    public static SavePlanRequest 계획_저장_요청() {
        return new SavePlanRequest(계획_ID);
    }

    public static SavePlanResponse 계획_저장_응답() {
        return new SavePlanResponse(계획_ID);
    }

    public static SharePlanRequest 계획_공유_요청() {
        return new SharePlanRequest(계획_ID);
    }

    public static SharePlanResponse 계획_공유_응답() {
        return new SharePlanResponse(계획_ID);
    }




}
