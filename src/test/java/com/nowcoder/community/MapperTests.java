package com.nowcoder.community;

import com.nowcoder.community.dao.*;
import com.nowcoder.community.entity.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = CommunityApplication.class)
public class MapperTests {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private DiscussPostMapper discussPostMapper;

    @Autowired
    private LoginTicketMapper loginTicketMapper;

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private MessageMapper messageMapper;

    @Test
    public void testSelectUser() {
        User user = userMapper.selectById(101);
        System.out.println(user);

        user = userMapper.selectByName("liubei");
        System.out.println(user);

        user = userMapper.selectByEmail("nowcoder101@sina.com");
        System.out.println(user);
    }

    @Test
    public void testInsertUser() {
        User user = new User();
        user.setUsername("test");
        user.setPassword("123456");
        user.setSalt("abc");
        user.setEmail("test@qq.com");
        user.setHeaderUrl("http://www.nowcoder.com/101.png");
        user.setCreateTime(new Date());

        int rows = userMapper.insertUser(user);
        System.out.println(rows);
        System.out.println(user.getId());
    }

    @Test
    public void updateUser() {
        int rows = userMapper.updateStatus(150, 1);
        System.out.println(rows);

        rows = userMapper.updateHeader(150, "http://www.nowcoder.com/102.png");
        System.out.println(rows);

        rows = userMapper.updatePassword(150, "hello");
        System.out.println(rows);
    }

    @Test
    public void testSelectPosts() {

        List<DiscussPost> list = discussPostMapper.selectDiscussPosts(149,0,10);
        for(DiscussPost discussPost:list){
            System.out.println(discussPost.getContent());
        }

        int total = discussPostMapper.selectDiscussPostRows(149);
        System.out.println(total);

    }

    @Test
    public void testLoginTicketsAdd(){
        LoginTicket loginTicket = new LoginTicket();
        loginTicket.setUserId(101);
        loginTicket.setTicket("abc");
        loginTicket.setStatus(0);
        loginTicket.setExpired(new Date(System.currentTimeMillis() + 1000 * 60 * 10));

        loginTicketMapper.insertLoginTicket(loginTicket);
    }

    @Test
    public void testLoginTicketsSelectAndUpdate(){
        LoginTicket loginTicket = loginTicketMapper.selectByTicket("abc");
        System.out.println(loginTicket);

        int result = loginTicketMapper.updateStatus("abc",1);
    }


    @Test
    public void testInsertDiscussPost(){
        DiscussPost discussPost = new DiscussPost();
        discussPost.setTitle("test");
        discussPost.setUserId(123);
        discussPost.setContent("这是一封测试帖子");
        discussPost.setType(0);
        discussPost.setStatus(0);
        discussPost.setCreateTime(new Date());
        discussPost.setCommentCount(10);
        discussPost.setScore(10);


        int result = discussPostMapper.insertDiscussPost(discussPost);
        System.out.println(result);
    }

    @Test
    public void testFindDiscussPostById(){
        DiscussPost discussPost = discussPostMapper.selectDiscussPostById(280);
        System.out.println(discussPost);
    }

    @Test
    public void testComentMapper(){
//        List<Comment> list = commentMapper.selectCommentsByEntity(1,228,0,10);
//        for(Comment c: list){
//            System.out.println(c.getContent());
//        }
//
//        int count = commentMapper.selectCountByEntity(1,228);
//        System.out.println(count);
        Comment comment = new Comment();
        comment.setEntityId(228);
        comment.setContent("nihao");
        comment.setCreateTime(new Date());
        comment.setUserId(111);
        comment.setTargetId(0);

        int result = commentMapper.insertComment(comment);
        System.out.println(result);

    }
    @Test
    public void testSelectLetters(){
        List<Message> list = messageMapper.selectConversations(111,0,10);

        for(Message message:list){
            System.out.println(message);
        }

        int result = messageMapper.selectConversationCount(111);
        System.out.println(result);

        List<Message> list2 = messageMapper.selectLetters("111_112",0,10);
        for(Message message:list2){
            System.out.println(message);
        }

        int result2 = messageMapper.selectLetterCount("111_112");
        System.out.println(result2);

        int result3 = messageMapper.selectLetterUnreadCount(131,"111_131");
        System.out.println(result3);

    }

    @Test
    public void testInsertUpdateMessage(){
        Message message = new Message();
        message.setFromId(112);
        message.setToId(111);
        message.setContent("hello");
        message.setConversationId("112_111");
        message.setStatus(1);
        message.setCreateTime(new Date());

        int count = messageMapper.insertMessage(message);
        System.out.println(count);




    }


























}
