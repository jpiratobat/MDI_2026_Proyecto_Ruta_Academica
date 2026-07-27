-- Script de inserción de datos para Planes de Estudio

-- Insertar planes de estudio
INSERT INTO PlanEstudio (idPlan, nomPlan) VALUES
(1, 'Ingeniería de Sistemas y Computación'),
(2, 'Ingeniería Eléctrica');

-- Insertar materias de Ingeniería de Sistemas y Computación
INSERT INTO Materia (idMateria, nomMateria, numCreditos) VALUES
-- Materias básicas compartidas (1-6)
(1, 'Cálculo diferencial', 4),
(2, 'Cálculo integral', 4),
(3, 'Fund. Mecánica', 4),
(4, 'Álgebra lineal', 4),
(5, 'Fund. Elect. y Magnet.', 4),
(6, 'Cálculo multivariado', 4),

-- Materias específicas de Sistemas (7-37)
(7, 'Prob. y estadística (fund.)', 3),
(8, 'Métodos numéricos', 3),
(9, 'Discretas I', 4),
(10, 'Discretas II', 4),
(11, 'Programación de Computadores', 3),
(12, 'POO', 3),
(13, 'Intro. teoría de la computación', 4),
(14, 'Estructuras de datos', 3),
(15, 'Bases de datos', 3),
(16, 'Pensamiento sistémico', 3),
(17, 'Intro. Ing. de Sistemas y computación', 3),
(18, 'Elementos de computadores', 3),
(19, 'Arq. de computadores', 3),
(20, 'Sistemas operativos', 3),
(21, 'Redes de computadores', 3),
(22, 'Modelos y simulación', 3),
(23, 'Optimización', 3),
(24, 'Modelos estocásticos', 3),
(25, 'Lenguajes de programación', 3),
(26, 'Algoritmos', 3),
(27, 'Teoría de la Información', 3),
(28, 'Comp. Visual', 3),
(29, 'Comp. Paralela', 3),
(30, 'Intro. Cripto. y seguridad de la Info.', 3),
(31, 'Intro. Sistemas Inteligentes', 3),
(32, 'Sistemas de Información', 3),
(33, 'Ingesoft I', 3),
(34, 'Ingesoft II', 3),
(35, 'Arquisoft', 3),
(36, 'ArquiTICS', 3),
(37, 'Gerencia y gestión de P.', 3),

-- Materias específicas de Eléctrica (38-66)
(38, 'Introduccion a la Ingenieria Electrica', 5),
(39, 'Taller de Ingenieria Electrica', 2),
(40, 'Ecuaciones Diferenciales', 4),
(41, 'Introducción a la ciencia de materiales', 3),
(42, 'Circuitos Electricos I', 3),
(43, 'Variable Compleja', 4),
(44, 'Fundamentos de mecánica de Fluidos', 3),
(45, 'Campos Electromagneticos', 4),
(46, 'Electronica Analoga I', 4),
(47, 'Optativa Probabilidad y Estadistica', 3),
(48, 'Señales y Sistemas I', 3),
(49, 'Fundamentos de Oscilaciones y Ondas', 4),
(50, 'Circuitos Electricos II', 4),
(51, 'Electronica Digital I', 4),
(52, 'Asignatura Optativa (Herramientas Ing)', 3),
(53, 'Señales y Sistemas II', 3),
(54, 'Conversión Electromagnetica', 3),
(55, 'Transmisión y Distribución', 3),
(56, 'Mecanica para Ingenieria', 3),
(57, 'Control', 4),
(58, 'Introduccion a Sistemas de Energia Electrica', 3),
(59, 'Instalaciones Electricas', 3),
(60, 'Taller de Ingenieria', 3),
(61, 'Optativa Administración I', 3),
(62, 'Asignatura Optativa (Electrotecnia)', 3),
(63, 'Análisis de Sistemas de Potencia', 3),
(64, 'Optativa Administración II', 3),
(65, 'Asignatura Optativa (Sistemas de Potencia)', 3),
(66, 'Asignatura Optativa (Innovación e Investigación)', 3),
(67, 'Ingenieria Economica',3);

