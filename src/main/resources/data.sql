-- Insertar temas iniciales
-- (IDs son 1, 2, 3 respectivamente)
INSERT INTO topics (name) VALUES
('Java'),
('Spring'),
('DevOps');

-- Insertar palabras para el tema 'Java' (topic_id = 1)
-- Todas las palabras tienen 5 letras para seguir el formato Wordle
INSERT INTO words (word_text, topic_id) VALUES
('CLASS', 1),
('FINAL', 1),
('BYTES', 1),
('THROW', 1),
('WHILE', 1),
('INDEX', 1),
('SUPER', 1);

-- Insertar palabras para el tema 'Spring' (topic_id = 2)
INSERT INTO words (word_text, topic_id) VALUES
('BEANS', 2),
('PROXY', 2),
('MODEL', 2),
('SCOPE', 2),
('AWARE', 2),
('ROUTE', 2),
('GUARD', 2);

-- Insertar palabras para el tema 'DevOps' (topic_id = 3)
INSERT INTO words (word_text, topic_id) VALUES
('DOCKER', 3), -- Nota: 6 letras, el juego deberá manejarlo
('AGENT', 3),
('VAULT', 3),
('CLOUD', 3),
('SHELL', 3),
('TERRA', 3),
('PROXY', 3); -- Una palabra puede pertenecer a varios temas si se inserta dos veces