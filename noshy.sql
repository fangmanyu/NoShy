create table browse_information
(
  browse_id   int auto_increment
    primary key,
  pageviews   int default '0'    null
  comment '浏览量',
  likes       int default '0'    null
  comment '点赞量',
  shares      int default '0'    null
  comment '转发分享量',
  browse_type int(1) default '0' null
  comment '浏览类型.0 不可用;1 视频;2帖子'
);

create table image
(
  image_id    int auto_increment
    primary key,
  image_url   varchar(10000) null,
  type        int(1)         null,
  create_time datetime       null,
  belong_id   char(32)       null
);

create table post_category
(
  category_id   int(4) auto_increment
    primary key,
  category_name varchar(1000)  null,
  description   varchar(10000) null,
  create_time   datetime       null
);

create table school
(
  school_id   int(4) auto_increment
    primary key,
  school_name varchar(255) null,
  school_desc varchar(255) null,
  create_time datetime     null,
  school_addr varchar(255) null
);

create table user
(
  user_id       int auto_increment
    primary key,
  user_name     varchar(255)       null,
  user_password varchar(255)       null,
  user_phone    char(11)           null,
  user_status   int(2) default '0' not null,
  create_time   datetime           null,
  school_id     int(4)             null,
  constraint user_ibfk_1
  foreign key (school_id) references school (school_id)
);

create table comment
(
  comment_id      int auto_increment
    primary key,
  browse_id       int             null,
  parent_id       int             null
  comment '父类评论编号',
  comment_content varchar(10000)  not null,
  user_id         int             null,
  comment_likes   int default '0' null
  comment '评论点赞量',
  constraint comment_ibfk_1
  foreign key (browse_id) references browse_information (browse_id),
  constraint comment_ibfk_3
  foreign key (parent_id) references comment (comment_id),
  constraint comment_ibfk_2
  foreign key (user_id) references user (user_id)
);

create index browse_id
  on comment (browse_id);

create index parent_id
  on comment (parent_id);

create index user_id
  on comment (user_id);

create table post
(
  post_id        int auto_increment
    primary key,
  title          varchar(1000)      null,
  status         int(2) default '1' null
  comment '4-置顶；3-加精；2-热门；1-可被浏览；0-不可被浏览；-1-删除帖子',
  page_view      int default '0'    null,
  authority      int(2) default '0' null
  comment '2-私人；1-学校；0-公开',
  description    varchar(1000)      null,
  user_id        int                null
  comment '帖子创始人',
  post_category  int                null,
  parent_id      int                null
  comment '父类帖子Id',
  create_time    datetime           null,
  last_edit_time datetime           null,
  browse_id      int                null,
  constraint post_ibfk_1
  foreign key (user_id) references user (user_id),
  constraint post_ibfk_3
  foreign key (post_category) references post_category (category_id),
  constraint post_ibfk_2
  foreign key (parent_id) references post (post_id),
  constraint post_ibfk_4
  foreign key (browse_id) references browse_information (browse_id)
);

create index browse_id
  on post (browse_id);

create index parent_id
  on post (parent_id);

create index post_category
  on post (post_category);

create index user_id
  on post (user_id);

create table post_information
(
  info_id      int auto_increment
    primary key,
  info_content varchar(10000) null,
  user_id      int(4)         null,
  create_time  datetime       null,
  image_url    varchar(10000) null,
  post_id      int            null,
  constraint post_information_ibfk_1
  foreign key (user_id) references user (user_id),
  constraint post_information_ibfk_2
  foreign key (post_id) references post (post_id)
);

create index post_id
  on post_information (post_id);

create index user_id
  on post_information (user_id);

create index school_id
  on user (school_id);

create table user_information
(
  user_id            int                not null
    primary key,
  head_portrait_addr varchar(10000)     null
  comment '头像地址',
  rank               int(1) default '0' null
  comment '等级',
  experience         int default '0'    null
  comment '经验值',
  introduction       varchar(10000)     null,
  constraint user_information_ibfk_1
  foreign key (user_id) references user (user_id)
);

create table challenge
(
  challenge_id          int auto_increment
    primary key,
  challenge_name        varchar(1000)      null,
  challenge_description varchar(10000)     null,
  status                int(1) default '0' null
  comment '0-正在进行;1-已经完成',
  create_time           datetime           null,
  browse_id             int                null,
  user_id               int                null,
  constraint challenge_ibfk_1
  foreign key (browse_id) references browse_information (browse_id),
  constraint challenge_ibfk_2
  foreign key (user_id) references user_information (user_id)
);

