INSERT INTO User(userName, password, firstName, lastName, email, role) VALUES
#     --Usuario con rol espectador
('viewer', '$2a$10$8o34vbwlRURkBGETvQzr8OCuPrk52E.j2ilm4KGKPrwNR89eNV/YG', 'Jane', 'Smith', 'jane.smith@example.com', 0),
#     --Usuario con rol taquillero
('ticketseller', '$2a$10$8o34vbwlRURkBGETvQzr8OCuPrk52E.j2ilm4KGKPrwNR89eNV/YG', 'Tom', 'Davis', 'tom.davis@example.com', 1);

INSERT INTO MovieTheater(name, capacity) VALUES
#     --Sala con 9 localidades
('Regal Cinemas', 9),
#     --Sala con más de 10 localidades
('Cineplex', 20);

INSERT INTO Movie(Title, Summary, Duration) VALUES
#     --Película 1
('The Godfather', 'The aging patriarch of an organized crime dynasty transfers control of his clandestine empire to his reluctant son.', 175),
#     --Película 2
('Forrest Gump', 'The presidencies of Kennedy and Johnson, the events of Vietnam, Watergate, and other history unfold through the perspective of an Alabama man with an IQ of 75.', 142);

INSERT INTO Session(movieId, movieTheaterId, date,time, price, seatsAvailable) VALUES
#     --Today Sesion 1
(1, 2, DATE_ADD(DATE(NOW()), INTERVAL '0' DAY),' 00:05:00', 10.50, 5),
#     --Today Sesion 2
(2, 1, DATE_ADD(DATE(NOW()), INTERVAL '0' DAY),' 23:55:00', 12.00, 20),
#     --Tomorrow
(2, 1, DATE_ADD(DATE(NOW()), INTERVAL '1' DAY),' 22:05:00', 12.00, 20),
(1, 2, DATE_ADD(DATE(NOW()), INTERVAL '1' DAY),' 17:35:00', 12.00, 9),
#     --Today + 2 days
(1, 1, DATE_ADD(DATE(NOW()), INTERVAL '2' DAY),' 07:45:00', 12.00, 20),
(2, 2, DATE_ADD(DATE(NOW()), INTERVAL '2' DAY),' 21:50:00', 12.00, 9),
#     --Today + 3 days
(2, 2, DATE_ADD(DATE(NOW()), INTERVAL '3' DAY),' 20:00:00', 12.00, 9),
(1, 1, DATE_ADD(DATE(NOW()), INTERVAL '3' DAY),' 12:55:00', 12.00, 20),
#     --Today + 4 days
(1, 2, DATE_ADD(DATE(NOW()), INTERVAL '4' DAY),' 13:25:00', 12.00, 9),
(2, 1, DATE_ADD(DATE(NOW()), INTERVAL '4' DAY),' 16:30:00', 12.00, 20),
#     --Today + 5 days
(2, 1, DATE_ADD(DATE(NOW()), INTERVAL '5' DAY),' 21:15:00', 12.00, 20),
(1, 2, DATE_ADD(DATE(NOW()), INTERVAL '5' DAY),' 20:55:00', 12.00, 9),
#     --Today + 6 days
(1, 1, DATE_ADD(DATE(NOW()), INTERVAL '6' DAY),' 06:30:00', 12.00, 20),
(2, 2, DATE_ADD(DATE(NOW()), INTERVAL '6' DAY),' 13:45:00', 12.00, 9);

INSERT INTO Purchase(reservedSeats, sessionId, userId, cardNumber, purchaseDate,used) VALUES
#     --Compra 1 de la sesion 1
(2, 1, 1, 123456, DATE_ADD(DATE(NOW()), INTERVAL '0' DAY),false),
#     --Compra 2 de la sesion 1
(3, 1, 1, 234567, DATE_ADD(DATE(NOW()), INTERVAL '0' DAY),false);