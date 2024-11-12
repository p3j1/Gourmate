package com.sparta.gourmate.domain.store.service;

import com.sparta.gourmate.domain.store.dto.CategoryRequestDto;
import com.sparta.gourmate.domain.store.dto.CategoryResponseDto;
import com.sparta.gourmate.domain.store.entity.Category;
import com.sparta.gourmate.domain.store.repository.CategoryRepository;
import com.sparta.gourmate.domain.user.entity.User;
import com.sparta.gourmate.domain.user.entity.UserRoleEnum;
import com.sparta.gourmate.global.exception.CustomException;
import com.sparta.gourmate.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    // 카테고리 생성
    public CategoryResponseDto createCategory(CategoryRequestDto requestDto, User user) {
        checkRole(user);    // 권한 확인

        Category category = new Category(requestDto);
        categoryRepository.save(category);
        return new CategoryResponseDto(category);
    }

    // 카테고리 목록 조회
    public List<CategoryResponseDto> getCategories() {
        List<Category> categoryList = categoryRepository.findAll();
        List<CategoryResponseDto> responseDtoList = new ArrayList<>();

        for (Category category : categoryList) {
            responseDtoList.add(new CategoryResponseDto(category));
        }
        return responseDtoList;
    }

    // 권한 확인
    private void checkRole(User user) {
        UserRoleEnum role = user.getRole();

        if (role != UserRoleEnum.MANAGER) {
            throw new CustomException(ErrorCode.AUTH_AUTHORIZATION_FAILED);
        }
    }
}