CREATE TABLE patient (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(100) NOT NULL,
    prenom VARCHAR(100) NOT NULL,
    email VARCHAR(150) UNIQUE,
    telephone VARCHAR(20),
    date_naissance DATE
);

CREATE TABLE medecin (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(100) NOT NULL,
    specialite VARCHAR(100),
    email VARCHAR(150) UNIQUE,
    telephone VARCHAR(20)
);

CREATE TABLE dossier_medical (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    diagnostic VARCHAR(500),
    observations VARCHAR(500),
    date_creation DATE,
    patient_id BIGINT UNIQUE,
    FOREIGN KEY (patient_id) REFERENCES patient(id)
);

CREATE TABLE rendez_vous (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    date_rendez_vous DATETIME NOT NULL,
    statut VARCHAR(50),
    patient_id BIGINT,
    medecin_id BIGINT,
    FOREIGN KEY (patient_id) REFERENCES patient(id),
    FOREIGN KEY (medecin_id) REFERENCES medecin(id)
);