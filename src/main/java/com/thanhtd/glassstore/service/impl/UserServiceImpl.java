package com.thanhtd.glassstore.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.thanhtd.glassstore.core.common.ErrorCode;
import com.thanhtd.glassstore.core.constant.Status;
import com.thanhtd.glassstore.core.exception.LogicException;
import com.thanhtd.glassstore.dao.UserDao;
import com.thanhtd.glassstore.dao.UserRoleDao;
import com.thanhtd.glassstore.dto.AuthInfo;
import com.thanhtd.glassstore.dto.UserInfo;
import com.thanhtd.glassstore.dto.UserRolesInfo;
import com.thanhtd.glassstore.model.*;
import com.thanhtd.glassstore.service.*;
import com.thanhtd.glassstore.utils.AppUtils;
import jodd.util.StringPool;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    private static final String PREFIX = "user";

    private final UserDao userDao;

    private final UserRoleDao userRoleDao;

    private final RoleService roleService;

    private final CityService cityService;

    private final DistrictService districtService;

    private final WardService wardService;

    private final PasswordEncoder passwordEncoder;

    private final RedisService redisService;

    private static final int REDIS_TTL = 300;

    @Override
    public User createUser(AuthInfo authInfo) throws LogicException {
        if (ObjectUtils.isEmpty(authInfo) || authInfo.isMissingInfo())
            throw new LogicException(ErrorCode.INVALID_CREDENTIAL);
        if (!AppUtils.checkValidEmailAddress(authInfo.getEmail()))
            throw new LogicException(ErrorCode.INVALID_EMAIL);
        if (!authInfo.getPassword().equals(authInfo.getConfirmPassword()))
            throw new LogicException(ErrorCode.UNLATCHING_PASSWORD);

        User user = new User();
        user.setEmail(authInfo.getEmail());
        user.setPhoneNumber(authInfo.getPhoneNumber());
        user.setPassword(passwordEncoder.encode(authInfo.getPassword()));
        user.setStatus(Status.ACTIVE);
        user.setCreateDate(new Date(System.currentTimeMillis()));

        return userDao.save(user);
    }

    @Override
    public User updateUser(UserInfo userInfo) throws LogicException {
        if (ObjectUtils.isEmpty(userInfo)) throw new LogicException(ErrorCode.DATA_NULL);

        User user = findByUserId(userInfo.getUserId());
        if (ObjectUtils.isEmpty(user))
            throw new LogicException(ErrorCode.NOT_FOUND_DATA);

        if (!ObjectUtils.isEmpty(userInfo.getAvatar())) {
            user.setAvatar(userInfo.getAvatar());
        }
        if (!ObjectUtils.isEmpty(userInfo.getPhoneNumber())) {
            user.setPhoneNumber(userInfo.getPhoneNumber());
        }
        if (!ObjectUtils.isEmpty(userInfo.getFullName())) {
            user.setFullName(userInfo.getFullName());
        }
        if (!ObjectUtils.isEmpty(userInfo.getAddress())) {
            user.setAddress(userInfo.getAddress());
        }
        if (!ObjectUtils.isEmpty(userInfo.getBirthday())) {
            user.setBirthday(userInfo.getBirthday());
        }
        if (!ObjectUtils.isEmpty(userInfo.getCityName())) {
            City city = cityService.findByName(userInfo.getCityName());
            if (ObjectUtils.isEmpty(city))
                throw new LogicException(ErrorCode.NOT_FOUND_CITY);
            else {
                user.setCityId(city.getCityId());
            }
        }
        if (!ObjectUtils.isEmpty(userInfo.getDistrictName())) {
            District district = districtService.findByName(userInfo.getDistrictName());
            if (ObjectUtils.isEmpty(district))
                throw new LogicException(ErrorCode.NOT_FOUND_DISTRICT);
            else {
                user.setDistrictId(district.getDistrictId());
            }
        }
        if (!ObjectUtils.isEmpty(userInfo.getWardName())) {
            Ward ward = wardService.findByName(userInfo.getWardName());
            if (ObjectUtils.isEmpty(ward))
                throw new LogicException(ErrorCode.NOT_FOUND_WARD);
            else {
                user.setWardId(ward.getWardId());
            }
        }
        if (!ObjectUtils.isEmpty(userInfo.getStatus())) {
            user.setStatus(userInfo.getStatus());
        }

        user.setModifiedDate(new Date(System.currentTimeMillis()));

        return userDao.save(user);
    }

    @Override
    public ErrorCode deleteUser(Long userId) throws LogicException {
        User user = findByUserId(userId);
        if (ObjectUtils.isEmpty(user))
            return ErrorCode.NOT_FOUND_USER;

        user.setStatus(Status.DELETED);
        user.setModifiedDate(new Date());
        userDao.save(user);
        return ErrorCode.OK;
    }

    @Override
    public User findByUserId(Long userId) throws LogicException {
        if (ObjectUtils.isEmpty(userId))
            throw new LogicException(ErrorCode.ID_NULL);
        String key = redisService.createKey(PREFIX, String.valueOf(userId));
        User user = (User) redisService.getObject(key, User.class);
        try {
            if (ObjectUtils.isEmpty(user)) {
                user = userDao.findByUserId(userId);
                if (!ObjectUtils.isEmpty(user)) redisService.saveObject(key, user, REDIS_TTL);
            }
        } catch (JsonProcessingException e) {
            logger.error("Save object to redis fail, error: {}", e.getMessage());
        }
        return user;
    }

    @Override
    public User findByEmailAddress(String email) throws LogicException {
        if (!AppUtils.checkValidEmailAddress(email))
            throw new LogicException(ErrorCode.INVALID_EMAIL);
        return userDao.findByEmail(email);
    }

    @Override
    public List<User> findAllUsers() throws JsonProcessingException {
        String key = redisService.createKey(PREFIX, "all");
        List<User> users = redisService.getListObjects(key, User.class);
        if (ObjectUtils.isEmpty(users)) {
            users = userDao.findAllUsers();
            List<Object> objects = users.stream().map(Object.class::cast).toList();
            redisService.saveList(key, objects, REDIS_TTL);
        }
        return users;
    }

    @Override
    public UserInfo getUserInfo(User user) throws LogicException {
        if (ObjectUtils.isEmpty(user)) throw new LogicException(ErrorCode.DATA_NULL);

        UserInfo userInfo = new UserInfo();
        userInfo.setFullName(user.getFullName());
        userInfo.setBirthday(user.getBirthday());
        userInfo.setAvatar(user.getAvatar());
        userInfo.setPhoneNumber(user.getPhoneNumber());
        userInfo.setStatus(user.getStatus());

        List<String> roleNames = new ArrayList<>();
        List<UserRole> userRoleList = userRoleDao.findByUserId(user.getUserId());
        for (UserRole userRole: userRoleList) {
            Role role = roleService.findByRoleId(userRole.getRoleId());
            if (ObjectUtils.isEmpty(role)) continue;
            roleNames.add(role.getName());
        }
        userInfo.setRoles(roleNames);

        return userInfo;
    }

    @Override
    public User getCurrentUser() throws LogicException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof AnonymousAuthenticationToken) throw new LogicException(ErrorCode.LOGIN_FAIL);
        return (User) authentication.getPrincipal();
    }

    @Override
    public ErrorCode forgotPassword(UserInfo userInfo) throws LogicException {
        return ErrorCode.OK;
    }

    @Override
    public ErrorCode changePassword(UserInfo userInfo) throws LogicException {
        return ErrorCode.OK;
    }

    @Override
    public UserInfo addRoles(UserRolesInfo userRolesInfo) throws LogicException {
        if (ObjectUtils.isEmpty(userRolesInfo))
            throw new LogicException(ErrorCode.DATA_NULL);
        User user = findByUserId(userRolesInfo.getUserId());
        if (ObjectUtils.isEmpty(user))
            throw new LogicException(ErrorCode.NOT_FOUND_USER);

        List<Long> roleIds = Arrays.stream(userRolesInfo.getRoleIds()
                .split(StringPool.DASH))
                .filter(StringUtils::isNumeric)
                .map(Long::parseLong)
                .toList();

        for (Long roleId: roleIds) {
            Role role = roleService.findByRoleId(roleId);
            if (ObjectUtils.isEmpty(role))
                throw new LogicException(ErrorCode.NOT_FOUND_ROLE);
            UserRole userRole = new UserRole();
            userRole.setUserId(user.getUserId());
            userRole.setRoleId(roleId);

            userRoleDao.save(userRole);
        }
        return getUserInfo(user);
    }
}
