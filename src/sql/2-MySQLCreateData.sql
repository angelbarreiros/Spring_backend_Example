INSERT INTO Movie(Title, Summary, Duration) VALUES
                                                               ('The Shawshank Redemption', 'Two imprisoned men bond over a number of years, finding solace and eventual redemption through acts of common decency.', 142),
                                                               ('The Godfather', 'The aging patriarch of an organized crime dynasty transfers control of his clandestine empire to his reluctant son.', 175),
                                                               ('The Dark Knight', 'When the menace known as the Joker wreaks havoc and chaos on the people of Gotham, Batman must accept one of the greatest psychological and physical tests of his ability to fight injustice.', 152),
                                                               ('Pulp Fiction', 'The lives of two mob hitmen, a boxer, a gangster and his wife, and a pair of diner bandits intertwine in four tales of violence and redemption.', 154),
                                                               ('Forrest Gump', 'The presidencies of Kennedy and Johnson, the events of Vietnam, Watergate, and other history unfold through the perspective of an Alabama man with an IQ of 75.', 142);

INSERT INTO MovieTheater(name, capacity) VALUES
                                             ('Regal Cinemas', 150),
                                             ('AMC Theatres', 200),
                                             ('Cineplex', 100),
                                             ('Cinemark', 120),
                                             ('Odeon Cinemas', 180);

INSERT INTO Session(movieId, movieTheaterId, date,time, price, seatsAvailable) VALUES
                                                                              (1, 2, '2023-03-03',' 18:00:00', 10.50, 100),
                                                                              (2, 1, '2023-03-04',' 20:30:00', 12.00, 150),
                                                                              (3, 3, '2023-03-05',' 14:00:00', 8.50, 80),
                                                                              (4, 4, '2023-03-06',' 19:45:00', 9.75, 110),
                                                                              (5, 5, '2023-03-07',' 17:15:00', 11.00, 170);

INSERT INTO User(userName, password, firstName, lastName, email, role) VALUES
                                                                           ('jane_smith', 'password456', 'Jane', 'Smith', 'jane.smith@example.com', 0),
                                                                           ('bob_johnson', 'password789', 'Bob', 'Johnson', 'bob.johnson@example.com', 0),
                                                                           ('amy_wilson', 'passwordabc', 'Amy', 'Wilson', 'amy.wilson@example.com', 0),
                                                                           ('tom_davis', 'passworddef', 'Tom', 'Davis', 'tom.davis@example.com', 1);

INSERT INTO Purchase(reservedSeats, sessionId, userId, cardNumber, purchaseDate,used) VALUES
                                                                                                     (2, 1, 1, 123456, '2023-03-03',false),
                                                                                                     (3, 2, 2, 234567, '2023-03-04',false),
                                                                                                     (1, 3, 3, 345678, '2023-03-05',false),
                                                                                                     (4, 4, 4, 456789, '2023-03-06',false),
                                                                                                     (2, 5, 4, 567890, '2023-03-07',false);


INSERT INTO Movie(Title, Summary, Duration) VALUES
                                                               ('The Matrix', 'A computer hacker learns from mysterious rebels about the true nature of his reality and his role in the war against its controllers.', 136),
                                                               ('The Lord of the Rings: The Fellowship of the Ring', 'A meek Hobbit from the Shire and eight companions set out on a journey to destroy the powerful One Ring and save Middle-earth from the Dark Lord Sauron.', 178),
                                                               ('The Lion King', 'A lion prince, Simba, must overcome the betrayal and murder of his father to become king of the Pride Lands.', 88),
                                                               ('Star Wars: Episode IV - A New Hope', 'Luke Skywalker joins forces with a Jedi Knight, a cocky pilot, a Wookiee, and two droids to save the galaxy from the Empire''s world-destroying battle station, while also attempting to rescue Princess Leia from the mysterious Darth Vader.', 121),
                                                               ('Jurassic Park', 'A pragmatic paleontologist visiting an almost complete theme park is tasked with protecting a couple of kids after a power failure causes the park''s cloned dinosaurs to run loose.', 127);

