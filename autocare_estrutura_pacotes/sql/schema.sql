-- Schema da base de dados AutoCare (Tarefa 8.1)
-- Reflete o ERD da Aula 3: Cliente 1:N Viatura, Viatura 1:N Marcação,
-- Mecânico 1:N Marcação, Marcação 1:1 Intervenção.

CREATE TABLE IF NOT EXISTS cliente (
    id_cliente BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome       VARCHAR(120) NOT NULL,
    contacto   VARCHAR(9)   NOT NULL,
    nif        VARCHAR(9)   NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS mecanico (
    id_mecanico    BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome           VARCHAR(120) NOT NULL,
    especialidade  VARCHAR(80)
);

CREATE TABLE IF NOT EXISTS viatura (
    id_viatura  BIGINT AUTO_INCREMENT PRIMARY KEY,
    matricula   VARCHAR(10) NOT NULL,
    marca       VARCHAR(60) NOT NULL,
    modelo      VARCHAR(60) NOT NULL,
    ano         INT NOT NULL,
    id_cliente  BIGINT NOT NULL,
    CONSTRAINT fk_viatura_cliente FOREIGN KEY (id_cliente) REFERENCES cliente (id_cliente)
);

CREATE TABLE IF NOT EXISTS marcacao (
    id_marcacao  BIGINT AUTO_INCREMENT PRIMARY KEY,
    data         DATE NOT NULL,
    hora         TIME NOT NULL,
    estado       VARCHAR(20) NOT NULL,
    id_viatura   BIGINT NOT NULL,
    id_mecanico  BIGINT NOT NULL,
    CONSTRAINT fk_marcacao_viatura  FOREIGN KEY (id_viatura)  REFERENCES viatura (id_viatura),
    CONSTRAINT fk_marcacao_mecanico FOREIGN KEY (id_mecanico) REFERENCES mecanico (id_mecanico)
);

CREATE TABLE IF NOT EXISTS intervencao (
    id_intervencao  BIGINT AUTO_INCREMENT PRIMARY KEY,
    descricao       VARCHAR(255) NOT NULL,
    pecas_usadas    VARCHAR(255),
    custo           DECIMAL(10,2) NOT NULL,
    data_conclusao  DATE NOT NULL,
    id_marcacao     BIGINT NOT NULL UNIQUE,
    CONSTRAINT fk_intervencao_marcacao FOREIGN KEY (id_marcacao) REFERENCES marcacao (id_marcacao)
);
