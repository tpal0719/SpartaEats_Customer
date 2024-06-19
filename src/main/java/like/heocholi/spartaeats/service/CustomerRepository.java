package like.heocholi.spartaeats.service;

import like.heocholi.spartaeats.dto.SignupRequestDto;
import like.heocholi.spartaeats.entity.Customer;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerRepository {

    private final like.heocholi.spartaeats.repository.CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;

    public String signup(SignupRequestDto requestDto) {
        String userId = requestDto.getUserId();
        String password = requestDto.getPassword();

        Optional<Customer> checkUsername = customerRepository.findByUserId(userId);
        if (checkUsername.isPresent()) {
            throw new IllegalArgumentException("중복된 사용자가 존재합니다.");
        }

        String encodedPassword = passwordEncoder.encode(password);

        Customer customer = new Customer(requestDto, encodedPassword);

        customerRepository.save(customer);

        return "회원가입 성공";
    }
}
