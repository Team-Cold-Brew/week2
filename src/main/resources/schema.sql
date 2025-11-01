-- Eliminar las tablas si ya existen para un reinicio limpio
DROP TABLE IF EXISTS attempts;
DROP TABLE IF EXISTS games;
DROP TABLE IF EXISTS words;
DROP TABLE IF EXISTS topics;

-- Tabla para almacenar los temas o categorías de las palabras
CREATE TABLE topics (
    id   BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE
);

-- Tabla para almacenar las palabras a adivinar
CREATE TABLE words (
    id        BIGINT AUTO_INCREMENT PRIMARY KEY,
    word_text VARCHAR(50) NOT NULL,
    topic_id  BIGINT NOT NULL,
    CONSTRAINT fk_topic FOREIGN KEY (topic_id) REFERENCES topics(id)
);

-- Tabla para gestionar cada partida
CREATE TABLE games (
    id            BIGINT AUTO_INCREMENT PRIMARY KEY,
    word_id       BIGINT NOT NULL,
    start_time    TIMESTAMP NOT NULL,
    status        VARCHAR(20) NOT NULL, -- Por ejemplo: 'IN_PROGRESS', 'WON', 'LOST'
    attempts_left INT NOT NULL,
    CONSTRAINT fk_word FOREIGN KEY (word_id) REFERENCES words(id)
);

-- Tabla para registrar cada intento dentro de una partida
CREATE TABLE attempts (
    id           BIGINT AUTO_INCREMENT PRIMARY KEY,
    game_id      BIGINT NOT NULL,
    guessed_word VARCHAR(50) NOT NULL,
    attempt_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_game FOREIGN KEY (game_id) REFERENCES games(id)
);