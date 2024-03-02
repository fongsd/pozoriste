drop table if exists Biletar;

drop table if exists TipScena;
drop table if exists Karte;
drop table if exists Izvodjenje;
drop table if exists Predstava;
drop table if exists Menadzer;


drop table if exists TipPredstave;


drop table if exists Korisnik;


drop table if exists Sediste;



drop table if exists Scena;
drop table if  exists Sediste;


create table Scena (
                       id int not null auto_increment primary key ,
                       naziv varchar(255) not null unique key ,
                       ton varchar(255)
);

create table Sediste(
                        red int not null,
                        broj int not null,
                        naziv varchar(255),
                        primary key (red, broj, naziv),
                        foreign key (naziv) references Scena(naziv)
);

create table TipPredstave(
                             tip varchar(255) not null primary key
                                 constraint provera check ( tip in ("BALET", "DRAMA", "OPERA"))
);

insert into TipPredstave values ("DRAMA"), ("BALET"), ("OPERA");

create table Korisnik(
                         username varchar(255) not null primary key,
                         lozinka varchar(255) not null,
                         ime varchar(255) not null ,
                         prezime varchar(255) not null
);

create table Menadzer(
                         username varchar(255) not null primary key ,
                         foreign key (username) references Korisnik(username)
);


create table Predstava(
                          naziv varchar(255) not null primary key,
                          tip varchar(255),
                          reziser varchar(255),
                          glumci varchar(255),
                          trajanje int,
                          produkcija varchar(255),
                          godina int,
                          opis varchar(255),
                          menadzer varchar(255),
                          foreign key (menadzer) references Menadzer(username),
                          foreign key (tip) references TipPredstave(tip)
);


insert into Predstava(naziv, tip, reziser, glumci, trajanje, produkcija, godina, opis)
    value ("Gospodjica", "DRAMA", "I. Andric", " Nataša Ninković, Vojislav Brajović, Nataša Tapuškovi, Srdjan Timarov", "100", "_" ,1945, "Neki opis");



insert into Predstava(naziv, tip, reziser, glumci, trajanje, produkcija, godina, opis)
    value ("Gospodjica2", "BALET", "I. Andric", " Nataša Ninković, Vojislav Brajović, Nataša Tapuškovi, Srdjan Timarov", "100", "_" ,1945, "Neki opis");



insert into Predstava(naziv, tip, reziser, glumci, trajanje, produkcija, godina, opis)
    value ("opcija2", "BALET", "I. Andric", " Nataša Ninković, Vojislav Brajović, Nataša Tapuškovi, Srdjan Timarov", "100", "_" ,1945, "Neki opis");

insert into scena value (1, "Prva scena", "neki ton");


create table Izvodjenje(
                           id int not null primary key auto_increment,
                           nazivPredstave varchar(255),
                           vremePocetka DATE,
                           scenaId varchar(255) not null,
                           cenaKarte double,
                           foreign key (nazivPredstave) references Predstava(naziv),
                           foreign key (scenaId) references Scena(naziv)
);


insert into izvodjenje(vremePocetka, cenaKarte, nazivPredstave, scenaId)
    value ('2023-10-20', 100, "Gospodjica", "Prva scena");
insert into izvodjenje(vremePocetka, cenaKarte, nazivPredstave, scenaId)
    value ('2023-10-20', 100, "Gospodjica2", "Prva scena");

create table Karte(
                      serialId int not null auto_increment primary key ,
                      cena double,
                      popust int,
                      vremeIzdavanja DATE,
                      izvodjenje int not null,
                      red int,
                      broj int,
                      unique key (izvodjenje, red, broj),
                      foreign key (izvodjenje) references Izvodjenje(id),
                      foreign key (red, broj) references Sediste(red, broj)
);

create table Biletar(
                        username varchar(255) not null,
                        primary key (username),
                        foreign key (username) references Korisnik(username)
);

insert into Korisnik value ("fongsd", "123", "pera", "peric");

insert into Biletar value ("fongsd");

insert into Menadzer(username) value ("fongsd");

create table TipScena(
                         tip varchar(255) not null,
                         scena varchar(255) not null,
                         primary key (tip, scena),
                         foreign key (tip) references TipPredstave(tip),
                         foreign key (scena) references Scena(naziv)
);