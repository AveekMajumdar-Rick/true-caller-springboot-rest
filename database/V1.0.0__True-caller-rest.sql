DROP TABLE true_caller.user;
DROP TABLE true_caller.user_info;

CREATE TABLE `user` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `password` varchar(50) DEFAULT NULL,
  `username` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE `user_info` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `email_address` varchar(100) DEFAULT NULL,
  `name` varchar(80) DEFAULT NULL,
  `phone_number` varchar(10) DEFAULT NULL,
  `spam` bit(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
