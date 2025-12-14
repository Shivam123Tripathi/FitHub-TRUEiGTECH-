

-- Creating the database
CREATE DATABASE IF NOT EXISTS fitplanhub;

-- Sertting the the database
USE fitplanhub;


--

-- Users Table
CREATE TABLE IF NOT EXISTS users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    full_name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    age INT NOT NULL,
    gender VARCHAR(255) NOT NULL,
    height DOUBLE NOT NULL,
    weight DOUBLE NOT NULL,
    fitness_goal VARCHAR(255) NOT NULL,
    role VARCHAR(255) NOT NULL DEFAULT 'USER',
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Trainers Table
CREATE TABLE IF NOT EXISTS trainers (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    full_name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    years_of_experience INT NOT NULL,
    specializations VARCHAR(500) NOT NULL,
    bio VARCHAR(1000),
    role VARCHAR(255) NOT NULL DEFAULT 'TRAINER',
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Creatted indexes for better query performance
CREATE INDEX idx_users_email ON users(email);
CREATE INDEX idx_trainers_email ON trainers(email);


SHOW TABLES;


DESCRIBE users;


DESCRIBE trainers;

-- Sample queries to view data after signup/login
-- SELECT * FROM users; only users
-- SELECT * FROM trainers; only trainers


