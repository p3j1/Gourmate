package com.sparta.gourmate.domain.menu.service;

import com.sparta.gourmate.domain.menu.dto.MenuRequestDto;
import com.sparta.gourmate.domain.menu.dto.MenuResponseDto;
import com.sparta.gourmate.domain.menu.entity.Menu;
import com.sparta.gourmate.domain.menu.repository.MenuRepository;
import com.sparta.gourmate.domain.store.entity.Store;
import com.sparta.gourmate.domain.store.repository.StoreRepository;
import com.sparta.gourmate.domain.user.entity.User;
import com.sparta.gourmate.domain.user.entity.UserRoleEnum;
import com.sparta.gourmate.global.exception.CustomException;
import com.sparta.gourmate.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

import static com.sparta.gourmate.global.exception.ErrorCode.STORE_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class MenuService {

    private final MenuRepository menuRepository;
    private final StoreRepository storeRepository;

    public MenuResponseDto createMenu(User user, MenuRequestDto requestDto) {
        Store store = checkStore(requestDto.getStoreId());
        checkUser(user, store);
        checkRole(user);
        Menu menu = new Menu(requestDto, store);
        menuRepository.save(menu);
        return new MenuResponseDto(menu);
    }

    private Store checkStore(UUID storeId) {
        return storeRepository.findById(storeId)
                .orElseThrow(() -> new CustomException(STORE_NOT_FOUND));
    }

    private void checkUser(User user, Store store) {
        if (!Objects.equals(store.getUser().getId(), user.getId())) {
            throw new CustomException(ErrorCode.USER_NOT_SAME);
        }
    }

    private void checkRole(User user) {
        UserRoleEnum role = UserRoleEnum.OWNER;
        if (!Objects.equals(user.getRole(), role)) {
            throw new CustomException(ErrorCode.AUTH_AUTHORIZATION_FAILED);
        }
    }

}
