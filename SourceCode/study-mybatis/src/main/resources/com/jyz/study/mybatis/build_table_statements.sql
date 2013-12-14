CREATE TABLE `t_blog` (  
  `id` int(11) NOT NULL AUTO_INCREMENT,  
  `title` varchar(255) DEFAULT NULL,  
  `content` varchar(255) DEFAULT NULL,  
  `owner` varchar(50) DEFAULT NULL,  
  PRIMARY KEY (`id`)  
)