create index browse_id
  on challenge (browse_id);

create index user_id
  on challenge (user_id);

create table rank
(
  rank_id        int auto_increment
    primary key,
  grade          int(1) default '0' null,
  type           int(1) default '0' null
  comment '0-点赞榜;1-浏览榜;2-关注榜',
  status         int(1) default '0' null
  comment '0-进行;1-完成',
  `like`         int default '0'    null
  comment '点赞量',
  create_time    datetime           null,
  last_edit_time datetime           null,
  user_id        int                null,
  challenge_id   int                null,
  constraint rank_ibfk_1
  foreign key (user_id) references user_information (user_id),
  constraint rank_ibfk_2
  foreign key (challenge_id) references challenge (challenge_id)
);

create index challenge_id
  on rank (challenge_id);

create index user_id
  on rank (user_id);

create table user_browse_record
(
  browse_record_id   int auto_increment
    primary key,
  user_id            int      null,
  browse_id          int      null,
  browse_record_time datetime null,
  constraint user_browse_record_ibfk_1
  foreign key (user_id) references user_information (user_id),
  constraint user_browse_record_ibfk_2
  foreign key (browse_id) references browse_information (browse_id)
);

create index browse_id
  on user_browse_record (browse_id);

create index user_id
  on user_browse_record (user_id);

create table user_collection_record
(
  collection_record_id   int auto_increment
    primary key,
  user_id                int      null,
  browse_id              int      null,
  collection_record_time datetime null,
  constraint user_collection_record_ibfk_1
  foreign key (user_id) references user_information (user_id),
  constraint user_collection_record_ibfk_2
  foreign key (browse_id) references browse_information (browse_id)
);

create index browse_id
  on user_collection_record (browse_id);

create index user_id
  on user_collection_record (user_id);

create table user_favorite_record
(
  favorite_record_id   int auto_increment
    primary key,
  user_id              int      null,
  browse_id            int      null,
  favorite_record_time datetime null,
  constraint user_favorite_record_ibfk_1
  foreign key (user_id) references user_information (user_id),
  constraint user_favorite_record_ibfk_2
  foreign key (browse_id) references browse_information (browse_id)
);

create index browse_id
  on user_favorite_record (browse_id);

create index user_id
  on user_favorite_record (user_id);

create table video_category
(
  category_id   int(4) auto_increment
    primary key,
  category_name varchar(1000) null,
  aliyun_id     int           not null,
  parent_id     int           null,
  constraint aliyun_id
  unique (aliyun_id),
  constraint video_category_ibfk_1
  foreign key (parent_id) references video_category (aliyun_id)
);

create table video
(
  video_id       char(32)      not null
    primary key,
  title          varchar(255)  not null,
  name           varchar(255)  not null,
  video_category int           null,
  description    varchar(255)  null,
  image_url      varchar(1024) null,
  status         varchar(255)  not null,
  create_time    datetime      null,
  last_edit_time datetime      null,
  user_id        int           null,
  browse_id      int           null,
  constraint video_ibfk_2
  foreign key (video_category) references video_category (aliyun_id),
  constraint video_ibfk_1
  foreign key (user_id) references user (user_id),
  constraint video_ibfk_3
  foreign key (browse_id) references browse_information (browse_id)
);

create table challenge_video_relation
(
  challenge_id int                not null,
  video_id     char(32)           not null,
  create_time  datetime           null,
  status       int(1) default '0' null
  comment '0-正在进行;1-完成挑战',
  primary key (challenge_id, video_id),
  constraint challenge_video_relation_ibfk_1
  foreign key (challenge_id) references challenge (challenge_id),
  constraint challenge_video_relation_ibfk_2
  foreign key (video_id) references video (video_id)
);

create index video_id
  on challenge_video_relation (video_id);

create index browse_id
  on video (browse_id);

create index user_id
  on video (user_id);

create index video_category
  on video (video_category);

create index parent_id
  on video_category (parent_id);

create table video_tag
(
  tag_id   int(6) auto_increment
    primary key,
  name     varchar(255) not null,
  video_id char(32)     null,
  constraint video_tag_ibfk_2
  foreign key (video_id) references video (video_id)
);

create index video_id
  on video_tag (video_id);


