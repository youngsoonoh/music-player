package com.music.demo.security;

import com.music.demo.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component("userDetailsService")
public class DomainUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    public DomainUserDetailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findOneWithAuthoritiesByLogin(username)
                .map(user -> {
                    List<GrantedAuthority> grantedAuthorities = user.getAuthorities().stream()
                            .map(authority -> new SimpleGrantedAuthority(authority.getName()))
                            .collect(Collectors.toList());

                    return new org.springframework.security.core.userdetails.User(user.getLogin(),
                            user.getPassword(),
                            grantedAuthorities);
                })
                .orElseThrow(() -> new UsernameNotFoundException("유저 이름을 찾을 수 없습니다. : " + username));
    }
}
