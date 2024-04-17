package com.thanhtd.glassstore.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@Data
@Entity
@Table(name = "_user")
@NamedQuery(name = "User.findAll", query = "SELECT u FROM User u WHERE u.status <> 0")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "birthday")
    private Date birthday;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "email")
    private String email;

    @Column(name = "avatar")
    private String avatar;

    @JsonIgnore
    @Column(name = "password")
    private String password;

    @JsonIgnore
    @Transient
    private String confirmPassword;

    @Column(name = "city_id")
    private Long cityId;

    @Column(name = "district_id")
    private Long districtId;

    @Column(name = "ward_id")
    private Long wardId;

    @Column(name = "address")
    private String address;

    @Column(name = "create_date")
    private Date createDate;

    @Column(name = "modified_date")
    private Date modifiedDate;

    @Column(name = "status")
    private Integer status;

    @JoinTable(name = "user_role",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id")}
    )
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    private Set<Role> roles = new HashSet<>();

    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        for (Role role: roles) {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        }
        return authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.getStatus() != 0;
    }
}
