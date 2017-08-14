package com.javaedge.async.handler;

import com.javaedge.async.EventHandler;
import com.javaedge.async.EventModel;
import com.javaedge.async.EventType;
import com.javaedge.model.Message;
import com.javaedge.model.User;
import com.javaedge.service.MessageService;
import com.javaedge.service.UserService;
import com.javaedge.util.YouZhiUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by javaedge on 2016/7/30.
 */
@Component
public class LikeHandler implements EventHandler {
    @Autowired
    MessageService messageService;

    @Autowired
    UserService userService;

    @Override
    public void doHandle(EventModel model) {
        Message message = new Message();
        message.setFromId(YouZhiUtil.SYSTEM_USER_ID);
        message.setToId(model.getEntityOwnerId());
        message.setCreatedDate(new Date());
        User user = userService.getUser(model.getActorId());
        message.setContent("用户" + user.getName()
                + "赞了你的评论,http://127.0.0.1:8080/question/" + model.getExt("questionId"));

        messageService.addMessage(message);
    }

    @Override
    public List<EventType> getSupportEventTypes() {
        return Arrays.asList(EventType.LIKE);
    }
}