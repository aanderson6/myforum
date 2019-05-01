DROP DATABASE IF EXISTS MyForum;

CREATE DATABASE IF NOT EXISTS MyForum;

USE MyForum;

CREATE TABLE IF NOT EXISTS Users (
    uid INT NOT NULL AUTO_INCREMENT,
    username VARCHAR(20) NOT NULL,
    shpw VARCHAR(100) NOT NULL,
    displayname VARCHAR(20) NOT NULL,
    email VARCHAR(100) NOT NULL,
    joindate Timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    karma INT NOT NULL,
    islocked Timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    PRIMARY KEY (uid),
    UNIQUE (username)
);

CREATE TABLE IF NOT EXISTS LoginAttempts (
    logid INT NOT NULL AUTO_INCREMENT,
    uid INT NOT NULL,
    logintime TIMESTAMP(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    succeeded TINYINT(1) NOT NULL,
    PRIMARY KEY (logid),
    FOREIGN KEY (uid) REFERENCES Users(uid)
);

CREATE TABLE IF NOT EXISTS SecurityQuestions (
    qid INT NOT NULL AUTO_INCREMENT,
    uid INT NOT NULL,
    question VARCHAR(100) NOT NULL,
    shanswer VARCHAR(100) NOT NULL,
    PRIMARY KEY (qid),
    FOREIGN KEY (uid) REFERENCES Users(uid)
);

CREATE TABLE IF NOT EXISTS Filters (
  filter_id INT NOT NULL AUTO_INCREMENT,
  uid INT NOT NULL,
  managed_uid INT NOT NULL,
  block_allow TINYINT(1) NOT NULL,
  PRIMARY KEY (filter_id),
  FOREIGN KEY (uid) REFERENCES Users(uid),
  FOREIGN KEY (managed_uid) REFERENCES Users(uid)
);

CREATE TABLE IF NOT EXISTS Settings (
    settings_id INT NOT NULL AUTO_INCREMENT,
    uid INT NOT NULL,
    collapse_read_messages TINYINT(1) NOT NULL,
    all_or_whitelist TINYINT(1) NOT NULL,
    notify_messages TINYINT(1) NOT NULL,
    open_mark_read TINYINT(1) NOT NULL,
    PRIMARY KEY (settings_id),
    FOREIGN KEY (uid) REFERENCES Users(uid)
);

CREATE TABLE IF NOT EXISTS Conversations (
    conversation_id INT NOT NULL AUTO_INCREMENT,
    from_uid INT NOT NULL,
    started TIMESTAMP(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    most_recent TIMESTAMP(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    chain_length INT NOT NULL,
    PRIMARY KEY (conversation_id),
    FOREIGN KEY (from_uid) REFERENCES Users(uid)
);

CREATE TABLE IF NOT EXISTS Messages (
  message_id INT NOT NULL AUTO_INCREMENT,
  conversation_id INT NOT NULL,
  from_uid INT NOT NULL,
  sent TIMESTAMP(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
  subject VARCHAR(140) NOT NULL,
  content VARCHAR(10000) NOT NULL,
  chain_num INT NOT NULL,
  PRIMARY KEY (message_id),
  FOREIGN KEY (conversation_id) REFERENCES Conversations(conversation_id),
  FOREIGN KEY (from_uid) REFERENCES Users(uid)
);

CREATE TABLE IF NOT EXISTS Membership (
    membership_id INT NOT NULL AUTO_INCREMENT,
    conversation_id INT NOT NULL,
    uid INT NOT NULL,
    unread_chain_num INT NOT NULL,
    visible TINYINT(1) NOT NULL,
    PRIMARY KEY (membership_id),
    FOREIGN KEY (conversation_id) REFERENCES Conversations(conversation_id),
    FOREIGN KEY (uid) REFERENCES Users(uid)
);

CREATE TABLE IF NOT EXISTS Notifications (
    notification_id INT NOT NULL AUTO_INCREMENT,
    uid INT NOT NULL,
    id_num INT NOT NULL,
    id_num_type VARCHAR(50) NOT NULL,
    PRIMARY KEY (notification_id),
    FOREIGN KEY (uid) REFERENCES Users(uid)
);

CREATE TABLE IF NOT EXISTS subforums (
    id INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    description VARCHAR(10000) NOT NULL,
    flair VARCHAR(50) NOT NULL,
    subscriber_count INT NOT NULL,
    created_date TIMESTAMP(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    created_by INT NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (created_by) REFERENCES Users(uid),
    UNIQUE (name)
);

CREATE TABLE IF NOT EXISTS subforum_settings (
    id INT NOT NULL AUTO_INCREMENT,
    subforum_id INT NOT NULL,
    rules VARCHAR(10000) NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (subforum_id) REFERENCES subforums(id)
);

CREATE TABLE IF NOT EXISTS modlist (
    id INT NOT NULL AUTO_INCREMENT,
    user_id INT NOT NULL,
    subforum_id INT NOT NULL,
    rank INT NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES Users(uid),
    FOREIGN KEY (subforum_id) REFERENCES subforums(id)
);

CREATE TABLE IF NOT EXISTS subscriptions (
    id INT NOT NULL AUTO_INCREMENT,
    subforum_id INT NOT NULL,
    user_id INT NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES Users(uid),
    FOREIGN KEY (subforum_id) REFERENCES subforums(id)
);

CREATE TABLE IF NOT EXISTS Posts (
    post_id INT NOT NULL AUTO_INCREMENT,
    posted_date TIMESTAMP(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    user_id INT,
    subforum_id INT NOT NULL,
    karma INT NOT NULL,
    title VARCHAR(140) NOT NULL,
    content VARCHAR(10000) NOT NULL,
    PRIMARY KEY (post_id),
    FOREIGN KEY (user_id) REFERENCES Users(uid),
    FOREIGN KEY (subforum_id) REFERENCES subforums(id)
);

CREATE TABLE IF NOT EXISTS Post_Karma (
    post_karma_id INT NOT NULL AUTO_INCREMENT,
    voted_date TIMESTAMP(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    user_id INT NOT NULL,
    post_id INT NOT NULL,
    up_or_down TINYINT(1) NOT NULL,
    PRIMARY KEY (post_karma_id),
    FOREIGN KEY (user_id) REFERENCES Users(uid),
    FOREIGN KEY (post_id) REFERENCES Posts(post_id)
);

CREATE TABLE IF NOT EXISTS Comments (
    comment_id INT NOT NULL AUTO_INCREMENT,
    comment_date TIMESTAMP(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    user_id INT,
    post_id INT NOT NULL,
    subforum_id INT NOT NULL,
    karma INT NOT NULL,
    content VARCHAR(10000) NOT NULL,
    parent_comment_id INT,
    comment_depth INT NOT NULL,
    has_children TINYINT(1) NOT NULL,
    PRIMARY KEY (comment_id),
    FOREIGN KEY (user_id) REFERENCES Users(uid),
    FOREIGN KEY (post_id) REFERENCES Posts(post_id),
    FOREIGN KEY (subforum_id) REFERENCES subforums(id),
    FOREIGN KEY (parent_comment_id) REFERENCES Comments(comment_id)
);

CREATE TABLE IF NOT EXISTS Comment_Karma (
    comment_karma_id INT NOT NULL AUTO_INCREMENT,
    voted_date TIMESTAMP(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    user_id INT NOT NULL,
    comment_id INT NOT NULL,
    up_or_down TINYINT(1) NOT NULL,
    PRIMARY KEY (comment_karma_id),
    FOREIGN KEY (user_id) REFERENCES Users(uid),
    FOREIGN KEY (comment_id) REFERENCES Comments(comment_id)
);
