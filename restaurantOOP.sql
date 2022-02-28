DROP DATABASE IF EXISTS RestaurantOOP;

CREATE DATABASE IF NOT EXISTS RestaurantOOP;
USE RestaurantOOP;



CREATE TABLE restaurant (
  id INT NOT NULL AUTO_INCREMENT,
  name VARCHAR(50) NOT NULL,
  manager VARCHAR(50) NOT NULL,
  phone VARCHAR(20) NOT NULL,
  rating FLOAT NOT NULL,
  PRIMARY KEY (id)
);

INSERT INTO restaurant (name, manager, phone, rating) VALUES
(1,'McDonalds', 'John Smith', '897882348', 3.5),
(2,'Burger King', 'Jane Doe', '987856789', 4.2),
(3,'KFC', 'Bob Johnson', '987669876', 3.8),
(4,'Pizza Hut', 'Sara Lee', '897743218', 4.5),
(5,'Subway', 'Tom Jones', '887978765', 3.2),
(6,'Taco Bell', 'Amy Johnson', '787872468', 2.9),
(7,'Wendy''s', 'Tim Brown', '897651357', 4.1),
(8,'Popeyes', 'Lisa Green', '879863698', 3.7),
(9,'Chipotle', 'Mike Davis', '888887142', 4.3),
(10,'Starbucks', 'Karen White', '797869876', 3.9);


