# hazelcast
Hazelcast Code Samples Associated with Videos

Hazelcast
https://hazelcast.com/

Spring Cache
https://docs.spring.io/spring-boot/docs/2.1.6.RELEASE/reference/html/boot-features-caching.html


Wireshark
https://www.wireshark.org/download.html
(src host 192.168.1.50 or dst host 192.168.1.50) and (src portrange 5801-5930 or dst  portrange 5801-5930 or src port 54320 or dst port 54320 or src net 224.0.0.0/4 or dst net 224.0.0.0/4)

(src host 192.168.1.111 or dst host 192.168.1.111) and (src portrange 5801-5930 or dst  portrange 5801-5930 or src port 54320 or dst port 54320 or src net 224.0.0.0/4 or dst net 224.0.0.0/4)

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

