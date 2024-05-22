CREATE TABLE Bombeiro(
    idbombeiro INTEGER PRIMARY KEY AUTOINCREMENT,
    nome VARCHAR(50),
    cpf CHAR(11),
    login VARCHAR(7),
    cargo VARCHAR(15),
    senha VARCHAR(15)
);

CREATE TABLE Sensor(
    idsensor INTEGER PRIMARY KEY AUTOINCREMENT,
    posição CHAR(3)
);


CREATE TABLE Alerta(
    idalerta INTEGER PRIMARY KEY AUTOINCREMENT,
    fk_idsensor INTEGER,
    datahora DATETIME,
    temperatura INTEGER,
    umidade INTEGER,
    vento INTEGER,
    FOREIGN KEY (fk_idsensor) REFERENCES Sensor(idsensor)
);

INSERT INTO Bombeiro VALUES("1","kauan","21312312312","kauan","kauan","kauan")