-- Asignar materias al plan de Sistemas
INSERT INTO MateriaPorPlan (idPlan, idMateria) VALUES
-- Plan de Sistemas (idPlan = 1)
(1, 1), (1, 2), (1, 3), (1, 4), (1, 5), (1, 6),  -- Materias básicas
(1, 7), (1, 8), (1, 9), (1, 10), (1, 11), (1, 12), (1, 13), (1, 14), (1, 15),
(1, 16), (1, 17), (1, 18), (1, 19), (1, 20), (1, 21), (1, 22), (1, 23), (1, 24),
(1, 25), (1, 26), (1, 27), (1, 28), (1, 29), (1, 30), (1, 31), (1, 32), (1, 33),
(1, 34), (1, 35), (1, 36), (1, 37), (1,67);

-- Asignar materias al plan de Eléctrica
INSERT INTO MateriaPorPlan (idPlan, idMateria) VALUES
-- Plan de Eléctrica (idPlan = 2)
(2, 1), (2, 2), (2, 3), (2, 4), (2, 5), (2, 6),  -- Materias básicas
(2, 11), (2, 38), (2, 39), (2, 40), (2, 41), (2, 42), (2, 43), (2, 44), (2, 45), (2, 46),
(2, 47), (2, 48), (2, 49), (2, 50), (2, 51), (2, 52), (2, 53), (2, 54), (2, 55),
(2, 56), (2, 57), (2, 58), (2, 59), (2, 60), (2, 61), (2, 62), (2, 63), (2, 64),
(2, 65), (2, 66), (2,67);

-- Insertar prerrequisitos (solo una vez para cada relación)
INSERT INTO Prerequisito (idMateria, idPrerequisito) VALUES
-- Prerrequisitos básicos compartidos
(2, 1),   -- Cálculo integral -> Cálculo diferencial
(3, 1),   -- Fund. Mecánica -> Cálculo diferencial
(4, 1),   -- Álgebra lineal -> Cálculo diferencial
(5, 2), (5, 3),   -- Fund. Elect. y Magnet. -> Cálculo integral, Fund. Mecánica
(6, 2),   -- Cálculo multivariado -> Cálculo integral
(67,2), -- Ingenieria economica -> Cálculo integral

-- Prerrequisitos específicos de Sistemas
(7, 2),   -- Prob. y estadística -> Cálculo integral
(8, 6),   -- Métodos numéricos -> Cálculo multivariado
(9, 4),   -- Discretas I -> Álgebra lineal
(10, 9),  -- Discretas II -> Discretas I
(12, 11), -- POO -> Programación de Computadores
(13, 9),  -- Intro. teoría de la computación -> Discretas I
(14, 12), -- Estructuras de datos -> POO
(15, 12), -- Bases de datos -> POO
(18, 17), -- Elementos de computadores -> Intro. Ing. de Sistemas
(19, 18), -- Arq. de computadores -> Elementos de computadores
(20, 19), -- Sistemas operativos -> Arq. de computadores
(21, 14), (21, 5), (21, 19), -- Redes -> Estructuras, Fund. Elect., Arq. computadores
(22, 7), (22, 6), (22, 10), (22, 12), -- Modelos y simulación -> Prob, Cálculo mult, Discretas II, POO
(23, 22), -- Optimización -> Modelos y simulación
(24, 23), -- Modelos estocásticos -> Optimizacion
(25, 13), (25, 14), -- Lenguajes -> Intro teoría, Estructuras
(26, 7), (26, 10), (26, 14), -- Algoritmos -> Prob, Discretas II, Estructuras
(27, 7), (27, 21), -- Teoría Información -> Prob, Redes
(28, 26), (28, 27), -- Comp. Visual -> Algoritmos, Teoría Información
(29, 26), -- Comp. Paralela -> Algoritmos
(30, 26), -- Intro. Cripto -> Algoritmos
(31, 26), -- Intro. Sistemas Inteligentes -> Algoritmos
(32, 15), (32, 16), (32,37) , -- Sistemas de Información -> Bases datos, Pensamiento sistémico, Gerencia y gestion de P
(33, 14), (33, 15), (33, 16), -- Ingesoft I -> Estructuras, Bases datos, Pensamiento sistémico
(34, 33), (34, 21), -- Ingesoft II -> Ingesoft I, Redes
(35, 34), -- Arquisoft -> Ingesoft II
(36, 34), (36, 32), -- ArquiTICS -> Ingesoft II, Sistemas de Información
(37, 67),  -- Gerencia y gestión -> Ingenieria economica


