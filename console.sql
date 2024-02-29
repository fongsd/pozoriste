drop table if exists Biletar;

drop table if exists TipScena;
drop table if exists Karte;
drop table if exists Izvodjenje;
drop table if exists Menadzer;
drop table if exists Predstava;


drop table if exists TipPredstave;


drop table if exists Korisnik;


drop table if exists Sediste;



drop table if  exists Sediste;

drop table if exists Scena;

create table TipPredstave(
                             tip varchar(255) not null primary key
                             constraint provera check ( tip in ("BALET", "DRAMA", "OPERA"))
);

insert into TipPredstave values ("DRAMA"), ("BALET"), ("OPERA");

create table Predstava(
naziv varchar(255) not null primary key,
tip varchar(255),
reziser varchar(255),
glumci varchar(255),
trajanje int,
produkcija varchar(255),
godina int,
opis varchar(255),
foreign key (tip) references TipPredstave(tip)
);

insert into Predstava(naziv, tip, reziser, glumci, trajanje, produkcija, godina, opis)
value ("Gospodjica", "DRAMA", "I. Andric", " Nataša Ninković, Vojislav Brajović, Nataša Tapuškovi, Srdjan Timarov", "100", "_" ,1945, "Neki opis");

insert into Predstava(naziv, tip, reziser, glumci, trajanje, produkcija, godina, opis)
    value ("Gospodjica2", "BALET", "I. Andric", " Nataša Ninković, Vojislav Brajović, Nataša Tapuškovi, Srdjan Timarov", "100", "_" ,1945, "Neki opis");

insert into Predstava(naziv, tip, reziser, glumci, trajanje, produkcija, godina, opis)
    value ("opcija2", "BALET", "I. Andric", " Nataša Ninković, Vojislav Brajović, Nataša Tapuškovi, Srdjan Timarov", "100", "_" ,1945, "Neki opis");


create table Scena (
id int not null,
naziv varchar(255) not null primary key,
ton varchar(255)
);

create table Izvodjenje(
id int not null primary key,
vremePocetka DATE,
cenaKarte double,
predstavaId varchar(255) not null,
scenaId varchar(255) not null,
foreign key (predstavaId) references Predstava(naziv),
foreign key (scenaId) references Scena(naziv)
);



create table Sediste(
red int not null,
broj int not null,
primary key (red, broj),
naziv varchar(255),
foreign key (naziv) references Scena(naziv)
);



create table Karte(
serialId varchar(255) not null primary key,
cena double,
popust int,
vremeIzdavanja DATE,
izvodjenje int not null,
red int,
broj int,
foreign key (izvodjenje) references Izvodjenje(id),
foreign key (red, broj) references Sediste(red, broj)
);

create table Korisnik(
username varchar(255) not null primary key,
lozinka varchar(255),
ime varchar(255),
prezime varchar(255)
);

insert into Korisnik value ("fongsd", "123", "pera", "peric");


create table Menadzer(
username varchar(255) not null,
nazivPredstave varchar(255) not null,
primary key (username, nazivPredstave),
foreign key (username) references Korisnik(username),
foreign key (nazivPredstave) references Predstava(naziv)
);

create table Biletar(
username varchar(255) not null,
karta varchar(255) not null ,
primary key (username, karta),
foreign key (username) references Korisnik(username),
foreign key (karta) references Karte(serialId)
);

create table TipScena(
    tip varchar(255) not null,
    scena varchar(255) not null,
    primary key (tip, scena),
    foreign key (tip) references TipPredstave(tip),
    foreign key (scena) references Scena(naziv)
);
