package like.heocholi.spartaeats.service;

import like.heocholi.spartaeats.constants.ErrorType;
import like.heocholi.spartaeats.constants.UserStatus;
import like.heocholi.spartaeats.dto.SignupRequestDto;
import like.heocholi.spartaeats.dto.WithdrawRequestDto;
import like.heocholi.spartaeats.entity.Customer;
import like.heocholi.spartaeats.exception.UserException;
import like.heocholi.spartaeats.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.With;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public String signup(SignupRequestDto requestDto) {
        String userId = requestDto.getUserId();
        String password = requestDto.getPassword();

        Optional<Customer> checkUsername = customerRepository.findByUserId(userId);
        if (checkUsername.isPresent()) {
            throw new UserException(ErrorType.DUPLICATE_ACCOUNT_ID);
        }

        String encodedPassword = passwordEncoder.encode(password);

        Customer customer = new Customer(requestDto, encodedPassword);

        customerRepository.save(customer);

        return "회원가입 성공";
    }

    @Transactional
    public String withdrawCustomer(WithdrawRequestDto requestDto, String userId) {
        // 유저 확인
        Customer customer = this.findByUserId(userId);
        // 이미 탈퇴한 회원인지 확인
        if(customer.getUserStatus().equals(UserStatus.DEACTIVATE)){
            throw new UserException(ErrorType.DEACTIVATE_USER);
        }
        // 비밀번호 확인
        if(!passwordEncoder.matches(requestDto.getPassword(), customer.getPassword())){
            throw new UserException(ErrorType.INVALID_PASSWORD);
        }

        customer.withdrawCustomer();

        return customer.getUserId();
    }

    private Customer findByUserId(String userId){
        return customerRepository.findByUserId(userId).orElseThrow(()-> new UserException(ErrorType.NOT_FOUND_USER));
    }


}
