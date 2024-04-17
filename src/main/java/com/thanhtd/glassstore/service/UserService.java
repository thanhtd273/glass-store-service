package com.thanhtd.glassstore.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.thanhtd.glassstore.core.common.ErrorCode;
import com.thanhtd.glassstore.core.exception.LogicException;
import com.thanhtd.glassstore.dto.AuthInfo;
import com.thanhtd.glassstore.dto.UserInfo;
import com.thanhtd.glassstore.dto.UserRolesInfo;
import com.thanhtd.glassstore.model.User;

import java.util.List;

public interface UserService {
    User createUser(AuthInfo authInfo) throws LogicException;

    User updateUser(UserInfo userInfo) throws LogicException;

    ErrorCode deleteUser(Long userId) throws LogicException;

    User findByUserId(Long userId) throws LogicException;

    User findByEmailAddress(String email) throws LogicException;

    List<User> findAllUsers() throws JsonProcessingException;

    UserInfo getUserInfo(User user) throws LogicException;

    User getCurrentUser() throws LogicException;

    ErrorCode forgotPassword(UserInfo userInfo) throws LogicException;

    ErrorCode changePassword(UserInfo userInfo) throws LogicException;

    UserInfo addRoles(UserRolesInfo userRoleInfoList) throws LogicException;

}
