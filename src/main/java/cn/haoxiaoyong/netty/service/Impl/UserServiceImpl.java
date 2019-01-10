package cn.haoxiaoyong.netty.service.Impl;

import cn.haoxiaoyong.netty.mapper.UserMapper;
import cn.haoxiaoyong.netty.model.User;
import cn.haoxiaoyong.netty.model.UserExample;
import cn.haoxiaoyong.netty.service.UserService;
import cn.haoxiaoyong.netty.util.FastDFSClient;
import cn.haoxiaoyong.netty.util.IdWorker;
import cn.haoxiaoyong.netty.util.QRCodeUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

/**
 * Created by haoxy on 2019/1/5.
 * E-mail:hxyHelloWorld@163.com
 * github:https://github.com/haoxiaoyong1014
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private IdWorker idWorker;
    @Autowired
    private FastDFSClient fastDFSClient;
    @Value("${fdfs.httpurl}")
    private String httpUrl;
    @Autowired
    private QRCodeUtils qrCodeUtils;


    @Override
    public List<User> selectAll() {

        return userMapper.selectByExample(null);
    }

    @Override
    public User login(String username, String password) {
        UserExample example = new UserExample();
        example.createCriteria().andUsernameEqualTo(username);
        List<User> users = userMapper.selectByExample(example);
        if (users != null && users.size() == 1) {
            //对密码进行校验
            String encoding = DigestUtils.md5DigestAsHex(password.getBytes());
            if (encoding.equals(users.get(0).getPassword())) {
                return users.get(0);
            }
        }
        return null;
    }

    /**
     * 用户注册
     *
     * @param user
     */
    @Override
    public void register(User user) {
        //要判断这个用户是否存在
        UserExample example = new UserExample();
        example.createCriteria().andUsernameEqualTo(user.getUsername());
        List<User> users = userMapper.selectByExample(example);
        if (users != null && users.size() > 0) {
            throw new RuntimeException("用户已经存在!");
        }
        //否则将用户信息保存到数据库
        user.setId(idWorker.nextId());
        user.setNickname(user.getUsername());
        user.setCreatetime(new Date());
        //生成用户专属二维码

        user.setQrcode("");
        user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
        userMapper.insert(user);
    }

    @Override
    public User upload(MultipartFile file, String userid) {
        try {
            //返回在FastDFS中的URL路径,这个路径不带 http://ip/..
            String url = fastDFSClient.uploadFace(file);
            System.out.println("url" + url);
            //在FastDFS上传的时候,会自动生成一个缩略图
            //文件名_150x150.后缀
            String[] fileNameList = url.split("\\.");
            String fileName = fileNameList[0];
            String ext = fileNameList[1];
            String picSmallUrl = fileName + "_150x150." + ext;
            User user = userMapper.selectByPrimaryKey(userid);
            //设置头像的大图片
            user.setPicNormal(httpUrl + url);
            //设置头像小图
            user.setPicSmall(httpUrl + picSmallUrl);
            userMapper.updateByPrimaryKey(user);
            return user;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void updateNickName(String id, String nickname) {
        if (StringUtils.isNotBlank(nickname)) {
            User user = userMapper.selectByPrimaryKey(id);
            user.setNickname(nickname);
            userMapper.updateByPrimaryKey(user);
        } else {
            throw new RuntimeException("用户昵称不能为空");
        }
    }

    @Override
    public User reloadUser(String userid) {
        if (StringUtils.isNotBlank(userid)) {
            User user = userMapper.selectByPrimaryKey(userid);
            if (user != null) {
                return user;
            }
            return null;
        } else {
            throw new RuntimeException("userid 不能为空!");
        }
    }
}
