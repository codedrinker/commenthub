CREATE TABLE `t_authorization`
(
  `id` BIGINT(13) PRIMARY KEY NOT NULL,
  `token` VARCHAR(40) NOT NULL,
  `email` VARCHAR(100),
  `utime` BIGINT(13) DEFAULT '0',
  `ctime` BIGINT(13) DEFAULT '0'
);