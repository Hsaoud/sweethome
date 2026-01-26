-- Sweet Home Database Schema
-- Generated for PostgreSQL

CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    dtype VARCHAR(31) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    phone VARCHAR(255),
    profile_image VARCHAR(255),
    role VARCHAR(255) NOT NULL,
    enabled BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);

CREATE TABLE homers (
    id BIGINT PRIMARY KEY,
    address VARCHAR(255),
    city VARCHAR(255),
    postal_code VARCHAR(255),
    additional_info VARCHAR(255),
    FOREIGN KEY (id) REFERENCES users(id)
);

CREATE TABLE cleaners (
    id BIGINT PRIMARY KEY,
    headline VARCHAR(500),
    bio VARCHAR(2000),
    hourly_rate NUMERIC(19, 2),
    experience_years INTEGER,
    city VARCHAR(255),
    service_area VARCHAR(255),
    available BOOLEAN DEFAULT TRUE,
    FOREIGN KEY (id) REFERENCES users(id)
);

CREATE TABLE categories (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE,
    description VARCHAR(255)
);

CREATE TABLE skills (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE,
    description VARCHAR(255)
);

CREATE TABLE cleaner_categories (
    cleaner_id BIGINT NOT NULL,
    category_id BIGINT NOT NULL,
    PRIMARY KEY (cleaner_id, category_id),
    FOREIGN KEY (cleaner_id) REFERENCES cleaners(id),
    FOREIGN KEY (category_id) REFERENCES categories(id)
);

CREATE TABLE cleaner_skills (
    cleaner_id BIGINT NOT NULL,
    skill_id BIGINT NOT NULL,
    PRIMARY KEY (cleaner_id, skill_id),
    FOREIGN KEY (cleaner_id) REFERENCES cleaners(id),
    FOREIGN KEY (skill_id) REFERENCES skills(id)
);

CREATE TABLE reviews (
    id BIGSERIAL PRIMARY KEY,
    reviewer_id BIGINT NOT NULL,
    reviewee_id BIGINT NOT NULL,
    rating INTEGER NOT NULL,
    comment VARCHAR(1000),
    created_at TIMESTAMP,
    FOREIGN KEY (reviewer_id) REFERENCES users(id),
    FOREIGN KEY (reviewee_id) REFERENCES users(id)
);

-- Indexes for performance
CREATE INDEX idx_cleaner_city ON cleaners(city);
CREATE INDEX idx_homer_city ON homers(city);
CREATE INDEX idx_review_reviewee ON reviews(reviewee_id);
