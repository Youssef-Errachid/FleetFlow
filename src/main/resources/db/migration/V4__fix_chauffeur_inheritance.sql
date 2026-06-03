
ALTER TABLE chauffeur
    ADD COLUMN IF NOT EXISTS user_id BIGINT;

ALTER TABLE chauffeur
    ADD CONSTRAINT fk_chauffeur_user
        FOREIGN KEY (user_id)
            REFERENCES users(id)
            ON DELETE SET NULL
            ON UPDATE CASCADE;


ALTER TABLE client
    ADD COLUMN IF NOT EXISTS user_id BIGINT;

ALTER TABLE client
    ADD CONSTRAINT fk_client_user
        FOREIGN KEY (user_id)
            REFERENCES users(id)
            ON DELETE SET NULL
            ON UPDATE CASCADE;