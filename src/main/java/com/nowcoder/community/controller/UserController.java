package com.nowcoder.community.controller;


        import com.nowcoder.community.annotation.LoginRequired;
        import com.nowcoder.community.entity.User;
        import com.nowcoder.community.service.FollowService;
        import com.nowcoder.community.service.LikeService;
        import com.nowcoder.community.service.UserService;
        import com.nowcoder.community.util.CommunityConstant;
        import com.nowcoder.community.util.CommunityUtil;
        import com.nowcoder.community.util.FileTypeJudge;
        import com.nowcoder.community.util.HostHolder;
        import org.apache.commons.lang3.StringUtils;
        import org.slf4j.Logger;
        import org.slf4j.LoggerFactory;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.beans.factory.annotation.Value;
        import org.springframework.stereotype.Controller;
        import org.springframework.ui.Model;
        import org.springframework.web.bind.annotation.PathVariable;
        import org.springframework.web.bind.annotation.RequestMapping;
        import org.springframework.web.bind.annotation.RequestMethod;
        import org.springframework.web.multipart.MultipartFile;

        import javax.servlet.http.HttpServletResponse;
        import java.io.File;
        import java.io.FileInputStream;
        import java.io.IOException;
        import java.io.OutputStream;

@Controller
@RequestMapping("/user")
public class UserController implements CommunityConstant {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    //配置上传图片路径
    @Value("${community.path.upload}")
    private String uploadPath;

    @Value("${community.path.domain}")
    private String domain;

    //配置项目名
    @Value("${server.servlet.context-path}")
    private String contextPath;

    @Autowired
    private UserService userService;

    //更新当前用户头像需要，从hostHolder中去取出user
    @Autowired
    private HostHolder hostHolder;

    @Autowired
    private LikeService likeService;

    @Autowired
    private FollowService followService;

    @LoginRequired
    @RequestMapping(path = "/setting", method = RequestMethod.GET)
    public String getSettingPage() {
        return "/site/setting";
    }

    @RequestMapping(path = "/upload", method = RequestMethod.POST)
    public String uploadHeader(MultipartFile headerImage, Model model) {
        if (headerImage == null) {
            model.addAttribute("error", "你还没有选择图片");
            return "/site/setting";
        }
        //上传图片时需要需改上传的文件的名
        //读取文件的后缀
        String fileName = headerImage.getOriginalFilename();//得到原始的文件名
        String suffix = fileName.substring(fileName.lastIndexOf("."));//得到后缀名
        System.out.println(suffix);
        if (StringUtils.isBlank(suffix) || FileTypeJudge.judgeIsPhoto(suffix) == false) {
            model.addAttribute("error", "文件的格式存在问题");
            return "/site/setting";
        } else {
            //生成随机的文件名加上后缀
            fileName = CommunityUtil.gennerateUUID() + suffix;
            //确定文件存放的路径
            //确定文件上传的路径
            File dest = new File(uploadPath + "/" + fileName);
            try {
                headerImage.transferTo(dest);
            } catch (IOException e) {
                logger.error("文件上传失败" + e.getMessage());
                throw new RuntimeException("文件上传失败，服务器发生异常", e);
            }

            //更新当前用户头像的路径（web访问路劲）
            // http://localhost:8080/community/user/header/xxx.png
            User user = hostHolder.getUser();
            String headerUrl = domain + contextPath + "/user/header/" + fileName;
            userService.updateHeader(user.getId(), headerUrl);

            return "redirect:/index";
        }

    }

    @RequestMapping(path = "/header/{fileName}", method = RequestMethod.GET)
    public void getHeader(@PathVariable("fileName") String fileName, HttpServletResponse response) {
        // 服务器存放路径
        fileName = uploadPath + "/" + fileName;
        // 文件后缀
        String suffix = fileName.substring(fileName.lastIndexOf("."));
        // 响应图片
        response.setContentType("image1/" + suffix);
        try (
                FileInputStream fis = new FileInputStream(fileName);
                OutputStream os = response.getOutputStream();
        ) {
            byte[] buffer = new byte[1024];
            int b = 0;
            while ((b = fis.read(buffer)) != -1) {
                os.write(buffer, 0, b);
            }
        } catch (IOException e) {
            logger.error("读取头像失败: " + e.getMessage());
        }
    }

    //个人主页,显示任意用户的主页
    @RequestMapping(path = "/profile/{userId}",method = RequestMethod.GET)
    public String getProfilePage(@PathVariable("userId") int userId,Model model){
        User user = userService.findUserById(userId);
        if(user == null){
            throw new RuntimeException("该用户不存在");
        }
        model.addAttribute("user",user);

        //点赞数量;
        int likeCount = likeService.findUserLikeCount(userId);
        model.addAttribute("likeCount",likeCount);

        //关注数量
        long followeeCount = followService.findFolloweeCount(userId,ENTITY_TYPE_USER);
        model.addAttribute("followeeCount",followeeCount);
        //粉丝数量
        long followerCount = followService.findFollowerCount(ENTITY_TYPE_USER,userId);
        model.addAttribute("followerCount",followerCount);
        //是否已关注
        boolean hasFollowed = false;
        if(hostHolder.getUser() != null){
            hasFollowed = followService.hasFollowed(hostHolder.getUser().getId(),ENTITY_TYPE_USER,userId);
        }
        model.addAttribute("hasFollowed",hasFollowed);

        return "/site/profile";
    }

}
