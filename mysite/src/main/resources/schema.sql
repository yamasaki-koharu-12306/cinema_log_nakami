CREATE TABLE IF NOT EXISTS admin_users (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(100) NOT NULL UNIQUE,
    password_hash VARCHAR(100) NOT NULL,
    role VARCHAR(30) NOT NULL DEFAULT 'ADMIN',
    enabled BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL
);

CREATE UNIQUE INDEX IF NOT EXISTS idx_admin_users_username
    ON admin_users(username);

CREATE TABLE IF NOT EXISTS movies (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(200) NOT NULL,
    slug VARCHAR(220) NOT NULL UNIQUE,
    original_title VARCHAR(255),
    release_year INTEGER,
    genre VARCHAR(100),
    director VARCHAR(255),
    cast_members TEXT,
    rating DOUBLE PRECISION NOT NULL CHECK (rating BETWEEN 1 AND 5),
    recommendation INTEGER NOT NULL CHECK (recommendation BETWEEN 1 AND 5),
    watched_at DATE,
    poster_url VARCHAR(1000),
    summary VARCHAR(500) NOT NULL,
    review_body TEXT NOT NULL,
    memorable_point TEXT,
    status VARCHAR(30) NOT NULL,
    amazon_prime_url VARCHAR(1000),
    netflix_url VARCHAR(1000),
    disney_plus_url VARCHAR(1000),
    unext_url VARCHAR(1000),
    hulu_url VARCHAR(1000),
    abema_url VARCHAR(1000),
    other_service_name VARCHAR(100),
    other_service_url VARCHAR(1000),
    meta_title VARCHAR(255),
    meta_description VARCHAR(255),
    view_count BIGINT NOT NULL DEFAULT 0,
    published_at TIMESTAMP,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL
);

ALTER TABLE movies ADD COLUMN IF NOT EXISTS disney_plus_url VARCHAR(1000);
ALTER TABLE movies ADD COLUMN IF NOT EXISTS unext_url VARCHAR(1000);
ALTER TABLE movies ADD COLUMN IF NOT EXISTS hulu_url VARCHAR(1000);
ALTER TABLE movies ADD COLUMN IF NOT EXISTS abema_url VARCHAR(1000);

CREATE INDEX IF NOT EXISTS idx_movies_status_published ON movies(status, published_at DESC);
CREATE INDEX IF NOT EXISTS idx_movies_genre ON movies(genre);
CREATE INDEX IF NOT EXISTS idx_movies_rating ON movies(rating DESC);
CREATE INDEX IF NOT EXISTS idx_movies_recommendation ON movies(recommendation DESC);
CREATE INDEX IF NOT EXISTS idx_movies_release_year ON movies(release_year DESC);


CREATE TABLE IF NOT EXISTS affiliate_clicks (
    id BIGSERIAL PRIMARY KEY,
    movie_id BIGINT NOT NULL REFERENCES movies(id) ON DELETE CASCADE,
    service_name VARCHAR(50) NOT NULL,
    destination_url VARCHAR(2000) NOT NULL,
    clicked_at TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE INDEX IF NOT EXISTS idx_affiliate_clicks_movie
    ON affiliate_clicks(movie_id, clicked_at DESC);

CREATE INDEX IF NOT EXISTS idx_affiliate_clicks_service
    ON affiliate_clicks(service_name, clicked_at DESC);
