USE clobs;

CREATE TABLE `bookmark` (
  `id` int NOT NULL AUTO_INCREMENT,
  `url` varchar(255) DEFAULT NULL,
  `name` varchar(150) DEFAULT NULL,
  PRIMARY KEY (`id`)
);

CREATE TABLE `tag` (
  `id` int NOT NULL AUTO_INCREMENT,
  `value` varchar(150) DEFAULT NULL,
  PRIMARY KEY (`id`)
);

CREATE TABLE `user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(45) DEFAULT NULL,
  `password` varchar(256) DEFAULT NULL,
  `registered` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
);

CREATE TABLE `tagBookmark` (
  `tagId` int NOT NULL,
  `bookmarkId` int NOT NULL,
  `userId` int NOT NULL,
  PRIMARY KEY (`tagId`,`bookmarkId`,`userId`),
  KEY `bookmarkId` (`bookmarkId`),
  KEY `userId` (`userId`),
  CONSTRAINT `tagBookmark_ibfk_1` FOREIGN KEY (`tagId`) REFERENCES `tag` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `tagBookmark_ibfk_2` FOREIGN KEY (`bookmarkId`) REFERENCES `bookmark` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `tagBookmark_ibfk_3` FOREIGN KEY (`userId`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
);

CREATE TABLE `userBookmark` (
  `userId` int NOT NULL,
  `bookmarkId` int NOT NULL,
  `createdAt` datetime DEFAULT NULL,
  `definedName` varchar(50) DEFAULT NULL,
  `private` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`userId`,`bookmarkId`),
  KEY `bookmarkId` (`bookmarkId`),
  CONSTRAINT `userBookmark_ibfk_1` FOREIGN KEY (`userId`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `userBookmark_ibfk_2` FOREIGN KEY (`bookmarkId`) REFERENCES `bookmark` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
);


-- =========================================================================================================================

INSERT INTO `user` (`username`, `password`, `registered`) VALUES 
(
  'matheus.antonio.c@outlook.com', 
  'bcrypt+sha512$105d47d33d57142ef638214c5ef731a5$12$7e817065230fa5211f4256081fb77d1286ace82cfc5c229e', 
  1
);

INSERT INTO `user` (`username`, `password`, `registered`) VALUES 
(
  'test.user.1@clobs.com', 
  'bcrypt+sha512$df6d8db97518626fa1d2a3b598f19534$12$f879cbcbfc7bed1750ab86f0251e6d590c6d6936643da7dc', 
  1
);

INSERT INTO `user` (`username`, `password`, `registered`) VALUES 
(
  'test.user.2@clobs.com', 
  'bcrypt+sha512$406d1b54683990ac1172676c4f88ebb5$12$ebc053cb3ec755466d2eb903baa024e6c4cfdaac9e141f3e', 
  1
);

INSERT INTO `user` (`username`, `password`, `registered`) VALUES 
(
  'test.user.3@clobs.com', 
  'bcrypt+sha512$ac42bd1356854102fa93c10b2b4e839a$12$00a3d9bb827b8c0ebb80f7a79d2f61f294a3fe6ccabf5ed1', 
  1
);
