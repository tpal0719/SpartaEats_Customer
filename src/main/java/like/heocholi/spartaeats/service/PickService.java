package like.heocholi.spartaeats.service;

import like.heocholi.spartaeats.constants.ErrorType;
import like.heocholi.spartaeats.dto.PickPageResponseDto;
import like.heocholi.spartaeats.entity.Customer;
import like.heocholi.spartaeats.entity.Pick;
import like.heocholi.spartaeats.exception.PageException;
import like.heocholi.spartaeats.exception.PickException;
import like.heocholi.spartaeats.repository.PickRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PickService {

    PickRepository pickRepository;

    public PickPageResponseDto getPickList(Customer customer, Integer page) {
        Pageable pageable = PageRequest.of(page-1, 5);
        Page<Pick> pickPage = pickRepository.findAllByCustomerAndIsPickTrue(customer, pageable);

        checkValidatePage(page, pickPage);

        return new PickPageResponseDto(page, pickPage);



        // 이런 코드를 찾긴 했습니당...ㅋㅋㅋㅋㅋㅋ
        // 요거 쓰면  pickRepository.findAllByCustomerAndIsPickTrue(customer, pageable);
        // 이거를 반환값을 List로 받아야되는 것 같기도.. 한데 잘 모르겠어요 테스트를 안해봐서...
        //PageImpl<>(pickList).stream().map().toList();

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
