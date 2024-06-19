package like.heocholi.spartaeats.security;

import like.heocholi.spartaeats.entity.Customer;
import like.heocholi.spartaeats.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService{
    private final CustomerRepository customerRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Customer customer = customerRepository.findByUserId(username).orElseThrow(() -> new UsernameNotFoundException(username));
        return new UserDetailsImpl(customer);
    }
}
