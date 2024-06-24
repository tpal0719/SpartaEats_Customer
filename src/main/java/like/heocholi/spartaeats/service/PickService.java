package like.heocholi.spartaeats.service;

import like.heocholi.spartaeats.constants.ErrorType;
import like.heocholi.spartaeats.dto.OrderListResponseDTO;
import like.heocholi.spartaeats.dto.PickPageResponseDto;
import like.heocholi.spartaeats.dto.PickResponseDto;
import like.heocholi.spartaeats.dto.StoreResponseDto;
import like.heocholi.spartaeats.entity.Customer;
import like.heocholi.spartaeats.entity.Order;
import like.heocholi.spartaeats.entity.Pick;
import like.heocholi.spartaeats.entity.Store;
import like.heocholi.spartaeats.exception.OrderException;
import like.heocholi.spartaeats.exception.PageException;
import like.heocholi.spartaeats.exception.PickException;
import like.heocholi.spartaeats.exception.StoreException;
import like.heocholi.spartaeats.repository.PickRepository;
import like.heocholi.spartaeats.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PickService {

    PickRepository pickRepository;

    public PickPageResponseDto getPickList(Customer customer, Integer page) {
        Pageable pageable = PageRequest.of(page-1, 5);
        Page<Pick> pickPage = pickRepository.findAllByCustomer(customer, pageable);

        checkValidatePage(page, pickPage);

        return new PickPageResponseDto(page, pickPage);

    }

    private static void checkValidatePage(Integer page, Page<Pick> pickPage) {
        if (pickPage.getTotalElements() == 0) {
            throw new PickException(ErrorType.NOT_FOUND_PICK);
        }

        if (page > pickPage.getTotalPages() || page < 1) {
            throw new PageException(ErrorType.INVALID_PAGE);
        }
    }
}