INSERT INTO MovieTheater(name, capacity) VALUES
                                             ('Vue Cinemas', 160),
                                             ('Cineworld', 190),
                                             ('Empire Cinemas', 120),
                                             ('ODEON Luxe', 150),
                                             ('Picturehouse Cinemas', 100);

INSERT INTO Session(movieId, movieTheaterId, date,time, price, seatsAvailable) VALUES
                                                                              (6, 1, '2023-03-10',' 17:30:00', 9.50, 120),
                                                                              (7, 2, '2023-03-10',' 19:45:00', 12.50, 170),
                                                                              (8, 3, '2023-03-10',' 14:00:00', 8.00, 80),
                                                                              (9, 4, '2023-03-10',' 21:15:00', 10.00, 140),
                                                                              (10, 5, '2023-03-10',' 18:30:00', 11.50, 90);

INSERT INTO User(userName, password, firstName, lastName, email, role) VALUES
                                                                           ('johndoe', 'password123', 'John', 'Doe', 'johndoe@example.com', 1),
                                                                           ('bobsmith', 'ilovecats', 'Bob', 'Smith', 'bobsmith@example.com', 2),
                                                                           ('sarahjones', 'pa$$w0rd', 'Sarah', 'Jones', 'sarahjones@example.com', 3),
                                                                           ('mikebrown', 'letmein', 'Mike', 'Brown', 'mikebrown@example.com', 3);

INSERT INTO Purchase(reservedSeats, sessionId, userId, cardNumber, purchaseDate,used) VALUES
                                                                                                     (2, 1, 1, 123456789, '2023-03-08',false),
                                                                                                     (4, 4, 2, 234567890, '2023-03-11',false),
                                                                                                     (1, 2, 3, 345678901, '2023-03-09',false),
                                                                                                     (3, 5, 4, 456789012, '2023-03-12',false),
                                                                                                     (2, 3, 5, 567890123, '2023-03-10',false),
                                                                                                    (4, 4, 2, 123459, '2023-03-03',false);
INSERT INTO Session(movieId, movieTheaterId, date,time, price, seatsAvailable) VALUES
                                                                              (6, 1, '2023-03-11',' 17:30:00', 9.50, 120),
                                                                              (7, 2, '2023-03-11',' 19:45:00', 12.50, 170),
                                                                              (8, 3, '2023-03-11',' 14:00:00', 8.00, 80),
                                                                              (9, 4, '2023-03-11',' 21:15:00', 10.00, 140),
                                                                              (10, 5, '2023-03-11',' 18:30:00', 11.50, 90);
INSERT INTO Session(movieId, movieTheaterId, date,time, price, seatsAvailable) VALUES
                                                                              (6, 1, '2023-03-11',' 17:30:00', 9.50, 120),
                                                                              (7, 2, '2023-03-11',' 19:45:00', 12.50, 170),
                                                                              (8, 3, '2023-03-11',' 14:00:00', 8.00, 80),
                                                                              (9, 4, '2023-03-11',' 21:15:00', 10.00, 140),
                                                                              (10, 5, '2023-03-11',' 18:30:00', 11.50, 90);
INSERT INTO Session(movieId, movieTheaterId, date,time, price, seatsAvailable) VALUES
                                                                                   (6, 1, '2023-03-11',' 22:30:00', 9.50, 120),
                                                                                   (7, 2, '2023-03-11',' 22:45:00', 12.50, 170),
                                                                                   (8, 3, '2023-03-11',' 22:10:00', 8.00, 80),
                                                                                   (9, 4, '2023-03-11',' 22:20:00', 10.00, 140),
                                                                                   (10, 5, '2023-03-11',' 22:30:00', 11.50, 90);