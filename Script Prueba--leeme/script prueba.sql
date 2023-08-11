-- Se debe crear en una base de datos PostgreSQL V 15

-- Versión del java del Sprinboot 17
--Proyecto Angular

-- Database: parqueadero

-- DROP DATABASE IF EXISTS parqueadero;

CREATE DATABASE parqueadero
    WITH
    OWNER = postgres
    ENCODING = 'UTF8'
    LC_COLLATE = 'Spanish_Colombia.1252'
    LC_CTYPE = 'Spanish_Colombia.1252'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1
    IS_TEMPLATE = False;

CREATE TABLE person (
    id serial PRIMARY KEY,
    nombre varchar(255),
    telefono integer,
    email varchar(255)
);

CREATE TABLE address (
    id serial PRIMARY KEY,
    streets varchar(255),
    citys varchar(255),
    states varchar(255),
    postal_code integer,
    country varchar(255),
    person_id integer REFERENCES person(id)
);

CREATE TABLE profesor (
    id serial PRIMARY KEY,
    salario integer,
    persona_id integer REFERENCES person(id)
);


CREATE TABLE estudiante (
    id serial PRIMARY KEY,
    codigo_estudiante integer,
    promedio double precision,
	personi_id integer REFERENCES person(id)
);

--- body post
{
    "name": "linda caicedo",
    "phoneNumber": 123434565,
    "email": "psawqqa@example.com",
    "tipoRoll": 1,
    "activo":1,
    "address": {
        "street": "Calle 4",
        "city": "bogota",
        "state": "cundi",
        "postalCode": 1212344,
        "country": "Colombia"
    },
    "profesor": {
        
    },
    "estudiante": {
        "codigoEstudiante":12315,
        "promedio":4.8
    }
}