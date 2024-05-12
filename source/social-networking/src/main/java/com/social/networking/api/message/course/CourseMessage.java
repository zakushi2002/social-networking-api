package com.social.networking.api.message.course;

import com.social.networking.api.constant.SocialNetworkingConstant;
import com.social.networking.api.dto.course.CourseNotificationMessage;
import com.social.networking.api.form.notification.NotificationService;
import com.social.networking.api.model.Course;
import com.social.networking.api.model.Notification;
import com.social.networking.api.repository.NotificationRepository;
import com.social.networking.api.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CourseMessage implements MessageService<Course> {
    @Autowired
    NotificationService notificationService;
    @Autowired
    NotificationRepository notificationRepository;

    @Override
    public void createNotificationAndSendMessage(Integer notificationState, Course data, Integer notificationKind) {
        List<Notification> notifications = new ArrayList<>();
        if (data.getExpert() != null) {
            // Creates a notification for the given course and notification kind
            Notification notification = createNotification(data, notificationState, notificationKind, data.getExpert().getAccount().getId());
            notifications.add(notification);
        }
        // Saves the notifications to the database
        notificationRepository.saveAll(notifications);
        // Sends a message for each notification
        for (Notification notification : notifications) {
            notificationService.sendMessage(notification.getContent(), notificationKind, notification.getIdUser());
        }
    }

    @Override
    public Notification createNotification(Course data, Integer notificationState, Integer notificationKind, Long accountId) {
        Notification notification = notificationService.createNotification(notificationState, notificationKind);
        String jsonMessage = getJsonMessage(data, notification);
        notification.setIdUser(accountId);
        notification.setContent(jsonMessage);
        if (notificationKind.equals(SocialNetworkingConstant.NOTIFICATION_KIND_COURSE_APPROVED)) {
            notificationRepository.deleteAllByIdUserAndKindAndRefId(accountId, SocialNetworkingConstant.NOTIFICATION_KIND_COURSE_APPROVED, data.getId().toString());
            notification.setRefId(data.getId().toString());
        }
        return notification;
    }

    @Override
    public String getJsonMessage(Course data, Notification notification) {
        CourseNotificationMessage courseNotificationMessage = new CourseNotificationMessage();
        courseNotificationMessage.setNotificationId(notification.getId());
        courseNotificationMessage.setExpertId(data.getExpert().getId());
        courseNotificationMessage.setCourseId(data.getId());
        courseNotificationMessage.setCourseTitle(data.getTitle());
        courseNotificationMessage.setExpertName(data.getExpert().getAccount().getFullName());
        courseNotificationMessage.setExpertAvatar(data.getExpert().getAccount().getAvatarPath());
        courseNotificationMessage.setStartDate(data.getStartDate());
        courseNotificationMessage.setEndDate(data.getEndDate());
        return notificationService.convertObjectToJson(courseNotificationMessage);
    }
}
