-- FitPlanHub Database Setup Script
-- Run this in MySQL Workbench to set up the database

-- Create the database
CREATE DATABASE IF NOT EXISTS fitplanhub;

-- Use the database
USE fitplanhub;

-- Note: Tables will be auto-created by Spring Boot JPA when the application starts
-- with hibernate.ddl-auto=update setting

-- However, if you want to create them manually or verify structure, here are the table definitions:

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

-- Create indexes for better query performance
CREATE INDEX idx_users_email ON users(email);
CREATE INDEX idx_trainers_email ON trainers(email);

-- Verify tables were created
SHOW TABLES;

-- Check users table structure
DESCRIBE users;

-- Check trainers table structure
DESCRIBE trainers;

-- Sample queries to view data after signup/login
-- SELECT * FROM users;
-- SELECT * FROM trainers;

-- Query to verify BCrypt hashed passwords (should see long hashed strings)
-- SELECT id, email, LEFT(password, 20) as password_preview, role FROM users;
-- SELECT id, email, LEFT(password, 20) as password_preview, role FROM trainers;
