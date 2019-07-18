
CREATE TABLE prosecutor (
    id VARCHAR PRIMARY KEY,
    name VARCHAR NOT NULL
);

CREATE TABLE criminalcase (
    id VARCHAR PRIMARY KEY,
    name VARCHAR NOT NULL,
    t_time bigint not NULL,
    criminalElements_id bigint,
    procuratorate_id bigint
);

CREATE TABLE procuratorate (
    id VARCHAR PRIMARY KEY,
    name VARCHAR(50) NOT NULL
);

CREATE TABLE criminalelements (
    id VARCHAR PRIMARY KEY,
    subjectiveElementDescription VARCHAR NOT NULL,
    objectiveElementDescription VARCHAR NOT NULL
);