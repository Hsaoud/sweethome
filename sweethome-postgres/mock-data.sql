DELETE FROM cleaners WHERE id >= 100;
DELETE FROM homers WHERE id >= 100;
DELETE FROM users WHERE id >= 100;

-- Test Password: "password"
INSERT INTO users (id, email, password, first_name, last_name, phone, role, enabled, latitude, longitude, created_at, updated_at) VALUES
(101, 'cleaner.paris@test.com', '$2b$10$HJ5CnimOjdJk6urALya3TO2AbVlEYJ3afXOoCO9/ZTWvxI/qJy7vy', 'Jean', 'Dupont', '+33612345678', 'CLEANER', true, 48.8566, 2.3522, NOW(), NOW()),
(102, 'cleaner.lyon@test.com', '$2b$10$HJ5CnimOjdJk6urALya3TO2AbVlEYJ3afXOoCO9/ZTWvxI/qJy7vy', 'Marie', 'Curie', '+33612345679', 'CLEANER', true, 45.7640, 4.8357, NOW(), NOW()),
(103, 'cleaner.marseille@test.com', '$2b$10$HJ5CnimOjdJk6urALya3TO2AbVlEYJ3afXOoCO9/ZTWvxI/qJy7vy', 'Paul', 'Cezanne', '+33612345680', 'CLEANER', true, 43.2965, 5.3698, NOW(), NOW()),
(104, 'cleaner.bordeaux@test.com', '$2b$10$HJ5CnimOjdJk6urALya3TO2AbVlEYJ3afXOoCO9/ZTWvxI/qJy7vy', 'Claire', 'Chazal', '+33612345681', 'CLEANER', true, 44.8378, -0.5792, NOW(), NOW()),
(105, 'homer.paris@test.com', '$2b$10$HJ5CnimOjdJk6urALya3TO2AbVlEYJ3afXOoCO9/ZTWvxI/qJy7vy', 'Homer', 'Simpson', '+33698765432', 'HOMER', true, 48.8600, 2.3500, NOW(), NOW()),
(106, 'homer.lyon@test.com', '$2b$10$HJ5CnimOjdJk6urALya3TO2AbVlEYJ3afXOoCO9/ZTWvxI/qJy7vy', 'Marge', 'Simpson', '+33698765433', 'HOMER', true, 45.7700, 4.8400, NOW(), NOW());

INSERT INTO cleaners (id, action_radius_km, available, bio, city, experience_years, headline, price_per_sqm, hourly_rate, service_area) VALUES
(101, 30, true, 'Je suis un nettoyeur très expérimenté sur la région parisienne.', 'Paris', 5, 'Nettoyage Pro Paris', 0.50, 15.00, 'Paris et banlieue'),
(102, 50, true, 'Spécialiste de la propreté. J''opère principalement sur Lyon.', 'Lyon', 8, 'Expert Lyon', 0.45, 14.00, 'Rhône-Alpes'),
(103, 40, true, 'Souriant, dynamique, je rends votre maison étincelante sur Marseille !', 'Marseille', 3, 'Nettoyage Rapide et Efficace', 0.40, 13.00, 'Bouches-du-Rhône'),
(104, 20, true, 'Qualité et confiance pour vos travaux ménagers sur Bordeaux.', 'Bordeaux', 10, 'Qualité Premium', 0.60, 16.00, 'Gironde');

INSERT INTO homers (id, address, city, postal_code, additional_info) VALUES
(105, '10 Rue de Rivoli', 'Paris', '75004', 'Appartement au 3ème étage, porte rouge.'),
(106, '15 Place Bellecour', 'Lyon', '69002', 'Bâtiment A, digicode 1234.');

SELECT setval('users_id_seq', (SELECT MAX(id) FROM users));