-- Prerrequisitos específicos de Eléctrica
(40, 2), (40, 4), -- Ecuaciones Diferenciales -> Cálculo integral, Álgebra lineal
(41, 3),  -- Introducción ciencia materiales -> Fund. Mecánica
(42, 39), (42, 4), -- Circuitos Electricos I -> Taller Ing Elect, Álgebra lineal
(43, 40), -- Variable Compleja -> Ecuaciones Diferenciales
(44, 40), (44, 3), -- Fund. mecánica Fluidos -> Ecuaciones, Fund. Mecánica
(45, 5), (45, 6), -- Campos Electromagneticos -> Fund. Elect, Cálculo multivariado
(46, 42), -- Electronica Analoga I -> Circuitos I
(47, 2),  -- Optativa Probabilidad -> Cálculo integral
(48, 42), (48, 40), -- Señales y Sistemas I -> Circuitos I, Ecuaciones
(49, 40), (49, 3), -- Fund. Oscilaciones y Ondas -> Ecuaciones, Fund. Mecánica
(50, 40), (50, 42), -- Circuitos Electricos II -> Ecuaciones, Circuitos I
(51, 46), -- Electronica Digital I -> Electronica Analoga I
(52, 40), (52, 11), (52, 6), (52, 47), -- Optativa Herramientas -> Ecuaciones, Programación, Cálculo mult, Probabilidad
(53, 48), (53, 43), -- Señales y Sistemas II -> Señales I, Variable Compleja
(54, 50), (54, 6), -- Conversión Electromagnetica -> Circuitos II, Cálculo multivariado
(55, 50), (55, 6), -- Transmisión y Distribución -> Circuitos II, Cálculo multivariado
(56, 4),  -- Mecanica para Ingenieria -> Álgebra lineal
(57, 53), -- Control -> Señales y Sistemas II
(58, 54), (58, 55), -- Intro Sistemas Energia -> Conversión, Transmisión
(59, 50), -- Instalaciones Electricas -> Circuitos II
(60, 51), -- Taller de Ingenieria -> Electronica Digital I
(61, 67),  -- Optativa Administración I -> Ingenieria Economica
(62, 58), (62, 59), -- Optativa Electrotecnia -> Intro Sistemas Energia, Instalaciones
(63, 58), -- Análisis Sistemas Potencia -> Intro Sistemas Energia
(64, 67),  -- Optativa Administración II -> Ingenieria Economica
(65, 63), -- Optativa Sistemas Potencia -> Análisis Sistemas Potencia
(66, 63), (66, 55); -- Optativa Innovación -> Análisis Sistemas Potencia, Transmisión

-- Verificar los prerequisitos de cada materia
SELECT materia , nomMateria AS prerequisito 
FROM Materia m JOIN 
(SELECT nomMateria AS materia , idPrerequisito FROM PlanEstudio 
JOIN MateriaPorPlan USING (idPlan)
JOIN Materia USING (idMateria)
JOIN prerequisito USING (idMateria)
WHERE idPlan = 1) AS sub -- Se puede cambiar el id del plan de estudios
ON (m.idMateria = sub.idPrerequisito);

