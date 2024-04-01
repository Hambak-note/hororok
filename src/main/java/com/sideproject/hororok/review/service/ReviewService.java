package com.sideproject.hororok.review.service;

import com.sideproject.hororok.aop.annotation.LogTrace;
import com.sideproject.hororok.cafe.cond.CafeCategorySearchCond;
import com.sideproject.hororok.cafe.cond.CreatePlanSearchCond;
import com.sideproject.hororok.cafe.entity.Cafe;
import com.sideproject.hororok.keword.dto.KeywordDto;
import com.sideproject.hororok.keword.entity.Keyword;
import com.sideproject.hororok.review.Entity.Review;
import com.sideproject.hororok.review.dto.ReviewDto;
import com.sideproject.hororok.review.repository.ReviewRepository;
import com.sideproject.hororok.reviewImage.entity.ReviewImage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;

    @LogTrace
    public List<ReviewDto> findReviewByCafeId(Long cafeId){

        List<Review> reviews = reviewRepository.findByCafeId(cafeId);
        List<ReviewDto> reviewDtos = new ArrayList<>();
        for (Review review : reviews) {
            reviewDtos.add(ReviewDto.of(review, review.getUser().getNickname()));
        }

        return reviewDtos;
    }

    @LogTrace
    public List<KeywordDto> findKeywordInReviewByCafeIdOrderByDesc(Long cafeId) {

        List<Keyword> keywords = reviewRepository.findKeywordInReviewByCafeIdOrderByDesc(cafeId);
        List<KeywordDto> keywordDtoList = new ArrayList<>();
        int idx = 0;
        for (Keyword keyword : keywords) {
            if(idx == 3) break;
            keywordDtoList.add(KeywordDto.from(keyword));
            idx++;
        }

        return keywordDtoList;
    }

    @LogTrace
    public List<Cafe> findCafeWithKeywordsInReview(CafeCategorySearchCond searchCond) {

        return reviewRepository.findCafeWithKeywordsInReview(searchCond.getKeywords());
    }


    @LogTrace
    public List<ReviewImage> findReviewImagesByCafeId(Long cafeId) {
        return reviewRepository.findReviewImagesByCafeId(cafeId);
    }

}
