-- users
DELETE FROM users; DELETE FROM customers;

INSERT INTO users(username,password,status,created_at,created_by)
VALUES
('alice_01','Password@123','ACT', CURRENT_TIMESTAMP, 'system'),
('bob_123','Welcome@2024','ACT',   CURRENT_TIMESTAMP, 'system'),
('charlie_1','Secret@123','DEL', CURRENT_TIMESTAMP, 'system');

-- customers (multiple rows for alice)
INSERT INTO customers(username, full_name, email, phone, status, created_at, created_by)
VALUES
('alice_01','Alice Johnson','alice@example.com','9990001111','ACT', CURRENT_TIMESTAMP, 'system'),
('alice_01','Alice Johnson','alice+alt@example.com','9990003333','ACT', CURRENT_TIMESTAMP, 'system'),
('bob_123','Robert Smith','bob@example.com','9990002222','ACT', CURRENT_TIMESTAMP, 'system');
