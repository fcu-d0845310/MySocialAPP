CREATE TABLE IF NOT EXISTS users (
                                     user_id      BIGSERIAL PRIMARY KEY,
                                     user_name    VARCHAR(50)  NOT NULL,
    phone        VARCHAR(20)  NOT NULL UNIQUE,
    email        VARCHAR(100),
    password     VARCHAR(255) NOT NULL,
    cover_image  TEXT,
    biography    TEXT
    );

CREATE TABLE IF NOT EXISTS posts (
                                     post_id     BIGSERIAL PRIMARY KEY,
                                     user_id     BIGINT       NOT NULL REFERENCES users(user_id),
    content     TEXT         NOT NULL,
    image       TEXT,
    created_at  TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP
    );

CREATE TABLE IF NOT EXISTS comments (
                                        comment_id  BIGSERIAL PRIMARY KEY,
                                        user_id     BIGINT       NOT NULL REFERENCES users(user_id),
    post_id     BIGINT       NOT NULL REFERENCES posts(post_id),
    content     TEXT         NOT NULL,
    created_at  TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP
    );

CREATE OR REPLACE FUNCTION sp_register(
    p_user_name    VARCHAR,
    p_phone        VARCHAR,
    p_password     VARCHAR,
    p_email        VARCHAR DEFAULT NULL,
    p_biography    TEXT    DEFAULT NULL,
    p_cover_image  TEXT    DEFAULT NULL
) RETURNS BIGINT AS $$
DECLARE
new_id BIGINT;
BEGIN
INSERT INTO users(user_name, phone, password, email, biography, cover_image)
VALUES (p_user_name, p_phone, p_password, p_email, p_biography, p_cover_image)
    RETURNING user_id INTO new_id;
RETURN new_id;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION sp_find_user_by_phone(
    p_phone VARCHAR
) RETURNS TABLE(
    user_id BIGINT, user_name VARCHAR, phone VARCHAR, email VARCHAR,
    password VARCHAR, cover_image TEXT, biography TEXT
) AS $$
BEGIN
RETURN QUERY
SELECT u.user_id, u.user_name, u.phone, u.email, u.password, u.cover_image, u.biography
FROM users u
WHERE u.phone = p_phone;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION sp_create_post(
    p_user_id BIGINT,
    p_content TEXT,
    p_image   TEXT DEFAULT NULL
) RETURNS BIGINT AS $$
DECLARE
new_id BIGINT;
BEGIN
INSERT INTO posts(user_id, content, image)
VALUES (p_user_id, p_content, p_image)
    RETURNING post_id INTO new_id;
RETURN new_id;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION sp_list_posts()
RETURNS TABLE(
    post_id BIGINT, content TEXT, image TEXT, created_at TIMESTAMP,
    user_id BIGINT, user_name VARCHAR, comment_count BIGINT
) AS $$
BEGIN
RETURN QUERY
SELECT p.post_id, p.content, p.image, p.created_at,
       u.user_id, u.user_name,
       COUNT(c.comment_id)
FROM posts p
         JOIN users u ON u.user_id = p.user_id
         LEFT JOIN comments c ON c.post_id = p.post_id
GROUP BY p.post_id, u.user_id
ORDER BY p.created_at DESC;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION sp_update_post(
    p_post_id BIGINT,
    p_user_id BIGINT,
    p_content TEXT,
    p_image   TEXT DEFAULT NULL
) RETURNS INT AS $$
DECLARE
affected INT;
BEGIN
UPDATE posts SET content = p_content, image = p_image
WHERE post_id = p_post_id AND user_id = p_user_id;
GET DIAGNOSTICS affected = ROW_COUNT;
RETURN affected;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION sp_delete_post_with_comments(
    p_post_id BIGINT,
    p_user_id BIGINT
) RETURNS INT AS $$
DECLARE
affected INT;
BEGIN
    IF NOT EXISTS (SELECT 1 FROM posts WHERE post_id = p_post_id AND user_id = p_user_id) THEN
        RETURN 0;
END IF;

DELETE FROM comments WHERE post_id = p_post_id;
DELETE FROM posts WHERE post_id = p_post_id AND user_id = p_user_id;

GET DIAGNOSTICS affected = ROW_COUNT;
RETURN affected;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION sp_create_comment(
    p_post_id BIGINT,
    p_user_id BIGINT,
    p_content TEXT
) RETURNS BIGINT AS $$
DECLARE
new_id BIGINT;
BEGIN
INSERT INTO comments(post_id, user_id, content)
VALUES (p_post_id, p_user_id, p_content)
    RETURNING comment_id INTO new_id;
RETURN new_id;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION sp_list_comments(
    p_post_id BIGINT
) RETURNS TABLE(
    comment_id BIGINT, post_id BIGINT, content TEXT, created_at TIMESTAMP,
    user_id BIGINT, user_name VARCHAR
) AS $$
BEGIN
RETURN QUERY
SELECT c.comment_id, c.post_id, c.content, c.created_at, u.user_id, u.user_name
FROM comments c
         JOIN users u ON u.user_id = c.user_id
WHERE c.post_id = p_post_id
ORDER BY c.created_at ASC;
END;
$$ LANGUAGE plpgsql;