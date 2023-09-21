# hazelcast
Hazelcast Code Samples Associated with Videos

Hazelcast
https://hazelcast.com/

Hazelcast Jet Tutorial
https://jet-start.sh/docs/tutorials/cdc


https://jet-start.sh/docs/api/submitting-jobs#:~:text=To%20execute%20a%20Jet%20job,ways%20to%20submit%20the%20job.
Spring Cache
https://docs.spring.io/spring-boot/docs/2.1.6.RELEASE/reference/html/boot-features-caching.html


MYSQL
https://dev.mysql.com/doc/mysql-installation-excerpt/5.7/en/installing.html

Drawing
https://app.diagrams.net/

Hazel Cast Jet Tutorial
https://jet-start.sh/docs/tutorials/cdc

Customer Table
CREATE TABLE `customers` (
  `id` int NOT NULL,
  `first_name` varchar(255) COLLATE latin1_bin DEFAULT NULL,
  `last_name` varchar(255) COLLATE latin1_bin DEFAULT NULL,
  `email` varchar(255) COLLATE latin1_bin DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_bin;

CREATE TABLE `customer_dependent` (
  `id` int NOT NULL,
  `First_Name` varchar(45) DEFAULT NULL,
  `Last_Name` varchar(45) DEFAULT NULL,
  `Customer_Id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `Customer_Dependent_idx` (`Customer_Id`),
  CONSTRAINT `customer_dependent_fk` FOREIGN KEY (`Customer_Id`) REFERENCES `customers` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


Data in the attahed CSV file.

