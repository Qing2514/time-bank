package com.fortuna.bampo.service;

import com.fortuna.bampo.entity.User;
import com.fortuna.bampo.model.request.data.UserRegistration;
import com.fortuna.bampo.model.request.data.UserSearchFilter;
import com.fortuna.bampo.model.response.data.UserAbstract;
import com.fortuna.bampo.model.response.data.UserDetail;
import com.fortuna.bampo.model.response.data.UserInfo;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.List;

/**
 * 用户服务接口
 *
 * @author Eva7, Qing2514, lhx, CMT
 * @since 0.3.1
 */
public interface UserService extends UserDetailsService {

    /**
     * 注册一个新用户
     *
     * @param userRegistration 用户注册表单
     * @return 是否注册成功
     * @throws EntityExistsException   当用户身份标识已存在时抛出
     * @throws EntityNotFoundException 当默认用户角色不存在时抛出
     */
    boolean register(UserRegistration userRegistration);

    /**
     * 获取验证邮件
     *
     * @param username 用户名
     * @return 验证邮件是否发送成功
     */
    boolean getVerification(String username);

    /**
     * 验证邮箱
     *
     * @param token    验证 token
     * @param username 用户名
     * @return 验证是否成功
     */
    boolean verify(String token, String username);

    /**
     * 通过用户名模糊查询用户
     *
     * @param query            搜索字段
     * @param order            排序方法
     * @param orderBy          排序依据
     * @param page             页数
     * @param pageSize         分页大小
     * @param userSearchFilter 用户搜索筛选数据
     * @return 用户名包含该搜索字段的所有用户
     */
    List<String> search(String query,
                        String order,
                        String orderBy,
                        Integer page,
                        Integer pageSize,
                        UserSearchFilter userSearchFilter);

    /**
     * 获取用户摘要
     *
     * @param username 用户名
     * @return 用户摘要
     */
    UserAbstract getAbstract(String username);

    /**
     * 获取用户信息
     *
     * @param username 用户名
     * @return 用户信息
     */
    UserInfo getInfo(String username);

    /**
     * 获取用户详情
     *
     * @param username 用户名
     * @return 用户详情
     */
    UserDetail getDetail(String username);

    /**
     * 修改用户基本信息
     *
     * @param username    用户名
     * @param city        城市
     * @param email       电子邮箱
     * @param phoneNumber 电话号码
     * @return 是否修改成功
     */
    boolean modify(String username, String city, String email, Long phoneNumber);

    /**
     * 修改用户名
     *
     * @param username    修改前的用户名
     * @param password    密码
     * @param newUsername 修改后的用户名
     * @return 是否修改成功
     */
    boolean updateUsername(String username, String password, String newUsername);

    /**
     * 修改密码
     *
     * @param username    用户名
     * @param password    密码
     * @param newPassword 新密码
     * @return 是否修改成功
     */
    boolean updatePassword(String username, String password, String newPassword);

    /**
     * 通过用户名获取用户
     *
     * @param username 用户名
     * @return 匹配该用户名的用户
     * @throws UsernameNotFoundException 该用户名未出现
     */
    @Override
    User loadUserByUsername(String username) throws UsernameNotFoundException;
}
