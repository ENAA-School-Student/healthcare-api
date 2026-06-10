

CREATE TABLE users (
        id       BIGINT AUTO_INCREMENT PRIMARY KEY,
        username VARCHAR(250) NOT NULL UNIQUE,
        email    VARCHAR(250) NOT NULL UNIQUE,
        password VARCHAR(250) NOT NULL,
        role     VARCHAR(20)  NOT NULL DEFAULT 'PATIENT'
);


CREATE TABLE patient (
        id             BIGINT PRIMARY KEY,
        nom            VARCHAR(100) NOT NULL,
        prenom         VARCHAR(100) NOT NULL,
        telephone      VARCHAR(20),
        date_naissance DATE,
        CONSTRAINT fk_patient_user FOREIGN KEY (id) REFERENCES users(id)
);


CREATE TABLE medecin (
        id         BIGINT PRIMARY KEY,
        nom        VARCHAR(100) NOT NULL,
        specialite VARCHAR(100),
        telephone  VARCHAR(20),
        CONSTRAINT fk_medecin_user FOREIGN KEY (id) REFERENCES users(id)
);

CREATE TABLE dossier_medical (
        id           BIGINT AUTO_INCREMENT PRIMARY KEY,
        diagnostic   VARCHAR(500),
        observations VARCHAR(500),
        date_creation DATE,
        patient_id   BIGINT UNIQUE,
        CONSTRAINT fk_dossier_patient FOREIGN KEY (patient_id) REFERENCES patient(id)
);

CREATE TABLE rendez_vous (
        id               BIGINT AUTO_INCREMENT PRIMARY KEY,
        date_rendez_vous DATETIME NOT NULL,
        statut           ENUM('PLANIFIE','CONFIRME','ANNULE'),
        patient_id       BIGINT,
        medecin_id       BIGINT,
        CONSTRAINT fk_rdv_patient FOREIGN KEY (patient_id) REFERENCES patient(id),
        CONSTRAINT fk_rdv_medecin FOREIGN KEY (medecin_id) REFERENCES medecin(id)
);