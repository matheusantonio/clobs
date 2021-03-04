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