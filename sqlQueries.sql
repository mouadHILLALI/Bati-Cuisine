CREATE TABLE clients (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    address VARCHAR(255),
    phone VARCHAR(255),
    isProfessional BOOLEAN DEFAULT FALSE
);
CREATE TABLE projects (
    id SERIAL PRIMARY KEY,
    projectName VARCHAR(255) NOT NULL,
    surface INT,
    projectStatus VARCHAR(255),
    beneficialMargin DOUBLE PRECISION,
    totalCost DOUBLE PRECISION,
    taxRate DOUBLE PRECISION,
    clientID INT REFERENCES clients(id) ON DELETE CASCADE
);
CREATE TABLE materials (
    id SERIAL PRIMARY KEY,
    materialName VARCHAR(255) NOT NULL,
    unitCost double PRECISION,
    quantity double PRECISION,
    tax DOUBLE PRECISION,
    transCost DOUBLE PRECISION,
    qualityCoeff DOUBLE PRECISION,
    projectID INT REFERENCES projects(id) ON DELETE CASCADE
);
CREATE TABLE labourers (
    id SERIAL PRIMARY KEY,
    specialty VARCHAR(255) NOT NULL,
    hourlyRate double PRECISION,
    totalHours double PRECISION,
    taxRate DOUBLE PRECISION,
    productivityCoefficient DOUBLE PRECISION,
    projectID INT REFERENCES projects(id) ON DELETE CASCADE
);

