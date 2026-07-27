DROP SCHEMA IF EXISTS graduacion;
CREATE SCHEMA IF NOT EXISTS graduacion;

USE graduacion;

DROP TABLE IF EXISTS PlanEstudio;
CREATE TABLE IF NOT EXISTS PlanEstudio(
  idPlan INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
  nomPlan VARCHAR(100) NOT NULL
);

DROP TABLE IF EXISTS Materia;
CREATE TABLE IF NOT EXISTS Materia(
  idMateria INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
  nomMateria VARCHAR(100) NOT NULL,
  numCreditos INT NOT NULL
);

DROP TABLE IF EXISTS MateriaPorPlan;
CREATE TABLE IF NOT EXISTS MateriaPorPlan (
	idPlan INT NOT NULL,
    idMateria INT NOT NULL,
    PRIMARY KEY(idPlan, idMateria),
    FOREIGN KEY (idPlan) REFERENCES PlanEstudio(idPlan),
    FOREIGN KEY (idMateria) REFERENCES Materia(idMateria)
);


DROP TABLE IF EXISTS Prerequisito ;
CREATE TABLE IF NOT EXISTS Prerequisito (
	idMateria INT NOT NULL,
    idPrerequisito INT NOT NULL,
    PRIMARY KEY(idMateria, idPrerequisito),
    FOREIGN KEY (idMateria) REFERENCES Materia(idMateria),
    FOREIGN KEY (idPrerequisito) REFERENCES Materia(idMateria)
);