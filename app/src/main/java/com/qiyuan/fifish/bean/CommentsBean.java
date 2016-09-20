package com.qiyuan.fifish.bean;

import java.util.List;
public class CommentsBean{
    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public static class Data {
        private List<CommentItem> rows;

        public List<CommentItem> getRows() {
            return rows;
        }

        public void setRows(List<CommentItem> rows) {
            this.rows = rows;
        }
    }

    public static class CommentItem{
        private String _id;
        private String content;
        public long user_id;
        public String target_id;
        private String created_at;
        private User user;
        private String reply_user_nickname;
        public String target_small_cover_url;
        public boolean is_unread;
        private ReplyComment reply_comment;

        public ReplyComment getReply_comment() {
            return reply_comment;
        }

        public void setReply_comment(ReplyComment reply_comment) {
            this.reply_comment = reply_comment;
        }

        public String getReply_user_nickname() {
            return reply_user_nickname;
        }

        public void setReply_user_nickname(String reply_user_nickname) {
            this.reply_user_nickname = reply_user_nickname;
        }

        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }

        public User getUser() {
            return user;
        }

        public void setUser(User user) {
            this.user = user;
        }
    }

    public static class ReplyComment{
        private User user;

        public User getUser() {
            return user;
        }

        public void setUser(User user) {
            this.user = user;
        }
    }

    public static class User {
        private String _id;
        private String nickname;
        private String small_avatar_url;

        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
        }

        public String getSmall_avatar_url() {
            return small_avatar_url;
        }

        public void setSmall_avatar_url(String small_avatar_url) {
            this.small_avatar_url = small_avatar_url;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }
    }
}
