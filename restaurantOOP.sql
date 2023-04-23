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

CREATE TABLE booking (
  booking_id INT NOT NULL AUTO_INCREMENT,
  restaurant_id INT NOT NULL,
  customer_name VARCHAR(50) NOT NULL,
  customer_phone VARCHAR(20) NOT NULL,
  booking_date DATE NOT NULL,
  booking_time TIME NOT NULL,
  num_guests INT NOT NULL,
  FOREIGN KEY (restaurant_id) REFERENCES restaurant(id),
  PRIMARY KEY (booking_id)
);


INSERT INTO restaurant (id, name, manager, phone, rating) VALUES
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


INSERT INTO booking (booking_id, restaurant_id, customer_name, customer_phone, booking_date, booking_time, num_guests) VALUES
(1, 1, 'Don Omar', '823456789', '2023-05-01', '18:00:00', 4),
(2, 1, 'Bad Bunny', '987654321', '2023-05-02', '19:30:00', 2),
(3, 3, 'Daddy Yankee', '877665432', '2023-05-03', '20:00:00', 6),
(4, 5, 'Lucas Brown', '897889782', '2023-05-04', '12:00:00', 3),
(5, 7, 'Grace Martin', '846473821', '2023-05-05', '14:30:00', 5),
(6, 9, 'Jacob Rodriguez', '987546732', '2023-05-06', '17:00:00', 2),
(7, 10, 'Sophia Davis', '898766554', '2023-05-07', '18:00:00', 4),
(8, 2, 'Ethan Johnson', '897678988', '2023-05-08', '19:00:00', 3),
(9, 4, 'Ava Wilson', '896765443', '2023-05-09', '20:00:00', 2),
(10, 6, 'Benjamin Lee', '8987676542', '2023-05-10', '12:30:00', 5);